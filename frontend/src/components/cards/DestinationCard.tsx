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

import { Destination } from "@/types/destination.ts";
import { Card } from "@/components/common/Card.tsx";
import { Skeleton } from "@/components/common/Skeleton.tsx";
import { cn } from "@/lib/utils.ts";

export interface DestinationCardProps {
  destination ?: Destination,
  cms         ?: boolean,
  onClick     ?: () => void
}

const DestinationCard = ({ destination, onClick,  cms = false } : DestinationCardProps) => {
  const handleClick = () => {
    onClick && onClick();
  }

  return (
    <Card
      className="flex flex-col p-8 h-fit w-200 bg-gray-850 hover:bg-gray-800 cursor-pointer"
      onClick={handleClick}
    >
      {
        destination ?
          <>
            <span className="text-heading mb-2">
              {destination.name}
            </span>
            <span
              className={cn(
                "",
                cms && "line-clamp-4"
              )}
            >
              {destination.description}
            </span>
          </>
          :
          <>
            <Skeleton className="w-full h-6 mt-1 mb-4"/>
            <Skeleton className="w-full h-20 "/>
          </>
      }
    </Card>
  )
}

export default DestinationCard;
