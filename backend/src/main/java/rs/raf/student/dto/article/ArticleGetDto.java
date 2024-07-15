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

package rs.raf.student.dto.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.student.adapter.time.LocalDateSerializer;
import rs.raf.student.dto.activity.ActivityGetDto;
import rs.raf.student.dto.comment.CommentGetDto;
import rs.raf.student.dto.destination.DestinationGetDto;
import rs.raf.student.dto.user.UserGetDto;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleGetDto {

    private Long id;

    private String title;

    private String content;

    private UserGetDto author;

    private DestinationGetDto destination;

    private List<ActivityGetDto> activities;

    private List<CommentGetDto> comments;

    @JsonProperty("created_at")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate createdAt;

    private Long visits;

}
