package rs.raf.student.repository.article;

import jakarta.inject.Inject;
import rs.raf.student.domain.Pageable;
import rs.raf.student.domain.StatementBuilder;
import rs.raf.student.dto.article.ArticleCreateDto;
import rs.raf.student.dto.article.ArticleUpdateDto;
import rs.raf.student.exception.ExceptionType;
import rs.raf.student.exception.TGException;
import rs.raf.student.mapper.ArticleMapper;
import rs.raf.student.model.Article;
import rs.raf.student.repository.IArticleRepository;
import rs.raf.student.repository.PostgresAbstractRepository;
import rs.raf.student.sql.PostgresType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresArticleRepository extends PostgresAbstractRepository implements IArticleRepository {

    @Inject
    private ArticleMapper mapper;

    @Override
    public List<Article> findAll(Pageable pageable) {
        List<Article> articles = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from article
                                                                  """,
                                                                  pageable.getPageNumber(),
                                                                  pageable.getPageSize());
            ResultSet resultSet         = builder.executeQuery()
        ) {
            while (resultSet.next())
                 articles.add(loadArticle(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception.getMessage());
        }

        return articles;
    }

    @Override
    public Article findById(Long id) {
        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from article
                                                                  where id = ?;
                                                                  """);
            ResultSet resultSet         = builder.setLong(id)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return loadArticle(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_ARTICLE_FIND_ID_NOT_FOUND, id.toString());
    }

    @Override
    public List<Article> findByIds(List<Long> ids, Pageable pageable) {
        List<Article> articles = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from article
                                                                  where id = any(?)
                                                                  """,
                                                                  pageable.getPageNumber(),
                                                                  pageable.getPageSize());
            ResultSet resultSet         = builder.setArray(PostgresType.BIGINT, ids)
                                                 .executeQuery()
        ) {
            while (resultSet.next())
                articles.add(loadArticle(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception.getMessage());
        }

        return articles;
    }

    @Override
    public List<Article> findByDestination(Long destinationId, Pageable pageable) {
        List<Article> articles = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from article
                                                                  where destination_id = ?;
                                                                  """,
                                                                  pageable.getPageNumber(),
                                                                  pageable.getPageSize());
            ResultSet resultSet         = builder.setLong(destinationId)
                                                 .executeQuery()
        ) {
            while (resultSet.next())
                articles.add(loadArticle(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception.getMessage());
        }

        return articles;
    }

    @Override
    public Article create(ArticleCreateDto createDto) {
        Article article = mapper.map(createDto);

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  insert into article(title, content, author_id, destination_id, created_at, visits)
                                                                  values (?, ?, ?, ?, ?, ?)
                                                                  """);
            ResultSet resultSet         = builder.setString(article.getTitle())
                                                 .setString(article.getContent())
                                                 .setLong(article.getAuthorId())
                                                 .setLong(article.getDestinationId())
                                                 .setDate(article.getCreatedAt())
                                                 .setLong(article.getVisits())
                                                 .executeInsert()
        ) {
            if (resultSet.next())
                return article.setId(resultSet.getLong(1));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_ARTICLE_CREATE_NO_RESULT_SET,
                              createDto.getTitle(),
                              createDto.getContent(),
                              createDto.getAuthorId().toString(),
                              createDto.getDestinationId().toString());
    }

    @Override
    public Article update(ArticleUpdateDto updateDto) {
        Article article;

        try {
            article = findById(updateDto.getId());
        }
        catch (TGException exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_UPDATE_ID_NOT_FOUND,
                                  updateDto.getId().toString(),
                                  updateDto.getTitle(),
                                  updateDto.getContent());
        }

        try(
            Connection       connection = createConnection();
            StatementBuilder builder = StatementBuilder.create(connection,
                                                               """
                                                               update article
                                                               set title = ?, content = ?
                                                               where id = ?
                                                               """);
            ResultSet resultSet      = builder.setString(article.getTitle())
                                              .setString(article.getContent())
                                              .setLong(article.getId())
                                              .executeInsertReturning(StatementBuilder.create(connection,
                                                                                              """
                                                                                              select *
                                                                                              from article
                                                                                              where id = ?
                                                                                              """)
                                                                                      .setLong(article.getId()))
        ) {
            if (resultSet.next())
                return loadArticle(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_ARTICLE_UPDATE_NO_RESULT_SET,
                              updateDto.getId().toString(),
                              updateDto.getTitle(),
                              updateDto.getContent());
    }

    @Override
    public void delete(Long id) {
        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  delete from article
                                                                  where id = ?
                                                                  """)
        ) {
            builder.setLong(id)
                   .executeDelete();
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception.getMessage());
        }
    }

    private Article loadArticle(ResultSet resultSet) throws SQLException {
        return new Article
            (
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getLong("author_id"),
                resultSet.getLong("destination_id"),
                resultSet.getDate("created_at").toLocalDate(),
                resultSet.getLong("visits")
            );
    }

}
