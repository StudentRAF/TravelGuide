package rs.raf.student.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Order {

    ASC    ("asc" , ""    ),
    DESC   ("desc", "desc");

    private final String keyword;
    private final String code;

    public static Order parse(String string) {
        for (Order order : Order.values())
            if (order.keyword.equalsIgnoreCase(string))
                return order;

        return null;
    }

    @Override
    public String toString() {
        return code;
    }

}
