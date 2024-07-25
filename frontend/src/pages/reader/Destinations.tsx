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
import { PaginationSection } from "@/components/common/Pagination.tsx";
import { Destination } from "@/types/destination.ts";
import DestinationCard from "@/components/cards/DestinationCard.tsx";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Env } from "@/lib/utils.ts";

const Destinations = () => {
  const pageSize = 5;
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [destinationPage, setDestinationPage] = useState<Page<Destination>>();
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`${Env.API_URL}/destinations?page=${page - 1}&size=${pageSize}&sort=name`)
         .then(response => setDestinationPage(response.data));
  }, [page]);

  useEffect(() => {
    setLoading(!destinationPage);
  }, [destinationPage]);

  const onClick = (destination: Destination) => {
    navigate(`/articles/destination/${destination.id}`)
  }

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
              <DestinationCard
                destination={destination}
                onClick={() => onClick(destination)}
                key={destination.id}
              />
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
