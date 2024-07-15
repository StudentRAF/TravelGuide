/*
 * Copyright (C) 2024. Nemanja Radovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getRole, User, UserUpdate } from "@/types/user.ts";
import axios, { AxiosError, HttpStatusCode } from "axios";
import { ApplicationContext, ApplicationContextData } from "@/lib/context.ts";
import { clearUserDataAsync } from "@/lib/local_storage.ts";
import { UserForm, UserFormData } from "@/components/form/UserForm.tsx";
import { Card } from "@/components/common/Card.tsx";

type UserParams = {
  id: string
}

const CMSUser = () => {
  const navigate = useNavigate();
  const userParams = useParams<UserParams>();
  const [user, setUser] = useState<User>();
  const application: ApplicationContextData = useContext(ApplicationContext);

  useEffect(() => {
    if (!application.data || !userParams.id) {
      navigate("/cms");
      return;
    }

    axios.get(`http://localhost:8080/TravelGuide/api/v1/users/${userParams.id}`, {
            headers: {
              Authorization: `${application.data.authorization}`
            }
          })
         .then(response => setUser(response.data))
         .catch((error: AxiosError) => {
           if (error.response?.status !== HttpStatusCode.Unauthorized)
             return;

           clearUserDataAsync().then(() => navigate("/cms"));
         });
  }, [application.data, userParams.id, navigate]);

  const onSubmit = (data: UserFormData) => {
    if (Number.isNaN(Number(userParams.id)) || !application.data)
      return;

    const user: UserUpdate = {
      id: Number(userParams.id),
      first_name: data.first_name,
      last_name: data.last_name,
      email: data.email,
      role_id: getRole(data.user_role).id,
      enabled: data.enabled === "true",
    }

    axios.put(`http://localhost:8080/TravelGuide/api/v1/users`, user, {
            headers: {
              Authorization: `${application.data.authorization}`
            }
          })
         .then(() => navigate("/cms/users"));
  }

  return (
    <Card className="h-fit p-8 bg-gray-850">
      <UserForm user={user} onSubmit={onSubmit}/>
    </Card>
  )
}

export default CMSUser;
