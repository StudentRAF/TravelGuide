package rs.raf.student.repository.user_role;

import rs.raf.student.domain.Page;
import rs.raf.student.domain.StatementBuilder;
import rs.raf.student.model.User;
import rs.raf.student.model.UserRole;
import rs.raf.student.repository.IUserRoleRepository;
import rs.raf.student.repository.PostgresAbstractRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;

public class PostgresUserRoleRepository extends PostgresAbstractRepository implements IUserRoleRepository {

    @Override
    public Page<UserRole> findAll() {
        return null;
    }

    @Override
    public Optional<UserRole> findById(Long id) {
        try(
            Connection connection = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select * from user_role
                                                                  where id = ?
                                                                  """);
            ResultSet resultSet         = builder.setLong(id)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                return Optional.of(new UserRole(resultSet.getLong("id"),
                                                resultSet.getString("name")));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

    @Override
    public Optional<UserRole> findByName(String name) {
        try(
            Connection connection       = createConnection();
            StatementBuilder builder    = StatementBuilder.create(connection,
                                                                  """
                                                                  select * from user_role
                                                                  where name like ?
                                                                  """);
            ResultSet resultSet         = builder.setString(name)
                                                 .executeQuery()
        ) {
            if (resultSet.next())
                if (resultSet.next())
                    return Optional.of(new UserRole(resultSet.getLong("id"),
                                                    resultSet.getString("name")));
        }
        catch (Exception exception) {
            exception.printStackTrace(System.err);
        }

        return Optional.empty();
    }

}
