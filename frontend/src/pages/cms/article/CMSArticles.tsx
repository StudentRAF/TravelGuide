import { useEffect, useState } from "react";
import { Page } from "@/types/common.ts";
import { Article } from "@/types/article.ts";
import axios from "axios";
import ArticleCard from "@/components/cards/ArticleCard.tsx";
import { PaginationSection } from "@/components/common/Pagination.tsx";
import { Card } from "@/components/common/Card.tsx";
import { Separator } from "@/components/common/Separator.tsx";
import { Button } from "@/components/common/Button.tsx";
import { useNavigate } from "react-router-dom";

const CMSArticles = () => {
  const pageSize = 5;
  const [page, setPage] = useState(1);
  const [articlePage, setArticlePage] = useState<Page<Article>>();
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`http://localhost:8080/TravelGuide/api/v1/articles?page=${page - 1}&size=${pageSize}&sort=created_at`)
         .then(response => setArticlePage(response.data));
  }, [page]);

  const onClick = (article: Article) => {
    navigate(`/cms/articles/${article.id}`)
  }

  return (
    <div className="flex flex-col w-full">
      <span className="text-heading text-center mb-6">
        Articles
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
            onClick={() => navigate("/cms/articles/create")}
          >
            Create Article
          </Button>
        </Card>
        <div className="flex flex-col gap-4 items-center">
          {
            articlePage ?
              articlePage.content.map((article: Article) => (
                <ArticleCard
                  article={article}
                  onClick={() => onClick(article)}
                  key={article.id}
                />
              ))
              :
              [...Array(pageSize).keys()].map(index => (
                <ArticleCard key={index}/>
              ))
          }
          <PaginationSection
            currentPage={page}
            totalPages={articlePage ? articlePage.total_pages : 0}
            onChangePage={setPage}
          />
        </div>
        <div className="w-72"/>
      </div>
    </div>
  )
}

export default CMSArticles;
