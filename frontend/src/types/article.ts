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
  destination_id: number,
  activities:     number[],
}

export type ArticleUpdate = {
  id:         number,
  title:      string,
  content:    string,
  activities: number[],
}
