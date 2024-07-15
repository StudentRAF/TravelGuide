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

import { useNavigate } from "react-router-dom";
import { ApplicationContext, ApplicationContextData } from "@/lib/context.ts";
import { useContext, useEffect } from "react";
import { isAdmin, isEditor, UserRole } from "@/types/user.ts";

const CMS = () => {
  const navigate = useNavigate();
  const application: ApplicationContextData = useContext(ApplicationContext);

  useEffect(() => {
    const role: UserRole | undefined = application.data?.user.user_role;

    if (!role)
      navigate("/cms/login");
    else if (isAdmin(role))
      navigate("/cms/users");
    else if (isEditor(role))
      navigate("/cms/destinations");

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <span>
      If you see this text, something went wrong.
    </span>
  )
}

export default CMS;
