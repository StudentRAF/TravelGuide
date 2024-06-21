import React from "react";
import { User } from "@/types/user.ts";

export type ApplicationData = {
  user: User,
  authorization: string,
}

export type ApplicationContextData = {
  setData: React.Dispatch<React.SetStateAction<ApplicationData | undefined>>,
  data?: ApplicationData,
}

export const defaultAppData : ApplicationData | undefined = undefined;

export const ApplicationContext = React.createContext<ApplicationContextData>({
  setData: () => {},
  data: defaultAppData,
});
