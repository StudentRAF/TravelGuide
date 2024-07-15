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

import React from "react";
import { User } from "@/types/user.ts";
import { getLocalStorageData, USER, USER_TOKEN } from "@/lib/local_storage.ts";

export type ApplicationData = {
  user: User,
  authorization: string,
}

export type ApplicationContextData = {
  setData: React.Dispatch<React.SetStateAction<ApplicationData | undefined>>,
  data?: ApplicationData,
}

export const getDefaultApplicationData = (): ApplicationData | undefined => {
  const user  = getLocalStorageData(USER,       undefined);
  const token = getLocalStorageData(USER_TOKEN, undefined);

  if (!user || !token)
    return undefined;

  return {
    user: user,
    authorization: token,
  }
};

export const ApplicationContext = React.createContext<ApplicationContextData>({
  setData: () => {},
  data: getDefaultApplicationData(),
});
