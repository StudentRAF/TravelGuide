export type Destination = {
  id:          number,
  name:        string,
  description: string,
}

export type DestinationCreate = {
  name:        string,
  description: string,
}

export type DestinationUpdate = {
  id:           number,
  name?:        string,
  description?: string,
}
