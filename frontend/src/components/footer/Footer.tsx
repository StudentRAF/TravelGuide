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

import LogoVertical from "@/assets/logo/LogoVertical.tsx";
import { Separator } from "@/components/common/Separator.tsx";
import { Button } from "@/components/common/Button.tsx";
import { Input } from "@/components/common/Input.tsx";
import { useNavigate } from "react-router-dom";

const Footer = () => {
  const navigate = useNavigate();

  return (
    <footer className="flex w-full py-6 px-60 border-t border-gray-700 justify-between">
      <div className="cursor-pointer w-72" onClick={() => navigate("/")}>
        <LogoVertical/>
      </div>
      <div className="flex gap-32">
        <div className="flex flex-col">
          <span className="text-center font-semibold">
            Reader
          </span>
          <Separator className="mb-1 mt-2"/>
          <div className="flex flex-col gap-1">
            <Button
              variant="link"
              size="navigation"
              onClick={() => navigate("/")}
            >
              Home
            </Button>
            <Button
              variant="link"
              size="navigation"
              onClick={() => navigate("/popular")}
            >
              Popular
            </Button>
            <Button
              variant="link"
              size="navigation"
              onClick={() => navigate("/destinations")}
            >
              Destinations
            </Button>
          </div>
        </div>
        <div className="flex flex-col">
          <span className="text-center font-semibold">
            CMS
          </span>
          <Separator className="mb-1 mt-2"/>
          <div className="flex flex-col gap-1">
            <Button
              variant="link"
              size="navigation"
              onClick={() => navigate("/cms/articles")}
            >
              Articles
            </Button>
            <Button
              variant="link"
              size="navigation"
              onClick={() => navigate("/cms/destinations")}
            >
              Destinations
            </Button>
            <Button
              variant="link"
              size="navigation"
              onClick={() => navigate("/cms/users")}
            >
              Users
            </Button>
          </div>
        </div>
      </div>
      <div className="flex flex-col">
        <span className="text-center text-normal font-semibold">
          Subscribe Our Newsletter
        </span>
        <Separator className="my-2"/>
        <span className="text-normal text-center text-gray-100">
          Get Our Latest Updates
        </span>
        <div className="flex mt-5 w-72">
          <Input className="rounded-r-none" placeholder="Enter email"/>
          <Button className="rounded-l-none pl-3">
            Subscribe
          </Button>
        </div>
      </div>
    </footer>
  )
}

export default Footer;