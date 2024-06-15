package rs.raf.student.repository.article;

import jakarta.inject.Inject;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.article.ArticleCreateDto;
import rs.raf.student.dto.article.ArticleUpdateDto;
import rs.raf.student.exception.ExceptionType;
import rs.raf.student.exception.TGException;
import rs.raf.student.mapper.ArticleMapper;
import rs.raf.student.model.Article;
import rs.raf.student.repository.IArticleRepository;
import rs.raf.student.repository.PostgresAbstractRepository;
import rs.raf.student.repository.ResultSetReader;
import rs.raf.student.sql.PostgresType;
import rs.raf.student.sql.StatementBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
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
                                                                  select *, count(*) over() as count
                                                                  from article
                                                                  """,
                                                                  pageable);
            ResultSet resultSet         = builder.executeQuery()
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
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception, exception.getMessage());
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
                                                                  where id = ?
                                                                  """);
            ResultSet resultSet         = builder.prepareLong(id)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return ResultSetReader.readArticle(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception, exception.getMessage());
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
                                                                  select *, count(*) over() as count
                                                                  from article
                                                                  where id = any(?)
                                                                  """,
                                                                  pageable);
            ResultSet resultSet         = builder.prepareArray(PostgresType.BIGINT, ids)
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
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception, exception.getMessage());
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
                                                                  select *, count(*) over() as count
                                                                  from article
                                                                  where destination_id = ?
                                                                  """,
                                                                  pageable);
            ResultSet resultSet         = builder.prepareLong(destinationId)
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
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception, exception.getMessage());
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
            ResultSet resultSet         = builder.prepareString(article.getTitle())
                                                 .prepareString(article.getContent())
                                                 .prepareLong(article.getAuthorId())
                                                 .prepareLong(article.getDestinationId())
                                                 .prepareDate(article.getCreatedAt())
                                                 .prepareLong(article.getVisits())
                                                 .executeInsert()
        ) {
            if (resultSet.next())
                return article.setId(resultSet.getLong(1));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_ARTICLE_CREATE_NO_RESULT_SET,
                              createDto.getTitle(),
                              createDto.getContent(),
                              createDto.getAuthorId().toString(),
                              createDto.getDestinationId().toString());
    }

    @Override
    public Article update(ArticleUpdateDto updateDto) {
        try(
            Connection       connection = createConnection();
            StatementBuilder builder = StatementBuilder.create(connection,
                                                               """
                                                               update article
                                                               set title = coalesce(?, title), content = coalesce(?, content)
                                                               where id = ?
                                                               """);
            ResultSet resultSet      = builder.prepareString(updateDto.getTitle())
                                              .prepareString(updateDto.getContent())
                                              .prepareLong(updateDto.getId())
                                              .executeInsertReturning(StatementBuilder.create(connection,
                                                                                              """
                                                                                              select *
                                                                                              from article
                                                                                              where id = ?
                                                                                              """)
                                                                                      .prepareLong(updateDto.getId()))
        ) {
            if (resultSet.next())
                return ResultSetReader.readArticle(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception, exception.getMessage());
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
            builder.prepareLong(id)
                   .executeDelete();
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ARTICLE_SQL_EXCEPTION, exception, exception.getMessage());
        }
    }

}
