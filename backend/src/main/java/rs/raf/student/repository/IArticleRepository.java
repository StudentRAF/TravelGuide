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
