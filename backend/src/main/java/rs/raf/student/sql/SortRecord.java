package rs.raf.student.sql;

public record SortRecord(String column, Order order, Nulls nulls) {

    public SortRecord(String column, Order order) {
        this(column, order, order.equals(Order.ASC) ? Nulls.LAST : Nulls.FIRST);
    }

    public SortRecord(String column, Nulls nulls) {
        this(column, Order.ASC, nulls);
    }

    @Override
    public String toString() {
        return (column + ' ' + order + ' ' + nulls.forOrder(order)).replace("  ", " ")
                                                                   .trim();
    }

    public static SortRecord parse(String sort) { //TODO: Error checking
        String[] attributes = sort.split(" ");

        if (attributes.length < 1 || attributes.length > 4)
            return null;

        String column = attributes[0];

        if (attributes.length == 1)
            return new SortRecord(column, Order.ASC);

        Order order = Order.parse(attributes[1]);

        if (attributes.length == 2)
            return new SortRecord(column, order);

        if (order == null)
            return new SortRecord(column, Nulls.parse(attributes[1] + ' ' + attributes[2]));

        return new SortRecord(column, order, Nulls.parse(attributes[2] + ' ' + attributes[3]));
    }

}
