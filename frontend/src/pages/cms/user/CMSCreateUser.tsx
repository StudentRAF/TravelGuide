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
