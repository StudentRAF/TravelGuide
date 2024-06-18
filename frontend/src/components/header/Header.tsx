import Logo from "@/assets/logo/Logo.tsx";
import Navigation from "@/components/header/Navigation.tsx";
import { useLocation } from "react-router-dom";

const Header = () => {
  const cms: boolean = useLocation().pathname
                                    .startsWith("/cms");
  return (
    <header className="flex flex-row w-full py-4 items-center px-60 justify-between">
      <div className="w-44">
        <Logo/>
      </div>
      <Navigation/>
      {
        cms ? (
          <div className="w-44" />
        ) : (
          <div className="w-44" />
        )
      }
    </header>
)
}

export default Header;