import DestinationMenu from "@/components/reader/DestinationMenu.tsx";
import ActivityMenu from "@/components/reader/ActivityMenu.tsx";
import { useEffect, useState } from "react";
import { Page } from "@/types/common.ts";
import { Article } from "@/types/article.ts";
import axios from "axios";
import ArticleCard from "@/components/cards/ArticleCard.tsx";
import { PaginationSection } from "@/components/common/Pagination.tsx";
import { useNavigate } from "react-router-dom";

const Homepage = () => {
  const pageSize = 5;
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [articlePage, setArticlePage] = useState<Page<Article>>();
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`http://localhost:8080/TravelGuide/api/v1/articles?page=${page - 1}&size=${pageSize}&sort=created_at`)
      .then(response => setArticlePage(response.data));
  }, [page]);

  useEffect(() => {
    setLoading(!articlePage);
  }, [articlePage]);

  const onClick = (article: Article) => {
    navigate(`/articles/${article.id}`)
  }

  return (
    <div className="flex flex-col w-full">
      <span className="text-heading text-center mb-6">
        Latest Articles
      </span>

      <div className="w-full flex justify-between">
        <DestinationMenu/>
        <div className="flex flex-col gap-4 items-center">
          {
            loading ?
              [...Array(pageSize).keys()].map(index => (
                <ArticleCard key={index}/>
              ))
              :
              articlePage?.content.map((article: Article) => (
                <ArticleCard
                  article={article}
                  onClick={() => onClick(article)}
                  key={article.id}
                />
              ))
          }
          <PaginationSection
            currentPage={page}
            totalPages={articlePage ? articlePage.total_pages : 0}
            onChangePage={setPage}
          />
        </div>
        <ActivityMenu/>
      </div>
    </div>
  )
}

export default Homepage;
