package rs.raf.student.repository;

import rs.raf.student.domain.Page;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.article.ArticleCreateDto;
import rs.raf.student.dto.article.ArticleUpdateDto;
import rs.raf.student.model.Article;

import java.util.Optional;

public interface IArticleRepository {

    Page<Article> findAll(Pageable pageable); //NOTE: Pageable TODO
                                              //NOTE: Pageable + Sort by date TODO
                                              //NOTE: Pageable + Sort by views, filter 30days TODO

    Page<Article> findByActivity(Long activityId, Pageable pageable);

    Page<Article> findByDestination(Long destinationId, Pageable pageable);

    Optional<Article> findById(Long id);

    Optional<Article> create(ArticleCreateDto createDto);

    Optional<Article> update(ArticleUpdateDto updateDto);

    Optional<Article> delete(Long id);

}
