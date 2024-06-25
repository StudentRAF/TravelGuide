package rs.raf.student.repository.article_activity;

import rs.raf.student.domain.Pageable;
import rs.raf.student.exception.ExceptionType;
import rs.raf.student.exception.TGException;
import rs.raf.student.model.Activity;
import rs.raf.student.model.Article;
import rs.raf.student.model.ArticleActivity;
import rs.raf.student.repository.IArticleActivityRepository;
import rs.raf.student.repository.PostgresAbstractRepository;
import rs.raf.student.repository.ResultSetReader;
import rs.raf.student.sql.PostgresType;
import rs.raf.student.sql.StatementBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PostgresArticleActivityRepository extends PostgresAbstractRepository implements IArticleActivityRepository {

    @Override
    public List<Article> findAllArticles(Long activityId, Pageable pageable) {
        List<Article> articles = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select article.*, count(*) over () as count
                                                                  from article_activity
                                                                  join article on article_activity.article_id = article.id
                                                                  where activity_id = ?
                                                                  """,
                                                                  pageable);
            ResultSet resultSet         = builder.prepareLong(activityId)
                                                 .executeQuery()
        ) {
            do {
                if (!resultSet.next())
                    break;

                articles.add(ResultSetReader.readArticle(resultSet));
            } while (!resultSet.isLast());

            if (resultSet.isLast())
                pageable.setTotalElements(resultSet.getInt("count"));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_ACTIVITY_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return articles;
    }

    @Override
    public List<Long> findAllActivityIds(Long articleId) {
        List<Long> ids = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select activity.id
                                                                  from article_activity
                                                                  join activity on article_activity.activity_id = activity.id
                                                                  where article_id = ?
                                                                  """);
            ResultSet resultSet         = builder.prepareLong(articleId)
                                                 .executeQuery()
        ) {
            while (resultSet.next())
                ids.add(resultSet.getLong("id"));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_ACTIVITY_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return ids;
    }

    @Override
    public List<Activity> findAllActivities(Long articleId) {
        List<Activity> activities = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select activity.*
                                                                  from article_activity
                                                                  join activity on article_activity.activity_id = activity.id
                                                                  where article_id = ?
                                                                  """);
            ResultSet resultSet         = builder.prepareLong(articleId)
                                                 .executeQuery()
        ) {
            while (resultSet.next())
                activities.add(ResultSetReader.readActivity(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_ACTIVITY_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return activities;
    }

    @Override
    public List<Activity> findAllActivitiesByArticleIds(List<Long> articleIds) {
        List<Activity> activities = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select distinct activity.*
                                                                  from article_activity
                                                                  join activity on article_activity.activity_id = activity.id
                                                                  where activity_id = any(?)
                                                                  """);
            ResultSet resultSet         = builder.prepareArray(PostgresType.BIGINT, articleIds)
                                                 .executeQuery()
        ) {
            while (resultSet.next())
                activities.add(ResultSetReader.readActivity(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_ACTIVITY_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return activities;
    }

    @Override
    public ArticleActivity create(Long articleId, Long activityId) {
        ArticleActivity activity = new ArticleActivity(null, articleId, activityId);

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  insert into article_activity(article_id, activity_id)
                                                                  values (?, ?)
                                                                  """);
            ResultSet resultSet         = builder.prepareLong(articleId)
                                                 .prepareLong(activityId)
                                                 .executeInsert()
        ) {
            if (resultSet.next())
                return activity.setId(resultSet.getLong(1));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_ACTIVITY_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_ARTICLE_ACTIVITY_CREATE_NO_RESULT_SET,
                              articleId.toString(),
                              activityId.toString());
    }

    @Override
    public List<ArticleActivity> create(Long articleId, List<Long> activityIds) {
        if (activityIds == null || activityIds.isEmpty())
            return null;

        List<ArticleActivity> articleActivities = activityIds.stream().map(activityId -> new ArticleActivity(null, articleId, activityId))
                                                             .toList();

        try(
            Connection       connection = createConnection(false);
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  insert into article_activity(article_id, activity_id)
                                                                  values (?, ?)
                                                                  """);
        ) {
            for (Long activityId : activityIds)
                builder.prepareLong(articleId)
                       .prepareLong(activityId)
                       .prepareBatch();

            ResultSet resultSet = builder.executeBatchReturning(StatementBuilder.create(connection,
                                                                                        """
                                                                                        select *
                                                                                        from article_activity
                                                                                        where article_id = ? and activity_id = any(?)
                                                                                        """)
                                                                                .prepareLong(articleId)
                                                                                .prepareArray(PostgresType.BIGINT, activityIds));

            int index = 0;
            while (resultSet.next())
                articleActivities.get(index++)
                                 .setId(resultSet.getLong("id"));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_ACTIVITY_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return articleActivities;
    }

    @Override
    public void delete(Long articleId, List<Long> activityIds) {
        if (activityIds == null || activityIds.isEmpty())
            return;

        try(
            Connection       connection = createConnection(false);
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  delete from article_activity
                                                                  where article_id = ? and activity_id = ?
                                                                  """)
        ) {
            for (Long activityId : activityIds)
                builder.prepareLong(articleId)
                       .prepareLong(activityId)
                       .prepareBatch();

            builder.executeBatch();
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_ACTIVITY_SQL_EXCEPTION, exception, exception.getMessage());
        }
    }

}
