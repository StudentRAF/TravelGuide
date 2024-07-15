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

package rs.raf.student.repository.user_role;

import rs.raf.student.exception.ExceptionType;
import rs.raf.student.exception.TGException;
import rs.raf.student.model.UserRole;
import rs.raf.student.repository.IUserRoleRepository;
import rs.raf.student.repository.PostgresAbstractRepository;
import rs.raf.student.repository.ResultSetReader;
import rs.raf.student.sql.PostgresType;
import rs.raf.student.sql.StatementBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PostgresUserRoleRepository extends PostgresAbstractRepository implements IUserRoleRepository {

    @Override
    public List<UserRole> findAll() throws TGException {
        List<UserRole> roles = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from user_role
                                                                  """);
            ResultSet resultSet         = builder.executeQuery()
        ) {
            while (resultSet.next())
                roles.add(ResultSetReader.readUserRole(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_USER_ROLE_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return roles;
    }

    @Override
    public List<UserRole> findByIds(List<Long> ids) throws TGException {
        List<UserRole> roles = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from user_role
                                                                  where id = any(?)
                                                                  """);
            ResultSet resultSet         = builder.prepareArray(PostgresType.BIGINT, ids)
                                                 .executeQuery()
        ) {
            while (resultSet.next())
                roles.add(ResultSetReader.readUserRole(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_USER_ROLE_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return roles;
    }

    @Override
    public UserRole findById(Long id) throws TGException {
        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from user_role
                                                                  where id = ?
                                                                  """);
            ResultSet resultSet         = builder.prepareLong(id)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return ResultSetReader.readUserRole(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_USER_ROLE_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_USER_ROLE_FIND_ID_NOT_FOUND, id.toString());
    }

    @Override
    public UserRole findByName(String name) throws TGException {
        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from user_role
                                                                  where name like ?
                                                                  """);
            ResultSet resultSet         = builder.prepareString(name)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                if (resultSet.next())
                    return ResultSetReader.readUserRole(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_USER_ROLE_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_USER_ROLE_FIND_NAME_NOT_FOUND, name);
    }

}
