package rs.raf.student.service;

import jakarta.inject.Inject;
import rs.raf.student.domain.Page;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.article.ArticleCreateDto;
import rs.raf.student.dto.article.ArticleGetDto;
import rs.raf.student.dto.article.ArticleUpdateDto;
import rs.raf.student.mapper.ArticleMapper;
import rs.raf.student.model.Article;
import rs.raf.student.model.Destination;
import rs.raf.student.model.User;
import rs.raf.student.repository.IArticleRepository;
import rs.raf.student.repository.IDestinationRepository;
import rs.raf.student.repository.IUserRepository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArticleService {

    @Inject
    private ArticleMapper mapper;

    @Inject
    private IArticleRepository repository;

    @Inject
    private IUserRepository userRepository;

    @Inject
    private IDestinationRepository destinationRepository;

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
        User        user        = userRepository.findById(article.getAuthorId());
        Destination destination = destinationRepository.findById(article.getDestinationId());

        return mapper.mapDto(article, user, destination);
    }

    private List<ArticleGetDto> mapArticles(List<Article> articles) {
        Map<Long, User> users = userRepository.findByIds(articles.stream()
                                                                 .map(Article::getAuthorId)
                                                                 .collect(Collectors.toSet())
                                                                 .stream()
                                                                 .toList())
                                              .stream()
                                              .collect(Collectors.toMap(User::getId, Function.identity()));

        Map<Long, Destination> destinations = destinationRepository.findByIds(articles.stream()
                                                                                      .map(Article::getDestinationId)
                                                                                      .collect(Collectors.toSet())
                                                                                      .stream()
                                                                                      .toList())
                                                                   .stream()
                                                                   .collect(Collectors.toMap(Destination::getId, Function.identity()));

        return IntStream.range(0, articles.size())
                        .mapToObj(index -> mapper.mapDto(articles.get(index), users.get((long) index), destinations.get((long) index)))
                        .toList();
    }

}
