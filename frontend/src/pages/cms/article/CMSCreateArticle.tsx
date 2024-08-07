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
import { useContext, useEffect, useState } from "react";
import { ArticleCreate } from "@/types/article.ts";
import { Page } from "@/types/common.ts";
import { Destination } from "@/types/destination.ts";
import { Activity } from "@/types/activity.ts";
import { ApplicationContext, ApplicationContextData } from "@/lib/context.ts";
import axios, { AxiosError, HttpStatusCode } from "axios";
import { clearUserDataAsync } from "@/lib/local_storage.ts";
import { ArticleForm, ArticleFormData } from "@/components/form/ArticleForm.tsx";
import { Card } from "@/components/common/Card.tsx";
import { Env } from "@/lib/utils.ts";

const CMSCreateArticle = () => {
  const navigate = useNavigate();
  const [destinationPage, setDestinationPage] = useState<Page<Destination>>();
  const [activityPage, setActivityPage] = useState<Page<Activity>>();
  const application: ApplicationContextData = useContext(ApplicationContext);

  useEffect(() => {
    if (!application.data) {
      navigate("/cms");
      return;
    }

    axios.get(`${Env.API_URL}/activities`, {
      headers: {
        Authorization: `${application.data.authorization}`
      }
    })
         .then(response => setActivityPage(response.data))
         .catch((error: AxiosError) => {
           if (error.response?.status !== HttpStatusCode.Unauthorized)
             return;

           clearUserDataAsync().then(() => navigate("/cms"));
         });
  }, []);

  useEffect(() => {
    if (!application.data) {
      navigate("/cms");
      return;
    }

    axios.get(`${Env.API_URL}/destinations`, {
      headers: {
        Authorization: `${application.data.authorization}`
      }
    })
         .then(response => setDestinationPage(response.data))
         .catch((error: AxiosError) => {
           if (error.response?.status !== HttpStatusCode.Unauthorized)
             return;

           clearUserDataAsync().then(() => navigate("/cms"));
         });
  }, []);

  const onSubmit = (data: ArticleFormData) => {
    if (!application.data)
      return;

    const create: ArticleCreate = {
      title:          data.title,
      content:        data.content,
      author_id:      application.data.user.id,
      destination_id: data.destination.id,
      activities:     data.activities.map((activity) => activity.id),
    }

    axios.post(`${Env.API_URL}/articles`, create, {
            headers: {
              Authorization: `${application.data.authorization}`
            }
          })
         .then(() => navigate("/cms/articles"));
  }

  return (
    <Card className="h-fit p-8 bg-gray-850">
      <ArticleForm
        activities={activityPage?.content}
        destinations={destinationPage?.content}
        onSubmit={onSubmit}
        create={true}
      />
    </Card>
  )
}

export default CMSCreateArticle;
