import { useEffect, useState } from "react";
import { Destination } from "@/types/destination.ts";
import { Page } from "@/types/common.ts";
import axios from "axios";
import { Button } from "@/components/common/Button.tsx";
import {
  Pagination,
  PaginationContent,
  PaginationItem,
  PaginationNext,
  PaginationPrevious
} from "@/components/common/Pagination.tsx";
import { Separator } from "@/components/common/Separator.tsx";
import { Skeleton } from "@/components/common/Skeleton.tsx";
import { useNavigate } from "react-router-dom";

const DestinationMenu = () => {
  const pageSize = 15;

  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [destinationPage, setDestinationPage] = useState<Page<Destination>>();

  useEffect(() => {
    axios.get(`http://localhost:8080/TravelGuide/api/v1/destinations?page=${page - 1}&size=${pageSize}&sort=name`)
         .then(response => {
           setDestinationPage(response.data)
         })
  }, [page]);

  useEffect(() => {
    setLoading(!destinationPage);
  }, [destinationPage]);

  const nextPage = (event: React.MouseEvent<HTMLAnchorElement>) => {
    event.preventDefault();
    setLoading(true);
    setPage(page + 1);
  }

  const previousPage = (event: React.MouseEvent<HTMLAnchorElement>) => {
    event.preventDefault();
    setLoading(true);
    setPage(page - 1);
  }

  const redirectToArticles = (destination_id: number) => {
    navigate(`/articles/destination/${destination_id}`);
  }

  return (
    <div className="flex flex-col w-72 p-5 bg-gray-850 h-fit rounded-large">
      <h1 className="text-title text-center mt-1 mb-3">
        Destinations
      </h1>
      <Separator />
      <div className="flex flex-col my-2">
        {
          loading ?
            [...Array(pageSize).keys()].map(index => (
              <div className="flex items-center h-10" key={index}>
                <Skeleton key={index} className="h-4 w-full" />
              </div>
            ))
            :
            destinationPage?.content.map((destination: Destination) => (
              <Button
                variant="ghost"
                className="hover:bg-gray-800"
                onClick={() => redirectToArticles(destination.id)}
                key={destination.id}>
                {destination.name}
              </Button>
            ))
        }
      </div>
      <Separator />
      <div className="mt-3">
        <Pagination>
          <PaginationContent className="flex justify-between w-full">
            <PaginationItem>
              <PaginationPrevious
                onClick={previousPage}
                disabled={loading || page === 1}
              />
            </PaginationItem>
            <PaginationItem>
              <span>
                Page {page} {destinationPage ? `of ${destinationPage.total_pages}` : ""}
              </span>
            </PaginationItem>
            <PaginationItem>
            <PaginationNext
                onClick={nextPage}
                disabled={loading || page === destinationPage?.total_pages}
              />
            </PaginationItem>
          </PaginationContent>
        </Pagination>
      </div>
    </div>
  )
}

export default DestinationMenu;