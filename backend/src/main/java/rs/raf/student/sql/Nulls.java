package rs.raf.student.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Nulls {

    FIRST("nulls first", "nulls first"),
    LAST ("nulls last" , "nulls last" );

    private final String code;
    private final String keyword;

    public String forOrder(Order order) {
        if (order.equals(Order.ASC)  && equals(LAST) ||
            order.equals(Order.DESC) && equals(FIRST))
            return "";

        return toString();
    }

    public static Nulls parse(String string) {
        for (Nulls nulls : Nulls.values())
            if (nulls.keyword.equals(string))
                return nulls;

        return null;
    }

    @Override
    public String toString() {
        return code;
    }

}
