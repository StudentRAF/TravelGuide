package rs.raf.student.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Order {

    ASC    ("asc" , ""    ),
    DESC   ("desc", "desc");

    private final String code;
    private final String keyword;

    public static Order parse(String string) {
        for (Order order : Order.values())
            if (order.keyword.equals(string))
                return order;

        return null;
    }

    @Override
    public String toString() {
        return code;
    }

}
