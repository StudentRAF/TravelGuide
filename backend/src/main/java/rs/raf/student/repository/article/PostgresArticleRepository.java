package rs.raf.student.repository.article;

import rs.raf.student.domain.Page;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.article.ArticleCreateDto;
import rs.raf.student.dto.article.ArticleUpdateDto;
import rs.raf.student.model.Article;
import rs.raf.student.repository.IArticleRepository;
import rs.raf.student.repository.PostgresAbstractRepository;

import java.util.Optional;

public class PostgresArticleRepository extends PostgresAbstractRepository implements IArticleRepository {

    @Override
    public Page<Article> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Article> findByActivity(Long activityId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Article> findByDestination(Long destinationId, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Article> create(ArticleCreateDto createDto) {
        return Optional.empty();
    }

    @Override
    public Optional<Article> update(ArticleUpdateDto updateDto) {
        return Optional.empty();
    }

    @Override
    public Optional<Article> delete(Long id) {
        return Optional.empty();
    }

}
