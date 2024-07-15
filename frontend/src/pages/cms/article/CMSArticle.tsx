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

import { useNavigate, useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { ApplicationContext, ApplicationContextData } from "@/lib/context.ts";
import { Article, ArticleUpdate } from "@/types/article.ts";
import axios, { AxiosError, HttpStatusCode } from "axios";
import { clearUserDataAsync } from "@/lib/local_storage.ts";
import { Card } from "@/components/common/Card.tsx";
import { ArticleForm, ArticleFormData } from "@/components/form/ArticleForm.tsx";
import { Destination } from "@/types/destination.ts";
import { Activity } from "@/types/activity.ts";
import { Page } from "@/types/common.ts";

type ArticleParams = {
  id: string
}

const CMSArticle = () => {
  const navigate = useNavigate();
  const articleParams = useParams<ArticleParams>();
  const [article, setArticle] = useState<Article>();
  const [destinationPage, setDestinationPage] = useState<Page<Destination>>();
  const [activityPage, setActivityPage] = useState<Page<Activity>>();
  const application: ApplicationContextData = useContext(ApplicationContext);

  useEffect(() => {
    if (!application.data || !articleParams.id) {
      navigate("/cms");
      return;
    }

    axios.get(`http://localhost:8080/TravelGuide/api/v1/articles/${articleParams.id}`, {
      headers: {
        Authorization: `${application.data.authorization}`
      }
    })
         .then(response => setArticle(response.data))
         .catch((error: AxiosError) => {
           if (error.response?.status !== HttpStatusCode.Unauthorized)
             return;

           clearUserDataAsync().then(() => navigate("/cms"));
         });
  }, [application.data, articleParams.id, navigate]);

  useEffect(() => {
    if (!application.data || !articleParams.id) {
      navigate("/cms");
      return;
    }

    axios.get(`http://localhost:8080/TravelGuide/api/v1/activities`, {
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
  }, [application.data, articleParams.id, navigate]);

  useEffect(() => {
    if (!application.data || !articleParams.id) {
      navigate("/cms");
      return;
    }

    axios.get(`http://localhost:8080/TravelGuide/api/v1/destinations`, {
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
  }, [application.data, articleParams.id, navigate]);

  const onSubmit = (data: ArticleFormData) => {
    if (!article || !application.data)
      return;

    const update: ArticleUpdate = {
      id:         article.id,
      title:      data.title,
      content:    data.content,
      activities: data.activities.map((activity) => activity.id),
    }

    axios.put(`http://localhost:8080/TravelGuide/api/v1/articles`, update, {
            headers: {
              Authorization: `${application.data.authorization}`
            }
          })
         .then(() => navigate("/cms/articles"));
  }

  return (
    <Card className="h-fit p-8 bg-gray-850">
      <ArticleForm
        article={article}
        activities={activityPage?.content}
        destinations={destinationPage?.content}
        onSubmit={onSubmit}
      />
    </Card>
  )
}

export default CMSArticle;
