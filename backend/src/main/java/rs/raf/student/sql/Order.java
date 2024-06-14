package rs.raf.student.sql;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(value = AccessLevel.PRIVATE)
public enum Order {

    ASC    (""    ),
    DESC   ("desc");

    private final String code;

    Order(String code) {
        this.code = code;
    }

    Order(Order order) {
        code = order.getCode();
    }

    @Override
    public String toString() {
        return code;
    }

}
