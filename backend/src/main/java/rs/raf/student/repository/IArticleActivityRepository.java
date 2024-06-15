package rs.raf.student.repository;

import rs.raf.student.domain.Pageable;
import rs.raf.student.model.Activity;
import rs.raf.student.model.Article;
import rs.raf.student.model.ArticleActivity;

import java.util.List;

public interface IArticleActivityRepository {

    List<Article> findAllArticles(Long activityId, Pageable pageable);

    List<Long> findAllActivityIds(Long articleId);

    List<Activity> findAllActivities(Long articleId);

    List<Activity> findAllActivitiesByArticleIds(List<Long> articleIds);

    ArticleActivity create(Long articleId, Long activityId);

}
