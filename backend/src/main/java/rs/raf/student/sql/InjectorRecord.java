package rs.raf.student.sql;

import lombok.SneakyThrows;

public record InjectorRecord<Type>(SQLType type, Type value, FunctionalInjector<Type> injector) {

    @SneakyThrows
    public void perform() {
        injector.inject(type, value);
    }

}
