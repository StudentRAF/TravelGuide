package rs.raf.student.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import rs.raf.student.sql.SortRecord;

import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pageable {

    private int page;

    private int pageSize;

    private int totalElements;

    private final List<SortRecord> sortRecords;

    public static Pageable empty() {
        return of(0);
    }

    public static Pageable of(int pageSize) {
        return of(0, pageSize, List.of());
    }

    public static Pageable of(int page, int pageSize) {
        return of(page, pageSize, List.of());
    }

    public static Pageable of(int pageSize, List<String> sort) {
        return new Pageable
            (
                0,
                pageSize,
                0,
                sort.stream()
                    .map(SortRecord::parse)
                    .toList()
            );
    }

    public static Pageable of(int page, int pageSize, List<String> sort) {
        return new Pageable
            (
                page,
                pageSize,
                0,
                sort.stream()
                    .map(SortRecord::parse)
                    .toList()
            );
    }

}
