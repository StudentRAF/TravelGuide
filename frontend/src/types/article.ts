import { Destination } from "@/types/destination.ts";
import { Activity } from "@/types/activity.ts";
import { Comment } from "@/types/comment.ts";
import { User } from "@/types/user.ts";

export type Article = {
  id:          number,
  title:       string,
  content:     string,
  author:      User,
  destination: Destination,
  activities:  Activity[],
  comments:    Comment[],
  created_at:  Date,
  visits:      number,
}

export type ArticleCreate = {
  title:          string,
  content:        string,
  author_id:      number,
  description_id: number,
  //TODO: Activities
}

export type ArticleUpdate = {
  id:         number,
  title:      string,
  content:    string,
  activities: number[],
}
