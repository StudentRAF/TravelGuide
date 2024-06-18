import { Button } from "@/components/common/Button.tsx";
import { useLocation, useNavigate } from "react-router-dom";

const Navigation = () => {
  const navigate = useNavigate();
  const cms: boolean = useLocation().pathname
                                    .startsWith("/cms");

  return (
    <div className="flex gap-1 bg-gray-850 p-1 rounded-full">
      {
        cms ? (
          <>
            <Button variant="navigation" size="navigation" onClick={() => navigate("/cms/destinations")}>
              Destinations
            </Button>
            <Button variant="navigation" size="navigation" onClick={() => navigate("/cms/articles")}>
              Articles
            </Button>
            <Button variant="navigation" size="navigation" onClick={() => navigate("/cms/users")}>
              Users
            </Button>
            <Button variant="navigation" size="navigation" onClick={() => navigate("/")}>
              Reader
            </Button>
          </>
        ) : (
          <>
            <Button variant="navigation" size="navigation" onClick={() => navigate("/")}>
              Home
            </Button>
            <Button variant="navigation" size="navigation" onClick={() => navigate("/popular")}>
              Popular
            </Button>
            <Button variant="navigation" size="navigation" onClick={() => navigate("/destinations")}>
              Destinations
            </Button>
            <Button variant="navigation" size="navigation" onClick={() => navigate("/cms")}>
              CMS
            </Button>
          </>
        )
      }
    </div>
  )
}

export default Navigation
