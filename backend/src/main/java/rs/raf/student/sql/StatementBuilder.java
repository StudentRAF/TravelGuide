package rs.raf.student.sql;

import rs.raf.student.domain.Pageable;

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
import java.util.ArrayList;
import java.util.List;

public class StatementBuilder implements AutoCloseable {

    private final Connection        connection;
    private       PreparedStatement statement;
    private final StringBuilder     sqlStatement;

    private final List<SortRecord>        sortRecords;
    private final List<InjectorRecord<?>> injectorRecords;

    private int     counter;
    private int     limit;
    private int     offset;
    private boolean hasPage;

    private StatementBuilder(Connection connection, String sql, Pageable pageable) {
        if (connection == null)
            throw new IllegalArgumentException("Database connection cannot be null!");

        if (sql == null)
            throw new IllegalArgumentException("SQL statement cannot be null!");

        int page     = pageable.getPage();
        int pageSize = pageable.getPageSize();

        if (page < 0)
            throw new IllegalArgumentException("Page number cannot be negative number!");

        if (pageSize < 0)
            throw new IllegalArgumentException("Page size number cannot be negative number!");

        counter         = 0;
        hasPage         = true;
        limit           = pageSize;
        offset          = page * pageSize;
        injectorRecords = new ArrayList<>();
        sortRecords     = new ArrayList<>();
        sqlStatement    = new StringBuilder(sql);

        if (sqlStatement.charAt(sqlStatement.length() - 1) != '\n')
            sqlStatement.append('\n');

        sortRecords.addAll(pageable.getSortRecords());

        this.connection = connection;
    }

    public static StatementBuilder create(Connection connection, String sql) throws SQLException {
        return create(connection, sql, Pageable.of(0, 0));
    }

    public static StatementBuilder create(Connection connection, String sql, Pageable pageable) throws SQLException {
        return new StatementBuilder(connection, sql, pageable);
    }

    //region Prepare Injectors

    private StatementBuilder addInjectorRecord(InjectorRecord<?> injectorRecord) {
        injectorRecords.add(injectorRecord);

        return this;
    }

    private StatementBuilder addSortRecord(SortRecord record) {
        sortRecords.add(record);

        return this;
    }

    public StatementBuilder prepareLimit(int value) {
        if (hasPage)
            offset = offset / limit * value;

        limit = value;

        return this;
    }

    public StatementBuilder prepareOffset(int value) {
        offset  = value;
        hasPage = false;

        return this;
    }

    public StatementBuilder preparePage(int page) {
        offset  = page * limit;
        hasPage = true;

        return this;
    }

    public StatementBuilder prepareNull(int sqlType) {
        return addInjectorRecord(new InjectorRecord<>(SQLType.INTEGER, sqlType, this::injectNull));
    }

    public StatementBuilder prepareBoolean(boolean value) {
        return addInjectorRecord(new InjectorRecord<>(SQLType.BOOLEAN, value, this::injectBoolean));
    }

    public StatementBuilder prepareByte(byte value) {
        return addInjectorRecord(new InjectorRecord<>(SQLType.INTEGER, value, this::injectByte));
    }

    public StatementBuilder prepareShort(short value) {
        return addInjectorRecord(new InjectorRecord<>(SQLType.SMALLINT, value, this::injectShort));
    }

    public StatementBuilder prepareInteger(int value) {
        return addInjectorRecord(new InjectorRecord<>(SQLType.INTEGER, value, this::injectInteger));
    }

    public StatementBuilder prepareLong(long value) {
        return addInjectorRecord(new InjectorRecord<>(SQLType.INTEGER, value, this::injectLong));
    }

    public StatementBuilder prepareFloat(float value) {
        return addInjectorRecord(new InjectorRecord<>(SQLType.FLOAT, value, this::injectFloat));
    }

    public StatementBuilder prepareDouble(double value) {
        return addInjectorRecord(new InjectorRecord<>(SQLType.DOUBLE_PRECISION, value, this::injectDouble));
    }

    public StatementBuilder prepareString(String value) {
        return addInjectorRecord(new InjectorRecord<>(SQLType.VARCHAR, value, this::injectString));
    }

    public StatementBuilder prepareDate(Date date) {
        return addInjectorRecord(new InjectorRecord<>(SQLType.DATE, date, this::injectDate));
    }

    public StatementBuilder prepareDate(LocalDate date) {
        return prepareDate(Date.valueOf(date));
    }

    public StatementBuilder prepareTime(Time time) {
        return addInjectorRecord(new InjectorRecord<>(SQLType.TIME, time, this::injectTime));
    }

    public StatementBuilder prepareTime(LocalTime time) {
        return prepareTime(Time.valueOf(time));
    }

    public StatementBuilder prepareTimestamp(Timestamp timestamp) {
        return addInjectorRecord(new InjectorRecord<>(SQLType.TIMESTAMP, timestamp, this::injectTimestamp));
    }

