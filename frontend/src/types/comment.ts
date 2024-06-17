export type Comment = {
  id:           number,
  content:      string,
  display_name: string,
  created_at:   Date,
}

export type CommentCreate = {
  article_id:   number,
  content:      string,
  display_name: string,
}
