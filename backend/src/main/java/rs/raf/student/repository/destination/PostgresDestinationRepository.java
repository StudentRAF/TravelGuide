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

package rs.raf.student.repository.destination;

import jakarta.inject.Inject;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.destination.DestinationCreateDto;
import rs.raf.student.dto.destination.DestinationUpdateDto;
import rs.raf.student.exception.ExceptionType;
import rs.raf.student.exception.TGException;
import rs.raf.student.mapper.DestinationMapper;
import rs.raf.student.model.Destination;
import rs.raf.student.repository.IDestinationRepository;
import rs.raf.student.repository.PostgresAbstractRepository;
import rs.raf.student.repository.ResultSetReader;
import rs.raf.student.sql.PostgresType;
import rs.raf.student.sql.StatementBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PostgresDestinationRepository extends PostgresAbstractRepository implements IDestinationRepository {

    @Inject
    private DestinationMapper mapper;

    @Override
    public List<Destination> findAll() {
        List<Destination>destinations = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from destination
                                                                  """);
            ResultSet resultSet         = builder.executeQuery()
        ) {
            while (resultSet.next())
                destinations.add(ResultSetReader.readDestination(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return destinations;
    }

    @Override
    public List<Destination> findAll(Pageable pageable) {
        List<Destination>destinations = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *, count(*) over() as count
                                                                  from destination
                                                                  """,
                                                                  pageable);
            ResultSet resultSet         = builder.executeQuery()
        ) {
            do {
                if (!resultSet.next())
                    break;

                destinations.add(ResultSetReader.readDestination(resultSet));
            } while (!resultSet.isLast());

            if (resultSet.isLast())
                pageable.setTotalElements(resultSet.getInt("count"));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return destinations;
    }

    @Override
    public Destination findById(Long id) {
        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from destination
                                                                  where id = ?
                                                                  """);
            ResultSet resultSet         = builder.prepareLong(id)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return ResultSetReader.readDestination(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_DESTINATION_FIND_ID_NOT_FOUND, id.toString());
    }

    @Override
    public List<Destination> findByIds(List<Long> ids) {
        List<Destination> destinations = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from destination
                                                                  where id = any(?)
                                                                  """);
            ResultSet resultSet         = builder.prepareArray(PostgresType.BIGINT, ids)
                                                 .executeQuery()
        ) {
            while (resultSet.next())
                destinations.add(ResultSetReader.readDestination(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return destinations;
    }

    @Override
    public Destination create(DestinationCreateDto createDto) {
        Destination destination = mapper.map(createDto);

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  insert into destination(name, description)
                                                                  values (?, ?)
                                                                  """);
            ResultSet resultSet         = builder.prepareString(destination.getName())
                                                 .prepareString(destination.getDescription())
                                                 .executeInsert()
        ) {
            if (resultSet.next())
                return destination.setId(resultSet.getLong(1));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_DESTINATION_CREATE_NO_RESULT_SET,
                              createDto.getName(),
                              createDto.getDescription());
    }

    @Override
    public Destination update(DestinationUpdateDto updateDto) {
        try(
            Connection       connection = createConnection();
            StatementBuilder builder = StatementBuilder.create(connection,
                                                               """
                                                               update destination
                                                               set name = coalesce(?, name), description = coalesce(?, description)
                                                               where id = ?
                                                               """);
            ResultSet resultSet      = builder.prepareString(updateDto.getName())
                                              .prepareString(updateDto.getDescription())
                                              .prepareLong(updateDto.getId())
                                              .executeInsertReturning(StatementBuilder.create(connection,"""
                                                                                              select *
                                                                                              from destination
                                                                                              where id = ?
                                                                                              """)
                                                                                      .prepareLong(updateDto.getId()))
        ) {
            if (resultSet.next())
                return ResultSetReader.readDestination(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_DESTINATION_UPDATE_NO_RESULT_SET,
                              updateDto.getId().toString(),
                              updateDto.getName(),
                              updateDto.getDescription());
    }

    @Override
    public void delete(Long id) {
        Destination destination;

        try {
            destination = findById(id);
        }
        catch (TGException exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_DELETE_ID_NOT_FOUND,
                                  id.toString());
        }

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  delete from destination
                                                                  where id = ?
                                                                  """)
        ) {
            builder.prepareString(destination.getName())
                   .prepareString(destination.getDescription())
                   .prepareLong(destination.getId())
                   .executeDelete();
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception, exception.getMessage());
        }

    }

}
