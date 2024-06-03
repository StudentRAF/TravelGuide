package rs.raf.student.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableImplementation implements Pageable {

    private final int pageNumber;

    private final int pageSize;

    public static PageableImplementation of(int pageNumber, int pageSize) {
        return new PageableImplementation(pageNumber, pageSize);
    }

}
