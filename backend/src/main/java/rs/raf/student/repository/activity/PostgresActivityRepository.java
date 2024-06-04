package rs.raf.student.repository.activity;

import jakarta.inject.Inject;
import rs.raf.student.domain.StatementBuilder;
import rs.raf.student.dto.activity.ActivityCreateDto;
import rs.raf.student.exception.ExceptionType;
import rs.raf.student.exception.TGException;
import rs.raf.student.mapper.ActivityMapper;
import rs.raf.student.model.Activity;
import rs.raf.student.repository.IActivityRepository;
import rs.raf.student.repository.PostgresAbstractRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresActivityRepository extends PostgresAbstractRepository implements IActivityRepository {

    @Inject
    private ActivityMapper mapper;

    @Override
    public List<Activity> findAll() {
        List<Activity> activities = new ArrayList<>();

        try(
            Connection connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from activity
                                                                  """);
            ResultSet resultSet         = builder.executeQuery()
        ) {
            while (resultSet.next())
                activities.add(loadActivity(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ACTIVITY_SQL_EXCEPTION, exception.getMessage());
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
                                                                  where id = ?;
                                                                  """);
            ResultSet resultSet         = builder.setLong(id)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return loadActivity(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ACTIVITY_SQL_EXCEPTION, exception.getMessage());
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
                                                                  where name like ?;
                                                                  """);
            ResultSet resultSet         = builder.setString(name)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return loadActivity(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ACTIVITY_SQL_EXCEPTION, exception.getMessage());
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
            ResultSet resultSet         = builder.setString(activity.getName())
                                                 .executeInsert()
        ) {
            if (resultSet.next())
                return activity.setId(resultSet.getLong(1));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_ACTIVITY_SQL_EXCEPTION, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_ACTIVITY_CREATE_NO_RESULT_SET,
                              createDto.getName());
    }

    private Activity loadActivity(ResultSet resultSet) throws SQLException {
        return new Activity(resultSet.getLong("id"),
                            resultSet.getString("name"));
    }

}