    public StatementBuilder prepareTimestamp(LocalDateTime dateTime) {
        return prepareTimestamp(Timestamp.valueOf(dateTime));
    }

    public StatementBuilder prepareTimestamp(LocalDate date, LocalTime time) {
        return prepareTimestamp(Timestamp.valueOf(LocalDateTime.of(date, time)));
    }

    public StatementBuilder prepareArray(SQLType type, Object[] elements) {
        return addInjectorRecord(new InjectorRecord<>(type, elements, this::injectArray));
    }

    public StatementBuilder prepareArray(SQLType type, List<?> elements) {
        return prepareArray(type, elements.toArray());
    }

    public StatementBuilder prepareSort(String column) {
        return prepareSort(column, Order.ASC);
    }

    public StatementBuilder prepareSort(String column, Order order) {
        return addSortRecord(new SortRecord(column, order));
    }

    public StatementBuilder prepareSort(String column, Nulls nulls) {
        return addSortRecord(new SortRecord(column, nulls));
    }

    public StatementBuilder prepareSort(String column, Order order, Nulls nulls) {
        return addSortRecord(new SortRecord(column, order, nulls));
    }

    //endregion Prepare Injectors

    //region Injectors

    private void injectNull(SQLType type, Integer sqlType) throws SQLException {
        statement.setNull(++counter, sqlType);
    }

    private void injectBoolean(SQLType type, Boolean value) throws SQLException {
        statement.setBoolean(++counter, value);
    }

    private void injectByte(SQLType type, Byte value) throws SQLException  {
        statement.setByte(++counter, value);
    }

    private void injectShort(SQLType type, Short value) throws SQLException  {
        statement.setShort(++counter, value);
    }

    private void injectInteger(SQLType type, Integer value) throws SQLException  {
        statement.setInt(++counter, value);
    }

    private void injectLong(SQLType type, Long value) throws SQLException  {
        statement.setLong(++counter, value);
    }

    private void injectFloat(SQLType type, Float value) throws SQLException  {
        statement.setFloat(++counter, value);
    }

    private void injectDouble(SQLType type, Double value) throws SQLException  {
        statement.setDouble(++counter, value);
    }

    private void injectString(SQLType type, String value) throws SQLException  {
        statement.setString(++counter, value);
    }

    private void injectDate(SQLType type, Date date) throws SQLException  {
        statement.setDate(++counter, date);
    }

    private void injectTime(SQLType type, Time time) throws SQLException  {
        statement.setTime(++counter, time);
    }

    private void injectTimestamp(SQLType type, Timestamp timestamp) throws SQLException  {
        statement.setTimestamp(++counter, timestamp);
    }

    private void injectArray(SQLType type, Object[] elements) throws SQLException {
        statement.setArray(++counter, connection.createArrayOf(type.getName(), elements));
    }

    //endregion Injectors

    //region Executors

    private void prepareStatement() throws SQLException {
        statement = connection.prepareStatement(sqlStatement.toString(),
                                                PreparedStatement.RETURN_GENERATED_KEYS);

        injectorRecords.forEach(InjectorRecord::perform);
    }

    public ResultSet executeQuery() throws SQLException {
        if (!sortRecords.isEmpty()) {
            sqlStatement.append("""
                                order by \
                                """);
            sqlStatement.append(sortRecords.getFirst());
            sortRecords.stream()
                       .skip(1)
                       .forEach(record -> sqlStatement.append(", ")
                                                      .append(record));

            sqlStatement.append('\n');
        }

        if (limit > 0) {
            sqlStatement.append("""
                                limit ?
                                offset ?
                                """);

            this.prepareInteger(limit)
                .prepareInteger(offset);
        }

        prepareStatement();

        return statement.executeQuery();
    }

    public ResultSet executeInsert() throws SQLException {
        prepareStatement();

        statement.executeUpdate();

        return statement.getGeneratedKeys();
    }

    public ResultSet executeInsertReturning(StatementBuilder builder) throws SQLException {
        prepareStatement();
        executeInsert();

        return builder.executeQuery();
    }

    public int executeUpdate() throws SQLException {
        prepareStatement();

        return statement.executeUpdate();
    }

    public ResultSet executeUpdateReturning(StatementBuilder builder) throws SQLException {
        prepareStatement();
        executeUpdate();

        return builder.executeQuery();
    }

    public int executeDelete() throws SQLException {
        prepareStatement();

        return statement.executeUpdate();
    }

    public ResultSet executeDeleteReturning(StatementBuilder builder) throws SQLException {
        executeDelete();

        return builder.executeQuery();
    }

    //endregion Executors

    @Override
    public void close() throws Exception {
        statement.close();
    }

    @Override
    public String toString() {
        return sqlStatement.toString();
    }

}
