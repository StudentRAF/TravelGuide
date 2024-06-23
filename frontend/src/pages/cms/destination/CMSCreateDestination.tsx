import { useNavigate } from "react-router-dom";
import { ApplicationContext, ApplicationContextData } from "@/lib/context.ts";
import { useContext, useEffect } from "react";
import axios from "axios";
import { Card } from "@/components/common/Card.tsx";
import { DestinationForm, DestinationFormData } from "@/components/form/DestinationForm.tsx";
import { DestinationCreate } from "@/types/destination.ts";

const CMSCreateDestination = () => {
  const navigate = useNavigate();
  const application: ApplicationContextData = useContext(ApplicationContext);

  useEffect(() => {
    if (!application.data) {
      navigate("/cms");
      return;
    }
  }, [application.data, navigate]);

  const onSubmit = (data: DestinationFormData) => {
    if (!application.data)
      return;

    const create: DestinationCreate = {
      name:        data.name,
      description: data.description,
    }

    axios.post(`http://localhost:8080/TravelGuide/api/v1/destinations`, create, {
            headers: {
              Authorization: `${application.data.authorization}`
            }
          })
         .then(() => navigate("/cms/destinations"));
  }

  return (
    <Card className="h-fit p-8 bg-gray-850">
      <DestinationForm
        create={true}
        onSubmit={onSubmit}
      />
    </Card>
  )
}

export default CMSCreateDestination;
