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

import { ApplicationContext, ApplicationContextData } from "@/lib/context.ts";
import { useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { LoginForm, LoginFormData } from "@/components/form/LoginForm.tsx";
import { Card } from "@/components/common/Card.tsx";
import { User, UserLogin } from "@/types/user.ts";
import axios from "axios";
import { setLocalStorageDataAsync, USER, USER_TOKEN } from "@/lib/local_storage.ts";


const CMSLogin = () => {
  const navigate = useNavigate();
  const application: ApplicationContextData = useContext(ApplicationContext);

  useEffect(() => {
    if (application.data)
      navigate("/cms");
  }, []);

  const onSubmit = (data: LoginFormData) => {
    const login: UserLogin = {
      email:    data.email,
      password: data.password,
    }

    axios.post(`http://localhost:8080/TravelGuide/api/v1/users/login`, login)
         .then(response => {
           const user: User    = response.data;
           const token: string = response.headers["authorization"];

           if (!user || !token)
             return;

           application.setData({
             user: user,
             authorization: token
           });

           setLocalStorageDataAsync(USER, user)
             .then(() => setLocalStorageDataAsync(USER_TOKEN, token))
             .then(() => navigate("/cms"));
         });
  }

  return (
    <Card className="bg-gray-850 h-full p-8">
      <LoginForm onSubmit={onSubmit} />
    </Card>
  )
}

export default CMSLogin;
