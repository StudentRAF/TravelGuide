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
  id?:         number,
  first_name?: string,
  last_name?:  string,
  email?:      string,
  user_id?:    number,
  enabled?:    boolean,
}

export type UserLogin = {
  email:    string,
  password: string,
}
