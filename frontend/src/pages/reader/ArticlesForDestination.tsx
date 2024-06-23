import { useEffect, useState } from "react";
import { Page } from "@/types/common.ts";
import { Article } from "@/types/article.ts";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import DestinationMenu from "@/components/reader/DestinationMenu.tsx";
import ArticleCard from "@/components/cards/ArticleCard.tsx";
import { PaginationSection } from "@/components/common/Pagination.tsx";
import ActivityMenu from "@/components/reader/ActivityMenu.tsx";

type DestinationParams = {
  id: string
}

const ArticlesForDestination = () => {
  const pageSize = 5;
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [articlePage, setArticlePage] = useState<Page<Article>>();
  const destinationParams = useParams<DestinationParams>();
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`http://localhost:8080/TravelGuide/api/v1/articles/destination/${destinationParams.id}?page=${page - 1}&size=${pageSize}&sort=title`)
      .then(response => setArticlePage(response.data));
  }, [destinationParams.id, page]);

  useEffect(() => {
    setLoading(!articlePage);
  }, [articlePage]);

  const onClick = (article: Article) => {
    navigate(`/articles/${article.id}`)
  }

  return (
    <div className="flex flex-col w-full">
      <span className="text-heading text-center mb-6">
        Popular Articles
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
              articlePage?.empty ?
                <>
                  No articles found
                </>
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

export default ArticlesForDestination;
