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

import { DestinationForm, DestinationFormData } from "@/components/form/DestinationForm.tsx";
import { useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { ApplicationContext, ApplicationContextData } from "@/lib/context.ts";
import axios, { AxiosError, HttpStatusCode } from "axios";
import { clearUserDataAsync } from "@/lib/local_storage.ts";
import { Destination, DestinationUpdate } from "@/types/destination.ts";
import { Card } from "@/components/common/Card.tsx";

type DestinationParams = {
  id: string
}

const CMSDestination = () => {
  const navigate = useNavigate();
  const destinationParams = useParams<DestinationParams>();
  const [destination, setDestination] = useState<Destination>();
  const application: ApplicationContextData = useContext(ApplicationContext);

  useEffect(() => {
    if (!application.data || !destinationParams.id) {
      navigate("/cms");
      return;
    }

    axios.get(`http://localhost:8080/TravelGuide/api/v1/destinations/${destinationParams.id}`, {
      headers: {
        Authorization: `${application.data.authorization}`
      }
    })
         .then(response => setDestination(response.data))
         .catch((error: AxiosError) => {
           if (error.response?.status !== HttpStatusCode.Unauthorized)
             return;

           clearUserDataAsync().then(() => navigate("/cms"));
         });
  }, [application.data, destinationParams.id, navigate]);

  const onSubmit = (data: DestinationFormData) => {
    if (!destination || !application.data)
      return;

    const update: DestinationUpdate = {
      id:          destination.id,
      name:        data.name,
      description: data.description,
    }

    axios.put(`http://localhost:8080/TravelGuide/api/v1/destinations`, update, {
            headers: {
              Authorization: `${application.data.authorization}`
            }
          })
         .then(() => navigate("/cms/destinations"));
  }


  return (
    <Card className="h-fit p-8 bg-gray-850">
      <DestinationForm
        destination={destination}
        onSubmit={onSubmit}
      />
    </Card>
  )
}

export default CMSDestination;
