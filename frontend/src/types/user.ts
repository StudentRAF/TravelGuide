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

export type UserRole = {
  id:   number,
  name: string,
}

export type User = {
  id:         number,
  first_name: string,
  last_name:  string,
  email:      string,
  user_role:  UserRole,
  enabled:    boolean,
}

export type UserCreate = {
  first_name:       string,
  last_name:        string,
  email:            string,
  password:         string,
  confirm_password: string,
  role_id:          number,
}

export type UserUpdate = {
  id:          number,
  first_name?: string,
  last_name?:  string,
  email?:      string,
  role_id?:    number,
  enabled?:    boolean,
}

export type UserLogin = {
  email:    string,
  password: string,
}

const ADMIN: UserRole = {
  id: 1,
  name: 'Admin',
}

const EDITOR: UserRole = {
  id: 2,
  name: 'Editor',
}

const roles: UserRole[] = [
  ADMIN, EDITOR,
]

export const isAdmin = (role: UserRole): boolean => {
  return role.id   === ADMIN.id &&
         role.name === ADMIN.name;
}

export const isEditor = (role: UserRole): boolean => {
  return role.id   === EDITOR.id &&
         role.name === EDITOR.name;
}

export const getRole = (name: string) : UserRole => {
  return roles.filter((role) => role.name === name)[0]
}
