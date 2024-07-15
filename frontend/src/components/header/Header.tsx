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

import Logo from "@/assets/logo/Logo.tsx";
import Navigation from "@/components/header/Navigation.tsx";
import { useLocation, useNavigate } from "react-router-dom";

const Header = () => {
  const navigate = useNavigate();
  const cms: boolean = useLocation().pathname
                                    .startsWith("/cms");
  return (
    <header className="flex flex-row w-full py-4 items-center px-60 justify-between">
      <div className="w-44 cursor-pointer" onClick={() => navigate("/")}>
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