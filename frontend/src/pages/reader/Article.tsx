import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Article as ArticleData } from "@/types/article.ts";
import axios from "axios";
import { Activity } from "@/types/activity.ts";
import { Comment, CommentCreate } from "@/types/comment.ts";
import { Badge } from "@/components/common/Badge.tsx";
import { Card } from "@/components/common/Card.tsx";
import { Separator } from "@/components/common/Separator.tsx";
import { CommentForm, CommentFormData } from "@/components/form/CommentForm.tsx";
import CommentCard from "@/components/cards/CommentCard.tsx";
import { Skeleton } from "@/components/common/Skeleton.tsx";

type ArticleParams = {
  id: string
}

const Article = () => {
  const [article, setArticle] = useState<ArticleData>();
  const articleParams = useParams<ArticleParams>();
  const navigate = useNavigate();

  useEffect(() => {
    if (!articleParams.id)
      return;

    axios.get(`http://localhost:8080/TravelGuide/api/v1/articles/${articleParams.id}`)
         .then(response => setArticle(response.data));
  }, [articleParams.id]);

  if (!articleParams.id)
    return <></>;

  const onCommentSubmit = (data: CommentFormData) => {
    if (!articleParams.id)
      return;

    const comment: CommentCreate = {
      article_id:   Number.parseInt(articleParams.id),
      display_name: data.display_name,
      content:      data.content,
    }

    axios.post(`http://localhost:8080/TravelGuide/api/v1/comments`, comment)
         .then(response => {
           const comment: Comment = response.data;

           setArticle(previous => {
             if (!previous)
               return previous;

             previous.comments.push(comment);

             return {...previous}
           })
         });
  }

  const navigateToDestination = (destination_id: number) => {
    navigate(`/articles/destination/${destination_id}`);
  }
  const navigateToActivity = (destination_id: number) => {
    navigate(`/articles/activity/${destination_id}`);
  }

  return (
    <div className="flex flex-row w-full justify-between">
      <div className="w-56 flex flex-col gap-5">
        <Card className="w-full bg-gray-850 p-8 h-fit flex flex-col items-center gap-3">
          <h1 className="text-title text-center">
            Destination
          </h1>
          <Separator/>
          {
            article ?
              <Badge
                className="w-fit cursor-pointer"
                onClick={() => navigateToDestination(article.destination.id)}
              >
                {article?.destination.name}
              </Badge>
              :
              <Skeleton className="w-24 h-6" />
          }
        </Card>
        <Card className="w-full bg-gray-850 p-8 h-fit flex flex-col items-center gap-3">
          <h1 className="text-title text-center">
            Activities
          </h1>
          <Separator/>
          {
            article ?
              article.activities.map((activity: Activity) =>
                <Badge
                 variant="secondary"
                 className="w-fit max-w-full cursor-pointer truncate"
                 onClick={() => navigateToActivity(activity.id)}
                 key={activity.id}
                >
                  {activity.name}
                </Badge>
              )
              :
              <Skeleton className="w-24 h-6" />
          }
        </Card>
      </div>

      <div className="flex flex-col w-200 gap-5">
        <Card className="flex flex-col w-full p-8 bg-gray-850">
          {
            article ?
              <>
                <span className="text-heading text-center font-semibold mb-5">
                  {article?.title}
                </span>
                <span className="text-large font-normal">
                  {article?.content}
                </span>
              </>
              :
              <>
                <Skeleton className="w-52 h-6 mt-1 mb-6 self-center" />
                <Skeleton className="w-full h-28 my-1" />
              </>
          }
        </Card>
        <Card className="flex flex-col w-full p-8 bg-gray-850">
          <div className="flex flex-col">
          <span className="text-title text-center font-semibold mb-5">
            Comments
          </span>
            <Separator className="mb-3"/>
            {
              article ?
                article.comments.map((comment: Comment) =>
                  <div key={comment.id}>
                    <CommentCard
                      comment={comment}
                    />
                    <Separator className="my-3" key={comment.id} />
                  </div>
                )
                :
                <>
                  <CommentCard />
                  <Separator className="my-3" />
                </>
            }
            <CommentForm onSubmit={onCommentSubmit} />
          </div>
        </Card>
      </div>

      <div className="w-56 flex flex-col gap-5">
        <Card className="w-full bg-gray-850 p-8 h-fit flex flex-col items-center gap-3">
          <h1 className="text-title text-center">
            Author
          </h1>
          <Separator/>
          {
            article ?
              <Badge className="w-fit bg-gray-700 hover:bg-gray-650">
                {article?.author.first_name} {article?.author.last_name}
              </Badge>
              :
              <Skeleton className="w-24 h-6" />
          }
        </Card>
        <Card className="w-full bg-gray-850 p-8 h-fit flex flex-col items-center gap-3">
          <h1 className="text-title text-center">
            Publish Date
          </h1>
          <Separator/>
          {
            article ?
              <Badge className="w-fit bg-gray-700 hover:bg-gray-650">
                {article?.created_at.toString()}
              </Badge>
              :
              <Skeleton className="w-24 h-6" />
          }
        </Card>
      </div>
    </div>
  )
}

export default Article;
