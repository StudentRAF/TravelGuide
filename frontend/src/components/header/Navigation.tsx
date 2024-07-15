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
