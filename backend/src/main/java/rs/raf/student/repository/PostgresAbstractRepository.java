package rs.raf.student.repository;

import rs.raf.student.utils.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

public class PostgresAbstractRepository {

    private static Properties properties;

    static {
        try { Class.forName("org.postgresql.Driver"); }
        catch (ClassNotFoundException exception) { exception.printStackTrace(System.err); }

        try { properties = Utilities.loadLocalProperties("application"); }
        catch (Exception exception) { System.err.println("Local Application properties file is missing.\n" + exception.getMessage()); }
    }

    protected Connection createConnection() throws SQLException {

        return DriverManager.getConnection(
                MessageFormat.format("jdbc:postgresql://{0}:{1}/{2}", properties.getProperty("database.host"),
                                                                      properties.getProperty("database.port"),
                                                                      properties.getProperty("database.scheme")
                ),
                properties.getProperty("database.username"),
                properties.getProperty("database.password")
        );
    }

    protected Connection createConnection(boolean autoCommit) throws SQLException {
        Connection connection = createConnection();

        connection.setAutoCommit(autoCommit);

        return connection;
    }

}
