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

import { useNavigate } from "react-router-dom";
import { useContext, useEffect } from "react";
import { getRole, UserCreate } from "@/types/user.ts";
import { ApplicationContext, ApplicationContextData } from "@/lib/context.ts";
import axios from "axios";
import { UserForm, UserFormData } from "@/components/form/UserForm.tsx";
import { Card } from "@/components/common/Card.tsx";

const CMSCreteUser = () => {
  const navigate = useNavigate();
  const application: ApplicationContextData = useContext(ApplicationContext);

  useEffect(() => {
    if (!application.data) {
      navigate("/cms");
      return;
    }
  }, [application.data, navigate]);

  const onSubmit = (data: UserFormData) => {
    if (!application.data)
      return;

    const user: UserCreate = {
      first_name:       data.first_name,
      last_name:        data.last_name,
      email:            data.email,
      role_id:          getRole(data.user_role).id,
      password:         data.password,
      confirm_password: data.password,
    }

    axios.post(`http://localhost:8080/TravelGuide/api/v1/users`, user, {
      headers: {
        Authorization: `${application.data.authorization}`
      }
    })
         .then(() => navigate("/cms/users"));
  }

  return (
    <Card className="h-fit p-8 bg-gray-850">
      <UserForm hasPassword={true} onSubmit={onSubmit}/>
    </Card>
  )
}

export default CMSCreteUser;
