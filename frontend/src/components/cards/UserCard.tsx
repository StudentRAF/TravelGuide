import { Card } from "@/components/common/Card.tsx";
import { User } from "@/types/user.ts";
import { Badge } from "@/components/common/Badge.tsx";
import { Circle } from "lucide-react";
import { cn } from "@/lib/utils.ts";
import { useNavigate } from "react-router-dom";

export interface UserCardProps {
  user?: User,
}

const UserCard = ({ user } : UserCardProps) => {
  const navigate = useNavigate();

  return (
    <Card
      className="w-140 p-8 bg-gray-850 hover:bg-gray-800 cursor-pointer"
      onClick={() => navigate(`/cms/users/${user?.id}`)}
    >
      {
        user ?
          <>
            <div className="flex flex-row gap-3 items-center">
              <span className="text-title">
                {user.first_name} {user.last_name}
              </span>
              <Badge className="w-fit">
                {user.user_role.name}
              </Badge>
            </div>
            <div className="flex flex-row gap-2 justify-between items-center mt-2">
              <span className="text-gray-100">
                {user.email}
              </span>
              <Circle className={cn(
                "w-5",
                user.enabled && "text-green-500 fill-green-500",
                !user.enabled && "text-red-500 fill-red-500",
              )}
              />
            </div>
          </>
          :
          <></>
      }
    </Card>
  )
}

export default UserCard;
