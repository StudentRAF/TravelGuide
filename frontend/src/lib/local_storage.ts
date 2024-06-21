import { User } from "@/types/user.ts";

export const USER_TOKEN = "user_token";
export const USER       = "user";

export type LocalStorageTypes = {
  [USER_TOKEN] ?: string
  [USER]       ?: User
};

export const getLocalStorageData = <Key extends keyof LocalStorageTypes>(
  item: Key,
  fallbackValue: LocalStorageTypes[Key],
):  LocalStorageTypes[Key] => {
  const data = localStorage.getItem(item);

  return data ? (JSON.parse(data) as LocalStorageTypes[Key])
    : fallbackValue;
}

export const setLocalStorageDataAsync = async <Key extends keyof LocalStorageTypes>(
  item: Key,
  value: LocalStorageTypes[Key],
): Promise<void> => localStorage.setItem(item, JSON.stringify(value));

export const removeLocalStorageDataAsync = async <Key extends keyof LocalStorageTypes>(
  item: Key,
): Promise<void> => localStorage.removeItem(item);

export const clearUserDataAsync = async (): Promise<void> => {
  removeLocalStorageDataAsync(USER);
  removeLocalStorageDataAsync(USER_TOKEN);
}
