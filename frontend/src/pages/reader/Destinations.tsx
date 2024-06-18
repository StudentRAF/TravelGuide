import { useEffect, useState } from "react";
import { Page } from "@/types/common.ts";
import { PaginationSection } from "@/components/common/Pagination.tsx";
import { Destination } from "@/types/destination.ts";
import DestinationCard from "@/components/cards/DestinationCard.tsx";
import axios from "axios";

const Destinations = () => {
  const pageSize = 5;
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [destinationPage, setDestinationPage] = useState<Page<Destination>>();

  useEffect(() => {
    axios.get(`http://localhost:8080/TravelGuide/api/v1/destinations?page=${page - 1}&size=${pageSize}&sort=name`)
         .then(response => setDestinationPage(response.data));
  }, [page]);

  useEffect(() => {
    setLoading(!destinationPage);
  }, [destinationPage]);

  return (
    <div className="flex flex-col w-full">
      <span className="text-heading text-center mb-6">
        Destinations
      </span>
      <div className="flex flex-col gap-4 items-center">
        {
          loading ?
            [...Array(pageSize).keys()].map(index => (
              <DestinationCard key={index}/>
            ))
            :
            destinationPage?.content.map((destination: Destination) => (
              <DestinationCard destination={destination} key={destination.id}/>
            ))
        }
        <PaginationSection
          currentPage={page}
          totalPages={destinationPage ? destinationPage.total_pages : 0}
          onChangePage={setPage}
        />
      </div>
    </div>
  )
}

export default Destinations;
