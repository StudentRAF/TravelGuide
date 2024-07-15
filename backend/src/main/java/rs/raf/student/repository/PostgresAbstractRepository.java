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
