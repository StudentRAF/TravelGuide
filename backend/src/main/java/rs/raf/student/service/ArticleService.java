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

package rs.raf.student.service;

import jakarta.inject.Inject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import rs.raf.student.domain.Page;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.article.ArticleCreateDto;
import rs.raf.student.dto.article.ArticleGetDto;
import rs.raf.student.dto.article.ArticleUpdateDto;
import rs.raf.student.mapper.ArticleMapper;
import rs.raf.student.model.Activity;
import rs.raf.student.model.Article;
import rs.raf.student.model.Comment;
import rs.raf.student.model.Destination;
import rs.raf.student.model.User;
import rs.raf.student.repository.IArticleActivityRepository;
import rs.raf.student.repository.IArticleRepository;
import rs.raf.student.repository.ICommentRepository;
import rs.raf.student.repository.IDestinationRepository;
import rs.raf.student.repository.IUserRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArticleService {

    @Inject
    private ArticleMapper mapper;

    @Inject
    private IArticleRepository repository;

    @Inject
    private IArticleActivityRepository articleActivityRepository;

    @Inject
    private ICommentRepository commentRepository;

    @Inject
    private IDestinationRepository destinationRepository;

    @Inject
    private IUserRepository userRepository;

    public Page<ArticleGetDto> getAll(Pageable pageable) {
        return Page.of(mapArticles(repository.findAll(pageable)),
                       pageable);
    }

    public ArticleGetDto getById(Long id) {
        return mapArticle(repository.findById(id));
    }

    public Page<ArticleGetDto> getByDestinationId(Long destinationId, Pageable pageable) {
        return Page.of(mapArticles(repository.findByDestination(destinationId, pageable)),
                       pageable);
    }

    public Page<ArticleGetDto> getByActivityId(Long activityId, Pageable pageable) {
        return Page.of(mapArticles(articleActivityRepository.findAllArticles(activityId, pageable)),
                       pageable);
    }

    public ArticleGetDto create(ArticleCreateDto createDto) {
        Article article = repository.create(createDto);

        articleActivityRepository.create(article.getId(), createDto.getActivities());

        return mapArticle(article);
    }

    public ArticleGetDto update(ArticleUpdateDto updateDto) {
        if (updateDto.getActivities() == null)
            return mapArticle(repository.update(updateDto));

        try (ExecutorService executors = Executors.newFixedThreadPool(3)) {
            var futureArticle     = executors.submit(() -> repository.update(updateDto));
            var futureActivityIds = executors.submit(() -> articleActivityRepository.findAllActivityIds(updateDto.getId()));

            List<Long> activityIds      = futureActivityIds.get();
            List<Long> addActivities    = updateDto.getActivities()
                                                   .stream()
                                                   .filter(activityId -> !activityIds.contains(activityId))
                                                   .toList();
            List<Long> removeActivities = activityIds.stream()
                                                     .filter(activityId -> !updateDto.getActivities()
                                                                                     .contains(activityId))
                                                     .toList();

            var futureAddedActivities   = executors.submit(() -> articleActivityRepository.create(updateDto.getId(), addActivities));
            var futureRemovedActivities = executors.submit(() -> articleActivityRepository.delete(updateDto.getId(), removeActivities));

            futureAddedActivities.get();
            futureRemovedActivities.get();

            return mapArticle(futureArticle.get());
        }
        catch (ExecutionException | InterruptedException exception) {
            exception.printStackTrace(System.err);
        }

        return null;
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    private ArticleGetDto mapArticle(Article article) {
        try (ExecutorService executors = Executors.newFixedThreadPool(4)) {
            var futureUser        = executors.submit(() -> userRepository.findById(article.getAuthorId()));
            var futureDestination = executors.submit(() -> destinationRepository.findById(article.getDestinationId()));
            var futureActivities  = executors.submit(() -> articleActivityRepository.findAllActivities(article.getId()));
            var futureComments    = executors.submit(() -> commentRepository.findByArticle(article.getId()));

            User           user        = futureUser.get();
            Destination    destination = futureDestination.get();
            List<Activity> activities  = futureActivities.get();
            List<Comment>  comments    = futureComments.get();

            return mapper.mapDto(article, user, destination, activities, comments);
        }
        catch (ExecutionException | InterruptedException exception) {
            exception.printStackTrace(System.err);
        }

        return null;
    }

    private List<ArticleGetDto> mapArticles(List<Article> articles) {
        try (ExecutorService executors = Executors.newFixedThreadPool(4)) {
            var futureActivityIds = executors.submit(() -> articles.parallelStream()
                                                                   .map(article -> ImmutablePair.of(article.getId(), articleActivityRepository.findAllActivityIds(article.getId())))
                                                                   .collect(Collectors.toMap(ImmutablePair::getLeft, ImmutablePair::getRight)));

            var futureUsers = executors.submit(() -> userRepository.findByIds(articles.stream()
                                                                                      .map(Article::getAuthorId)
                                                                                      .collect(Collectors.toSet())
                                                                                      .stream()
                                                                                      .toList())
                                                                   .stream()
                                                                   .collect(Collectors.toMap(User::getId, Function.identity())));

            var futureDestinations = executors.submit(() -> destinationRepository.findByIds(articles.stream()
                                                                                                    .map(Article::getDestinationId)
                                                                                                    .collect(Collectors.toSet())
                                                                                                    .stream()
                                                                                                    .toList())
                                                                                 .stream()
                                                                                 .collect(Collectors.toMap(Destination::getId, Function.identity())));

            var futureComments = executors.submit(() -> articles.parallelStream()
                                                                .map(article -> ImmutablePair.of(article.getId(), commentRepository.findByArticle(article.getId())))
                                                                .collect(Collectors.toMap(ImmutablePair::getKey, ImmutablePair::getValue)));

            Map<Long, List<Long>> activityIds = futureActivityIds.get();

            var futureUniqueActivities = executors.submit(() -> articleActivityRepository.findAllActivitiesByArticleIds(activityIds.values()
                                                                                                                                   .stream()
                                                                                                                                   .flatMap(List::stream)
                                                                                                                                   .collect(Collectors.toSet())
                                                                                                                                   .stream()
                                                                                                                                   .toList()));

            Map<Long, User>           users            = futureUsers.get();
            Map<Long, Destination>    destinations     = futureDestinations.get();
            Map<Long, List<Comment>>  comments         = futureComments.get();
            List<Activity>            uniqueActivities = futureUniqueActivities.get();

            Map<Long, List<Activity>> activities       = articles.stream()
                                                                 .map(article -> ImmutablePair.of(article.getId(), uniqueActivities.stream()
                                                                                                                                   .filter(activity -> activityIds.get(article.getId())
                                                                                                                                                                  .contains(activity.getId()))
                                                                                                                                   .toList()))
                                                                 .collect(Collectors.toMap(ImmutablePair::getKey, ImmutablePair::getValue));
            return articles.stream()
                           .map(article -> mapper.mapDto(article,
                                                         users.get(article.getAuthorId()),
                                                         destinations.get(article.getDestinationId()),
                                                         activities.get(article.getId()),
                                                         comments.get(article.getId())))
                           .toList();
        }
        catch (ExecutionException | InterruptedException exception) {
            exception.printStackTrace(System.err);
        }

        return List.of();
    }

}
