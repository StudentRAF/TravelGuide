package rs.raf.student.sql;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(value = AccessLevel.PRIVATE)
public enum Nulls {

    FIRST  ("nulls first"),
    LAST   ("nulls last" );

    private final String code;

    Nulls(String code) {
        this.code = code;
    }

    public String forOrder(Order order) {
        if (order.equals(Order.ASC)  && equals(LAST) ||
            order.equals(Order.DESC) && equals(FIRST))
            return "";

        return toString();
    }

    @Override
    public String toString() {
        return code;
    }

}
