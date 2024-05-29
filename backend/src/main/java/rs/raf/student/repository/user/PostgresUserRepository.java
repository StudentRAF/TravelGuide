package rs.raf.student.repository.user;

import jakarta.inject.Inject;
import rs.raf.student.domain.Page;
import rs.raf.student.domain.PageImplementation;
import rs.raf.student.domain.StatementBuilder;
import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.mapper.UserMapper;
import rs.raf.student.model.User;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.repository.PostgresAbstractRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresUserRepository extends PostgresAbstractRepository implements IUserRepository {

    @Inject
    private UserMapper userMapper;

    @Override
    public Page<User> findAll(int page, int pageSize) {
        List<User> users = new ArrayList<>();
        try(
            Connection connection       = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select * from "user"
                                                                  """,
                                                                  0,
                                                                  pageSize);
            ResultSet resultSet         = builder.executeQuery()
        ) {
            if (resultSet.next())
                users.add(new User(resultSet.getLong("id"),
                                            resultSet.getString("first_name"),
                                            resultSet.getString("last_name"),
                                            resultSet.getString("email"),
                                            resultSet.getString("salt"),
                                            resultSet.getString("password"),
                                            resultSet.getLong("role_id"),
                                            resultSet.getBoolean("enabled")));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return PageImplementation.of(users, pageSize);
    }

    @Override
    public Optional<User> findById(Long id) {
        return findById(null, id);
    }

    private Optional<User> findById(Connection newConnection, Long id) {
        Connection connection = newConnection == null ?  createConnection() : newConnection;

        try(
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select * from "user"
                                                                  where id = ?
                                                                  """);
            ResultSet resultSet         = builder.setLong(id)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return Optional.of(new User(resultSet.getLong("id"),
                                            resultSet.getString("first_name"),
                                            resultSet.getString("last_name"),
                                            resultSet.getString("email"),
                                            resultSet.getString("salt"),
                                            resultSet.getString("password"),
                                            resultSet.getLong("role_id"),
                                            resultSet.getBoolean("enabled")));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try(
            Connection connection       = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select * from "user"
                                                                  where email like ?
                                                                  """);
            ResultSet resultSet         = builder.setString(email)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return Optional.of(new User(resultSet.getLong("id"),
                                            resultSet.getString("first_name"),
                                            resultSet.getString("last_name"),
                                            resultSet.getString("email"),
                                            resultSet.getString("salt"),
                                            resultSet.getString("password"),
                                            resultSet.getLong("role_id"),
                                            resultSet.getBoolean("enabled")));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> create(UserCreateDto createDto) {
        User user = userMapper.map(createDto);

        try(
            Connection connection    = createConnection();
            StatementBuilder builder = StatementBuilder.create(connection,
                                                               """
                                                               insert into "user"(first_name, last_name, email, salt, password, role_id)
                                                               values (?, ?, ?, ?, ?, ?)
                                                               """);
            ResultSet resultSet      = builder.setString(user.getFirstName())
                                              .setString(user.getLastName())
                                              .setString(user.getEmail())
                                              .setString(user.getSalt())
                                              .setString(user.getPassword())
                                              .setLong(user.getRoleId())
                                              .executeInsert();
        ) {
            if (resultSet.next())
                user.setId(resultSet.getLong(1));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.of(user);
    }
///            StatementBuilder builder = StatementBuilder.create(connection,
//                                                               """
//                                                               update "user"
//                                                               set first_name = ?, last_name = ?, email = ?, role_id = ?, enabled = ?
//                                                               where id = ?
//                                                               """);
//            ResultSet resultSet      = builder.setString(updateDto.getFirstName())
//                                              .setString(updateDto.getLastName())
//                                              .setString(updateDto.getEmail())
//                                              .setLong(updateDto.getRoleId())
//                                              .setBoolean(updateDto.getEnabled())
//                                              .executeInsert();
    @Override
    public Optional<User> update(UserUpdateDto updateDto) {
        try(
            Connection connection = createConnection();
        ) {
            User user = findById(connection, updateDto.getId())
                .orElse(null);

            if (user == null)
                return Optional.empty();


        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> disable(Long id) {
        return Optional.empty();
    }

}
