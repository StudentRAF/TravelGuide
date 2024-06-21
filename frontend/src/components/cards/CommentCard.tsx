import { Comment } from "@/types/comment.ts";
import { Skeleton } from "@/components/common/Skeleton.tsx";

export interface CommentCardProps {
  comment?: Comment,
}

const CommentCard = ({ comment } : CommentCardProps) => {
  return (
    <div className="flex flex-col">
      {
        comment ?
          <>
            <span className="text-large">
              {comment.display_name}
            </span>
            <span className="mt-2.5">
              {comment.content}
            </span>
            <span className="text-small text-gray-200 mt-2">
              Posted at: {comment.created_at.toString()}
            </span>

          </>
          :
          <>
            <Skeleton className="w-20 h-4 my-1"/>
            <Skeleton className="w-full h-10 mb-0.5 mt-3"/>
            <Skeleton className="w-32 h-3.5 mb-0.5 mt-2.5"/>
          </>
      }
    </div>
  )
}

export default CommentCard;