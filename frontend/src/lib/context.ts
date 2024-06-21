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
