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

package rs.raf.student.mapper;

import jakarta.inject.Inject;
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.comment.CommentGetDto;
import rs.raf.student.model.Comment;

import java.time.LocalDate;

public class CommentMapper {

    @Inject
    private UserMapper userMapper;

    public CommentGetDto mapDto(Comment comment) {
        return new CommentGetDto
            (
                comment.getId(),
                comment.getContent(),
                comment.getDisplayName(),
                comment.getCreatedAt()

            );
    }

    public Comment map(CommentCreateDto createDto) {
        return map(new Comment(), createDto);
    }

    public Comment map(Comment comment, CommentCreateDto createDto) {
        comment.setContent(createDto.getContent());
        comment.setDisplayName(createDto.getDisplayName());
        comment.setArticleId(createDto.getArticleId());
        comment.setCreatedAt(LocalDate.now());

        return comment;
    }

}
