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
  user_id:          number,
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
