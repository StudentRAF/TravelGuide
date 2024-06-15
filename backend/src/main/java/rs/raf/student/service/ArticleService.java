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

import java.util.ArrayList;
import java.util.HashMap;
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

    public ArticleGetDto create(ArticleCreateDto createDto) {
        return mapArticle(repository.create(createDto));
    }

    public ArticleGetDto update(ArticleUpdateDto updateDto) {
        return mapArticle(repository.update(updateDto));
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    private ArticleGetDto mapArticle(Article article) {
        User           user        = userRepository.findById(article.getAuthorId());
        Destination    destination = destinationRepository.findById(article.getDestinationId());
        List<Activity> activities  = articleActivityRepository.findAllActivities(article.getId());
        List<Comment>  comments    = commentRepository.findByArticle(article.getId());

        return mapper.mapDto(article, user, destination, activities, comments);
    }

    private List<ArticleGetDto> mapArticles(List<Article> articles) {
        Map<Long, List<Activity>> activities       = new HashMap<>();
        Map<Long, User>           users            = new HashMap<>();
        Map<Long, Destination>    destinations     = new HashMap<>();
        Map<Long, List<Comment>>  comments         = new HashMap<>();
        Map<Long, List<Long>>     activityIds      = new HashMap<>();
        List<Activity>            uniqueActivities = new ArrayList<>();

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

            activityIds.putAll(futureActivityIds.get());

            var futureUniqueActivities = executors.submit(() -> articleActivityRepository.findAllActivitiesByArticleIds(activityIds.values()
                                                                                                                                   .stream()
                                                                                                                                   .flatMap(List::stream)
                                                                                                                                   .collect(Collectors.toSet())
                                                                                                                                   .stream()
                                                                                                                                   .toList()));
            users.putAll(futureUsers.get());
            destinations.putAll(futureDestinations.get());
            comments.putAll(futureComments.get());
            uniqueActivities.addAll(futureUniqueActivities.get());
        }
        catch (ExecutionException | InterruptedException exception) {
            exception.printStackTrace(System.err);
        }

        activities.putAll(articles.stream()
                                  .map(article -> ImmutablePair.of(article.getId(), uniqueActivities.stream()
                                                                                                    .filter(activity -> activityIds.get(article.getId())
                                                                                                                                   .contains(activity.getId()))
                                                                                                    .toList()))
                                  .collect(Collectors.toMap(ImmutablePair::getKey, ImmutablePair::getValue)));

        return articles.stream()
                       .map(article -> mapper.mapDto(article,
                                                     users.get(article.getAuthorId()),
                                                     destinations.get(article.getDestinationId()),
                                                     activities.get(article.getId()),
                                                     comments.get(article.getId())))
                       .toList();
    }

}
