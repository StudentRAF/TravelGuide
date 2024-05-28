package rs.raf.student.domain;

import java.util.List;

public interface Page<Type> extends Iterable<Type> {

    List<Type> getContent();

    int getTotalElements();

    int getTotalPages();

    int getPageSize();

    int getPageNumber();

    int getContentSize();

    boolean isEmpty();

}
