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

package rs.raf.student.repository;

import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.article.ArticleCreateDto;
import rs.raf.student.dto.article.ArticleUpdateDto;
import rs.raf.student.model.Article;

import java.util.List;

public interface IArticleRepository {

    List<Article> findAll(Pageable pageable); //NOTE: Pageable TODO
                                              //NOTE: Pageable + Sort by date TODO
                                              //NOTE: Pageable + Sort by views, filter 30days TODO

    List<Article> findByIds(List<Long> ids, Pageable pageable);

    List<Article> findByDestination(Long destinationId, Pageable pageable);

    Article findById(Long id);

    Article create(ArticleCreateDto createDto);

    Article update(ArticleUpdateDto updateDto);

    void delete(Long id);

}
