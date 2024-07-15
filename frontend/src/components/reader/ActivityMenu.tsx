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

import { useEffect, useState } from "react";
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
import { Activity } from "@/types/activity.ts";
import { Card } from "@/components/common/Card.tsx";

const DestinationMenu = () => {
  const pageSize = 15;

  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [activityPage, setActivityPage] = useState<Page<Activity>>();

  useEffect(() => {
    axios.get(`http://localhost:8080/TravelGuide/api/v1/activities?page=${page - 1}&size=${pageSize}&sort=name`)
         .then(response => {
           setActivityPage(response.data)
         })
  }, [page]);

  useEffect(() => {
    setLoading(!activityPage);
  }, [activityPage]);

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
    navigate(`/articles/activity/${destination_id}`);
  }

  return (
    <Card className="flex flex-col w-72 h-fit p-5 bg-gray-850">
      <h1 className="text-title text-center mt-1 mb-3">
        Activities
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
            activityPage?.content.map((activity: Activity) => (
              <Button
                variant="ghost"
                className="hover:bg-gray-800"
                onClick={() => redirectToArticles(activity.id)}
                key={activity.id}
              >
                {activity.name}
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
                Page {page} {activityPage ? `of ${activityPage.total_pages}` : ""}
              </span>
            </PaginationItem>
            <PaginationItem>
            <PaginationNext
                onClick={nextPage}
                disabled={loading || page === activityPage?.total_pages}
              />
            </PaginationItem>
          </PaginationContent>
        </Pagination>
      </div>
    </Card>
  )
}

export default DestinationMenu;