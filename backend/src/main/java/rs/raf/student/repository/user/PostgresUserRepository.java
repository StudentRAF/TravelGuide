package rs.raf.student.repository.user;

import lombok.SneakyThrows;
import rs.raf.student.domain.Page;
import rs.raf.student.domain.PageImplementation;
import rs.raf.student.domain.StatementBuilder;
import rs.raf.student.model.User;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.repository.PostgresAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresUserRepository extends PostgresAbstractRepository implements IUserRepository {

    @Override
    public Page<User> findAll(int page, int pageSize) {
        List<User> users = new ArrayList<>();
        try(
            Connection connection       = createConnection();
            StatementBuilder builder    = new StatementBuilder(connection,
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
        try(
            Connection connection       = createConnection();
            StatementBuilder builder    = new StatementBuilder(connection,
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

    @SneakyThrows
    private ResultSet executeById(PreparedStatement statement, Long id) {
        statement.setLong(1, id);

        return statement.executeQuery();
    }

}
