import { useContext, useEffect, useState } from "react";
import { Page } from "@/types/common.ts";
import axios, { HttpStatusCode } from "axios";
import { PaginationSection } from "@/components/common/Pagination.tsx";
import { User } from "@/types/user.ts";
import { Card } from "@/components/common/Card.tsx";
import { Separator } from "@/components/common/Separator.tsx";
import { Button } from "@/components/common/Button.tsx";
import UserCard from "@/components/cards/UserCard.tsx";
import { ApplicationContext, ApplicationContextData } from "@/lib/context.ts";
import { clearUserDataAsync } from "@/lib/local_storage.ts";
import { useNavigate } from "react-router-dom";

const CMSUsers = () => {
  const pageSize = 5;
  const [page, setPage] = useState(1);
  const [usersPage, setUsersPage] = useState<Page<User>>();
  const application: ApplicationContextData = useContext(ApplicationContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (!application.data) {
      navigate("/cms");
      return;
    }

    axios.get(`http://localhost:8080/TravelGuide/api/v1/users?page=${page - 1}&size=${pageSize}&sort=role_id&sort=first_name`, {
      headers: {
        Authorization: application.data.authorization,
      }
    }).then(response => {
      if (response.status === HttpStatusCode.Unauthorized) {
        clearUserDataAsync();
        navigate("/cms");
        return;
      }

      setUsersPage(response.data)
    })
  }, [page]);

  return (
    <div className="flex flex-col w-full">
      <span className="text-heading text-center mb-6">
        User Accounts
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
            onClick={() => navigate("/cms/users/create")}
          >
            Create User
          </Button>
        </Card>
        <div className="flex w-160 flex-col gap-4 items-center">
          {
            usersPage ?
              usersPage.content.map((user: User) => (
                <UserCard user={user} key={user.id}/>
              ))
              :
              [...Array(pageSize).keys()].map(index => (
                <UserCard key={index}/>
              ))
          }
          <PaginationSection
            currentPage={page}
            totalPages={usersPage ? usersPage.total_pages : 0}
            onChangePage={setPage}
          />
        </div>
        <div className="w-72"/>
      </div>
    </div>
  )
}

export default CMSUsers;
