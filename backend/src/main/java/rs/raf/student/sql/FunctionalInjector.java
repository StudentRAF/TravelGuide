package rs.raf.student.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface FunctionalInjector<Type> {

    void inject(SQLType type, Type value) throws SQLException;

}
