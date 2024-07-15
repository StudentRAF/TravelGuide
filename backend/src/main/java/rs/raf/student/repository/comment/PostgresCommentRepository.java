/*
 * Copyright (C) 2024. Nemanja Radovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rs.raf.student.repository.comment;

import jakarta.inject.Inject;
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.exception.ExceptionType;
import rs.raf.student.exception.TGException;
import rs.raf.student.mapper.CommentMapper;
import rs.raf.student.model.Comment;
import rs.raf.student.repository.ICommentRepository;
import rs.raf.student.repository.PostgresAbstractRepository;
import rs.raf.student.repository.ResultSetReader;
import rs.raf.student.sql.StatementBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PostgresCommentRepository extends PostgresAbstractRepository implements ICommentRepository {

    @Inject
    private CommentMapper mapper;

    @Override
    public List<Comment> findByArticle(Long articleId) {
        List<Comment> comments = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from comment
                                                                  where article_id = ?
                                                                  """);
            ResultSet resultSet         = builder.prepareLong(articleId)
                                                 .executeQuery()
        ) {
            while (resultSet.next())
                comments.add(ResultSetReader.readComments(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_COMMENT_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return comments;
    }

    @Override
    public Comment create(CommentCreateDto createDto) {
        Comment comment = mapper.map(createDto);

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  insert into comment(content, display_name, article_id)
                                                                  values (?, ?, ?)
                                                                  """);
            ResultSet resultSet         = builder.prepareString(createDto.getContent())
                                                 .prepareString(createDto.getDisplayName())
                                                 .prepareLong(createDto.getArticleId())
                                                 .executeInsert()
        ) {
            if (resultSet.next())
                return comment.setId(resultSet.getLong(1));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_COMMENT_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_COMMENT_CREATE_NO_RESULT_SET,
                              createDto.getContent(),
                              createDto.getDisplayName(),
                              createDto.getArticleId().toString());
    }

}
