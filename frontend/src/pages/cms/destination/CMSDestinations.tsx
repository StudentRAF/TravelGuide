import { useEffect, useState } from "react";
import { Page } from "@/types/common.ts";
import { Destination } from "@/types/destination.ts";
import axios from "axios";
import DestinationCard from "@/components/cards/DestinationCard.tsx";
import { PaginationSection } from "@/components/common/Pagination.tsx";
import { useNavigate } from "react-router-dom";
import { Card } from "@/components/common/Card.tsx";
import { Separator } from "@/components/common/Separator.tsx";
import { Button } from "@/components/common/Button.tsx";

const CMSDestinations = () => {
  const pageSize = 5;
  const [page, setPage] = useState(1);
  const [destinationPage, setDestinationPage] = useState<Page<Destination>>();
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`http://localhost:8080/TravelGuide/api/v1/destinations?page=${page - 1}&size=${pageSize}&sort=name`)
         .then(response => setDestinationPage(response.data));
  }, [page]);

  const onClick = (destination: Destination) => {
    navigate(`/cms/destinations/${destination.id}`)
  }

  return (
    <div className="flex flex-col w-full">
      <span className="text-heading text-center mb-6">
        Destinations
      </span>
      <div className="w-full flex justify-between">
        <Card className="w-72 flex flex-col gap-3 h-fit bg-gray-850 p-8">
          <h1 className="text-title text-center">
            Actions
          </h1>
          <Separator/>
          <Button
            variant="outline"
            className="hover:bg-gray-800"
            onClick={() => navigate("/cms/destinations/create")}
          >
            Create Destination
          </Button>
        </Card>
        <div className="flex flex-col gap-4 items-center">
          {
            destinationPage ?
              destinationPage?.content.map((destination: Destination) => (
                <DestinationCard
                  destination={destination}
                  onClick={() => onClick(destination)}
                  cms={true}
                  key={destination.id}/>
              ))
              :
              [...Array(pageSize).keys()].map(index => (
                <DestinationCard
                  cms={true}
                  key={index}
                />
              ))
          }
          <PaginationSection
            currentPage={page}
            totalPages={destinationPage ? destinationPage.total_pages : 0}
            onChangePage={setPage}
          />
        </div>
        <div className="w-72"/>
      </div>
    </div>
  )
}

export default CMSDestinations;
