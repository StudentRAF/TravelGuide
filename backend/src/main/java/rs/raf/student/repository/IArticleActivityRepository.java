package rs.raf.student.repository;

import rs.raf.student.domain.Page;
import rs.raf.student.domain.Pageable;
import rs.raf.student.model.Article;
import rs.raf.student.model.ArticleActivity;

import java.util.List;
import java.util.Optional;

public interface IArticleActivityRepository {

    Page<Article> findAllArticles(String activity, Pageable pageable);

    Page<Article> findAllArticles(Long activityId, Pageable pageable);

    List<Article> findAllActivities(Long articleId);

    Optional<ArticleActivity> create(Long articleId, Long activityId);

}
