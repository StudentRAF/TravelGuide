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
  }, []);

  useEffect(() => {
    if (!application.data) {
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

    axios.post(`http://localhost:8080/TravelGuide/api/v1/articles`, create, {
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
