package rs.raf.student.repository.article_activity;

import rs.raf.student.domain.Page;
import rs.raf.student.domain.Pageable;
import rs.raf.student.model.Article;
import rs.raf.student.model.ArticleActivity;
import rs.raf.student.repository.IArticleActivityRepository;
import rs.raf.student.repository.PostgresAbstractRepository;

import java.util.List;
import java.util.Optional;

public class PostgresArticleActivityRepository extends PostgresAbstractRepository implements IArticleActivityRepository {

    @Override
    public Page<Article> findAllArticles(String activity, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Article> findAllArticles(Long activityId, Pageable pageable) {
        return null;
    }

    @Override
    public List<Article> findAllActivities(Long articleId) {
        return List.of();
    }

    @Override
    public Optional<ArticleActivity> create(Long articleId, Long activityId) {
        return Optional.empty();
    }

}
