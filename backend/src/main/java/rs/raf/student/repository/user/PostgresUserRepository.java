package rs.raf.student.repository.user;

import jakarta.inject.Inject;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.exception.ExceptionType;
import rs.raf.student.exception.TGException;
import rs.raf.student.mapper.UserMapper;
import rs.raf.student.model.User;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.repository.PostgresAbstractRepository;
import rs.raf.student.sql.PostgresType;
import rs.raf.student.sql.StatementBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresUserRepository extends PostgresAbstractRepository implements IUserRepository {

    @Inject
    private UserMapper userMapper;

    @Override
    public List<User> findAll(Pageable pageable) throws TGException {
        List<User> users = new ArrayList<>();

        try(
            Connection connection       = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from "user"
                                                                  """,
                                                                  pageable);
            ResultSet resultSet         = builder.executeQuery()
        ) {
            while (resultSet.next())
                users.add(loadUser(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_USER_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return users;
    }

    @Override
    public User findById(Long id) throws TGException {
        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from "user"
                                                                  where id = ?;
                                                                  """);
            ResultSet resultSet         = builder.prepareLong(id)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return loadUser(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_USER_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_USER_FIND_ID_NOT_FOUND, id.toString());
    }

    @Override
    public List<User> findByIds(List<Long> ids) throws TGException {
        List<User> users = new ArrayList<>();

        try(
            Connection       connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from "user"
                                                                  where id = any(?)
                                                                  """);
            ResultSet resultSet         = builder.prepareArray(PostgresType.BIGINT, ids)
                                                 .executeQuery()
        ) {
            while (resultSet.next())
                users.add(loadUser(resultSet));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_USER_SQL_EXCEPTION, exception, exception.getMessage());
        }

        return users;
    }

    @Override
    public User findByEmail(String email) throws TGException {
        try(
            Connection connection       = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select *
                                                                  from "user"
                                                                  where email like ?
                                                                  """);
            ResultSet resultSet         = builder.prepareString(email)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return loadUser(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_USER_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_USER_FIND_EMAIL_NOT_FOUND, email);
    }

    @Override
    public User create(UserCreateDto createDto) throws TGException {
        User user = userMapper.map(createDto);

        try(
            Connection connection    = createConnection();
            StatementBuilder builder = StatementBuilder.create(connection,
                                                               """
                                                               insert into "user"(first_name, last_name, email, salt, password, role_id)
                                                               values (?, ?, ?, ?, ?, ?)
                                                               """);
            ResultSet resultSet      = builder.prepareString(user.getFirstName())
                                              .prepareString(user.getLastName())
                                              .prepareString(user.getEmail())
                                              .prepareString(user.getSalt())
                                              .prepareString(user.getPassword())
                                              .prepareLong(user.getRoleId())
                                              .executeInsert()
        ) {
            if (resultSet.next())
                return user.setId(resultSet.getLong(1));
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_USER_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_USER_UPDATE_USER_NOT_FOUND,
                              createDto.getEmail(),
                              createDto.getFirstName(),
                              createDto.getLastName(),
                              createDto.getRoleId().toString());
    }

    @Override
    public User update(UserUpdateDto updateDto) throws TGException {
        User user;

        try {
            user = findById(updateDto.getId());
        }
        catch (TGException exception) {
            throw new TGException(ExceptionType.REPOSITORY_USER_UPDATE_USER_NOT_FOUND,
                                  updateDto.getId().toString(),
                                  updateDto.getFirstName(),
                                  updateDto.getLastName(),
                                  updateDto.getRoleId().toString());
        }

        try(
            Connection connection = createConnection();
            StatementBuilder builder = StatementBuilder.create(connection,
                                                               """
                                                               update "user"
                                                               set first_name = ?, last_name = ?, email = ?, role_id = ?, enabled = ?
                                                               where id = ?
                                                               """);
            ResultSet resultSet      = builder.prepareString(updateDto.getFirstName())
                                              .prepareString(updateDto.getLastName())
                                              .prepareString(updateDto.getEmail())
                                              .prepareLong(updateDto.getRoleId())
                                              .prepareBoolean(updateDto.getEnabled())
                                              .executeInsertReturning(StatementBuilder.create(connection,
                                                                                              """
                                                                                              select *
                                                                                              from "user"
                                                                                              where id = ?
                                                                                              """)
                                                                                      .prepareLong(user.getId()))
        ) {
            if (resultSet.next())
                return loadUser(resultSet);
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.REPOSITORY_USER_SQL_EXCEPTION, exception, exception.getMessage());
        }

        throw new TGException(ExceptionType.REPOSITORY_USER_UPDATE_NO_RESULT_SET,
                              updateDto.getId().toString(),
                              updateDto.getFirstName(),
                              updateDto.getLastName(),
                              updateDto.getRoleId().toString());
    }

    private User loadUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("salt"),
                        resultSet.getString("password"),
                        resultSet.getLong("role_id"),
                        resultSet.getBoolean("enabled"));
    }

}
