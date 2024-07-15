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
