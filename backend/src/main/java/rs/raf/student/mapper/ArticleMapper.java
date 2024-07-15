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
import rs.raf.student.dto.article.ArticleCreateDto;
import rs.raf.student.dto.article.ArticleGetDto;
import rs.raf.student.dto.article.ArticleUpdateDto;
import rs.raf.student.model.Activity;
import rs.raf.student.model.Article;
import rs.raf.student.model.Comment;
import rs.raf.student.model.Destination;
import rs.raf.student.model.User;

import java.time.LocalDate;
import java.util.List;

public class ArticleMapper {

    @Inject
    private UserMapper userMapper;

    @Inject
    private DestinationMapper destinationMapper;

    @Inject
    private ActivityMapper activityMapper;

    @Inject
    private CommentMapper commentMapper;

    public ArticleGetDto mapDto(Article article, User user, Destination destination, List<Activity> activities, List<Comment> comments) {

        return new ArticleGetDto
            (
                article.getId(),
                article.getTitle(),
                article.getContent(),
                userMapper.mapDto(user, null),
                destinationMapper.mapDto(destination),
                activities.stream()
                          .map(activityMapper::mapDto)
                          .toList(),
                comments.stream()
                        .map(commentMapper::mapDto)
                        .toList(),
                article.getCreatedAt(),
                article.getVisits()
            );
    }

    public Article map(ArticleCreateDto createDto) {
        return map(new Article(), createDto);
    }

    public Article map(Article article, ArticleCreateDto createDto) {
        article.setTitle(createDto.getTitle());
        article.setContent(createDto.getContent());
        article.setAuthorId(createDto.getAuthorId());
        article.setDestinationId(createDto.getDestinationId());
        article.setCreatedAt(LocalDate.now());
        article.setVisits(0L);

        return article;
    }

    public Article map(ArticleUpdateDto updateDto) {
        return map(new Article(), updateDto);
    }

    public Article map(Article article, ArticleUpdateDto updateDto) {
        article.setTitle(updateDto.getTitle());
        article.setContent(updateDto.getContent());

        return article;
    }

}
