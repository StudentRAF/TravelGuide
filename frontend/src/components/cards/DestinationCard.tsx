import { Destination } from "@/types/destination.ts";
import { Card } from "@/components/common/Card.tsx";
import { useNavigate } from "react-router-dom";
import { Skeleton } from "@/components/common/Skeleton.tsx";

export interface DestinationCardProps {
  destination?: Destination,
}

const DestinationCard = ({ destination } : DestinationCardProps) => {
  const navigate = useNavigate();

  return (
    <Card
      className="flex flex-col p-8 h-fit w-200 bg-gray-850 hover:bg-gray-800 cursor-pointer"
      onClick={() => navigate(`/articles/destination/${destination?.id}`)}
    >
      {
        destination ?
          <>
            <span className="text-heading mb-2">
              {destination.name}
            </span>
            <span className="">
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
