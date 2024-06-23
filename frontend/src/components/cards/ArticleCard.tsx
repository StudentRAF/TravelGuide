import { Article } from "@/types/article.ts";
import { Card } from "@/components/common/Card.tsx";
import { Activity } from "@/types/activity.ts";
import { Badge } from "@/components/common/Badge.tsx";
import ActivityIcon from "@/assets/Icons/ActivityIcon.tsx";
import LocationIcon from "@/assets/Icons/LocationIcon.tsx";
import { Skeleton } from "@/components/common/Skeleton.tsx";

export interface ArticleCardProps {
  article ?: Article,
  onClick ?: () => void
}

const ArticleCard = ({ article, onClick} : ArticleCardProps) => {
  const handleClick = () => {
    onClick && onClick();
  }

  return (
    <Card
      className="flex flex-col p-8 w-200 bg-gray-850 hover:bg-gray-800 cursor-pointer"
      onClick={handleClick}
    >
      {article ?
        <>
          <span className="text-heading font-semibold">
            {article.title}
          </span>
          <div className="flex flex-wrap gap-2 items-center my-2">
            <div className="flex size-4 justify-center">
              <LocationIcon />
            </div>
            <Badge className="w-fit cursor-pointer">
              {article.destination.name}
            </Badge>
          </div>
          <div className="flex flex-wrap gap-1.5 items-center">
            <div className="flex size-4 justify-center">
              <ActivityIcon/>
            </div>
            {article.activities.map((activity: Activity) => (
              <Badge
                variant="secondary"
                className="w-fit cursor-pointer"
                key={activity.id}
              >
                {activity.name}
              </Badge>
            ))}
          </div>
          <span className="text-gray-100 line-clamp-2 mt-3 mb-5">
            {article.content}
          </span>
          <div className="flex justify-between">
            <span>
              {article.author.first_name} {article.author.last_name}
            </span>
            <span>
              {article.created_at.toString()}
            </span>
          </div>
        </>
        :
        <>
          <Skeleton className="w-full h-6 my-1"/>
          <div className="flex flex-wrap gap-2 items-center my-2">
            <div className="flex size-4 justify-center">
              <LocationIcon/>
            </div>
            <Skeleton className="w-28 h-6" />
          </div>
          <div className="flex flex-wrap gap-1.5 items-center">
            <div className="flex size-4 justify-center">
              <ActivityIcon/>
            </div>
            <Skeleton className="w-28 h-6" />
          </div>
          <Skeleton className="w-full h-10 mt-3 mb-5"/>
          <div className="flex justify-between">
            <Skeleton className="w-32 h-4 my-0.5"/>
            <Skeleton className="w-20 h-4 my-0.5"/>
          </div>
        </>
      }
    </Card>
  )
}

export default ArticleCard;
