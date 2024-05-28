package rs.raf.student.domain;

public enum Order {
    ASC("asc"),
    DESC("desc");

    private final String name;

    Order(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
