import { Route, Routes } from "react-router-dom";
import Header from "@/components/header/Header.tsx";
import Footer from "@/components/Footer.tsx";
import Homepage from "@/pages/Homepage.tsx";
import CMS from "@/pages/cms/CMS.tsx";
import CMSLogin from "@/pages/cms/CMSLogin.tsx";
import CMSDestinations from "@/pages/cms/destination/CMSDestinations.tsx";
import CMSDestination from "@/pages/cms/destination/CMSDestination.tsx";
import CMSCreateDestination from "@/pages/cms/destination/CMSCreateDestination.tsx";
import CMSArticles from "@/pages/cms/article/CMSArticles.tsx";
import CMSArticle from "@/pages/cms/article/CMSArticle.tsx";
import CMSCreateArticle from "@/pages/cms/article/CMSCreateArticle.tsx";
import CMSUsers from "@/pages/cms/user/CMSUsers.tsx";
import CMSUser from "@/pages/cms/user/CMSUser.tsx";
import CMSCreateUser from "@/pages/cms/user/CMSCreateUser.tsx";
import PopularArticles from "@/pages/reader/PopularArticles.tsx";
import Article from "@/pages/reader/Article.tsx";
import ArticlesForActivity from "@/pages/reader/ArticlesForActivity.tsx";
import ArticlesForDestination from "@/pages/reader/ArticlesForDestination.tsx";
import Destinations from "@/pages/reader/Destinations.tsx";


const App = () => {
  return (
    <div className="size-full flex flex-col items-center">
      <Header />
      <Routes>
        <Route path="/"                         element={ <Homepage />               } />
        <Route path="/cms"                      element={ <CMS />                    } />
        <Route path="/cms/login"                element={ <CMSLogin />               } />
        <Route path="/cms/destinations"         element={ <CMSDestinations />        } />
        <Route path="/cms/destinations/:id"     element={ <CMSDestination />         } />
        <Route path="/cms/destinations/create"  element={ <CMSCreateDestination />   } />
        <Route path="/cms/articles"             element={ <CMSArticles />            } />
        <Route path="/cms/articles/:id"         element={ <CMSArticle />             } />
        <Route path="/cms/articles/create"      element={ <CMSCreateArticle />       } />
        <Route path="/cms/users"                element={ <CMSUsers />               } />
        <Route path="/cms/users/:id"            element={ <CMSUser />                } />
        <Route path="/cms/users/create"         element={ <CMSCreateUser />          } />
        <Route path="/popular"                  element={ <PopularArticles />        } />
        <Route path="/destinations"             element={ <Destinations />           } />
        <Route path="/articles/:id"             element={ <Article />                } />
        <Route path="/articles/activity/:id"    element={ <ArticlesForActivity />    } />
        <Route path="/articles/destination/:id" element={ <ArticlesForDestination /> } />
      </Routes>
      <Footer />
    </div>
  )
}

export default App
