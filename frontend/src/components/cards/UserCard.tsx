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
