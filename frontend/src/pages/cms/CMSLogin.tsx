import { ApplicationContext, ApplicationContextData } from "@/lib/context.ts";
import { useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { LoginForm, LoginFormData } from "@/components/form/LoginForm.tsx";
import { Card } from "@/components/common/Card.tsx";
import { User, UserLogin } from "@/types/user.ts";
import axios from "axios";


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

           navigate("/cms");
         });
  }

  return (
    <Card className="bg-gray-850 h-full p-8">
      <LoginForm onSubmit={onSubmit} />
    </Card>
  )
}

export default CMSLogin;
