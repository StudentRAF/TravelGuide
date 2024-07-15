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

package rs.raf.student.repository.activity;

import jakarta.inject.Inject;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.activity.ActivityCreateDto;
import rs.raf.student.exception.ExceptionType;
import rs.raf.student.exception.TGException;
import rs.raf.student.mapper.ActivityMapper;
import rs.raf.student.model.Activity;
import rs.raf.student.repository.IActivityRepository;
import rs.raf.student.repository.PostgresAbstractRepository;
import rs.raf.student.repository.ResultSetReader;
import rs.raf.student.sql.StatementBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PostgresActivityRepository extends PostgresAbstractRepository implements IActivityRepository {

    @Inject
    private ActivityMapper mapper;

    @Override
    public List<Activity> findAll(Pageable pageable) {
        List<Activity> activities = new ArrayList<>();

        try(
            Connection connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *, count(*) over() as count
                                                                  from activity
                                                                  """,
                                                                  pageable);
            ResultSet resultSet         = builder.executeQuery()
        ) {
            do {
                if (!resultSet.next())
                    break;

                activities.add(ResultSetReader.readActivity(resultSet));
            } while (!resultSet.isLast());

            if (resultSet.isLast())
                pageable.setTotalElements(resultSet.getInt("count"));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ACTIVITY_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return activities;
    }

    @Override
    public Activity findById(Long id) {
        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from activity
                                                                  where id = ?
                                                                  """);
            ResultSet resultSet         = builder.prepareLong(id)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return ResultSetReader.readActivity(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ACTIVITY_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_ACTIVITY_FIND_ID_NOT_FOUND, id.toString());
    }

    @Override
    public Activity findByName(String name) {
        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from activity
                                                                  where name like ?
                                                                  """);
            ResultSet resultSet         = builder.prepareString(name)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return ResultSetReader.readActivity(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ACTIVITY_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_ACTIVITY_FIND_NAME_NOT_FOUND, name);
    }

    @Override
    public List<Activity> findByDestination(Long destinationId) {
        return List.of(); // TODO ?
    }

    @Override
    public Activity create(ActivityCreateDto createDto) {
        Activity activity = mapper.map(createDto);

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  insert into activity(name)
                                                                  values (?)
                                                                  """);
            ResultSet resultSet         = builder.prepareString(activity.getName())
                                                 .executeInsert()
        ) {
            if (resultSet.next())
                return activity.setId(resultSet.getLong(1));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ACTIVITY_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_ACTIVITY_CREATE_NO_RESULT_SET,
                              createDto.getName());
    }

}
