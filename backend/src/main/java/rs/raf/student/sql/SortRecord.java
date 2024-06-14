package rs.raf.student.sql;

public record SortRecord(String column, Order order, Nulls nulls) {

    public SortRecord(String column, Order order) {
        this(column, order, order.equals(Order.ASC) ? Nulls.LAST : Nulls.FIRST);
    }

    @Override
    public String toString() {
        return (column + ' ' + order + ' ' + nulls.forOrder(order)).replace("  ", " ")
                                                                   .trim();
    }

}
