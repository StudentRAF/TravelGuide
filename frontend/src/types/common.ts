export type Page<Type> = {
  content:        Type[],
  total_elements: number,
  total_pages:    number,
  page_size:      number,
  page:           number,
  content_size:   number,
  empty:          boolean,
}
