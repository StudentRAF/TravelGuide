package rs.raf.student.repository.destination;

import jakarta.inject.Inject;
import rs.raf.student.domain.Pageable;
import rs.raf.student.sql.StatementBuilder;
import rs.raf.student.dto.destination.DestinationCreateDto;
import rs.raf.student.dto.destination.DestinationUpdateDto;
import rs.raf.student.exception.ExceptionType;
import rs.raf.student.exception.TGException;
import rs.raf.student.mapper.DestinationMapper;
import rs.raf.student.model.Destination;
import rs.raf.student.repository.IDestinationRepository;
import rs.raf.student.repository.PostgresAbstractRepository;
import rs.raf.student.sql.PostgresType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                destinations.add(loadDestination(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception.getMessage());
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
                                                                  select *
                                                                  from destination
                                                                  """,
                                                                  pageable);
            ResultSet resultSet         = builder.executeQuery()
        ) {
            while (resultSet.next())
                destinations.add(loadDestination(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception.getMessage());
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
                                                                  where id = ?;
                                                                  """);
            ResultSet resultSet         = builder.prepareLong(id)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return loadDestination(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception.getMessage());
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
                destinations.add(loadDestination(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception.getMessage());
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
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_DESTINATION_CREATE_NO_RESULT_SET,
                              createDto.getName(),
                              createDto.getDescription());
    }

    @Override
    public Destination update(DestinationUpdateDto updateDto) {
        Destination destination;

        try {
            destination = findById(updateDto.getId());
        }
        catch (TGException exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_UPDATE_ID_NOT_FOUND,
                                  updateDto.getId().toString(),
                                  updateDto.getName(),
                                  updateDto.getDescription());
        }

        try(
            Connection       connection = createConnection();
            StatementBuilder builder = StatementBuilder.create(connection,
                                                               """
                                                               update destination
                                                               set name = ?, description = ?
                                                               where id = ?
                                                               """);
            ResultSet resultSet      = builder.prepareString(destination.getName())
                                              .prepareString(destination.getDescription())
                                              .prepareLong(destination.getId())
                                              .executeInsertReturning(StatementBuilder.create(connection,"""
                                                                                              select *
                                                                                              from destination
                                                                                              where id = ?
                                                                                              """)
                                                                                      .prepareLong(destination.getId()))
        ) {
            if (resultSet.next())
                return loadDestination(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception.getMessage());
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
            throw new TGException(ExceptionType.REPOSITORY_DESTINATION_SQL_EXCEPTION, exception.getMessage());
        }

    }

    private Destination loadDestination(ResultSet resultSet) throws SQLException {
        return new Destination(resultSet.getLong("id"),
                               resultSet.getString("name"),
                               resultSet.getString("description"));
    }

}
