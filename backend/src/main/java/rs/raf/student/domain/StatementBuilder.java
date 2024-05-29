package rs.raf.student.domain;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class StatementBuilder implements AutoCloseable {

    private final PreparedStatement statement;

    private       int counter;
    private final int limit;
    private final int offset;

    public StatementBuilder(Connection connection, String sql) throws SQLException {
        this(connection, sql, 0);
    }

    public StatementBuilder(Connection connection, String sql, int pageSize) throws SQLException {
        this(connection, sql, 0, pageSize);
    }

    public StatementBuilder(Connection connection, String sql, int page, int pageSize) throws SQLException {
        if (connection == null)
            throw new IllegalArgumentException("Database connection cannot be null!");

        if (sql == null)
            throw new IllegalArgumentException("SQL statement cannot be null!");

        if (page < 0)
            throw new IllegalArgumentException("Page number cannot be negative number!");

        if (pageSize < 0)
            throw new IllegalArgumentException("Page size number cannot be negative number!");

        StringBuilder sqlStatement = new StringBuilder(sql);

        counter = 0;
        limit   = pageSize;
        offset  = page * pageSize;

        if (sqlStatement.charAt(sqlStatement.length() - 1) != '\n')
            sqlStatement.append('\n');

        if (pageSize > 0)
            sqlStatement.append("""
                            limit ?
                            offset ?
                            """);

        statement = connection.prepareStatement(sqlStatement.toString(),
                                                PreparedStatement.RETURN_GENERATED_KEYS);
    }

    public StatementBuilder setNull(int sqlType) throws SQLException {
        statement.setNull(++counter, sqlType);

        return this;
    }

    public StatementBuilder setBoolean(boolean value) throws SQLException {
        statement.setBoolean(++counter, value);

        return this;
    }

    public StatementBuilder setByte(byte value) throws SQLException  {
        statement.setByte(++counter, value);

        return this;
    }

    public StatementBuilder setShort(short value) throws SQLException  {
        statement.setShort(++counter, value);

        return this;
    }

    public StatementBuilder setInteger(int value) throws SQLException  {
        statement.setInt(++counter, value);

        return this;
    }

    public StatementBuilder setLong(long value) throws SQLException  {
        statement.setLong(++counter, value);

        return this;
    }

    public StatementBuilder setFloat(float value) throws SQLException  {
        statement.setFloat(++counter, value);

        return this;
    }

    public StatementBuilder setDouble(double value) throws SQLException  {
        statement.setDouble(++counter, value);

        return this;
    }

    public StatementBuilder setString(String value) throws SQLException  {
        statement.setString(++counter, value);

        return this;
    }

    public StatementBuilder setDate(Date date) throws SQLException  {
        statement.setDate(++counter, date);

        return this;
    }

    public StatementBuilder setDate(LocalDate date) throws SQLException  {
        statement.setDate(++counter, Date.valueOf(date));

        return this;
    }

    public StatementBuilder setTime(Time time) throws SQLException  {
        statement.setTime(++counter, time);

        return this;
    }

    public StatementBuilder setTime(LocalTime time) throws SQLException  {
        statement.setTime(++counter, Time.valueOf(time));

        return this;
    }

    public StatementBuilder setTimestamp(Timestamp timestamp) throws SQLException  {
        statement.setTimestamp(++counter, timestamp);

        return this;
    }

    public StatementBuilder setTimestamp(LocalDateTime dateTime) throws SQLException  {
        statement.setTimestamp(++counter, Timestamp.valueOf(dateTime));

        return this;
    }

    public StatementBuilder setTimestamp(LocalDate date, LocalTime time) throws SQLException {
        statement.setTimestamp(++counter, Timestamp.valueOf(LocalDateTime.of(date, time)));

        return this;
    }

    public ResultSet executeQuery() throws SQLException {
        if (limit > 0)
            this.setInteger(limit)
                .setInteger(offset);

        return statement.executeQuery();
    }

    public ResultSet executeInsert() throws SQLException {
        statement.executeUpdate();

        return statement.getGeneratedKeys();
    }

    public int executeUpdate() throws SQLException {
        return statement.executeUpdate();
    }

    public int executeDelete() throws SQLException {
        return statement.executeUpdate();
    }

    @Override
    public void close() throws Exception {
        statement.close();
    }

}
