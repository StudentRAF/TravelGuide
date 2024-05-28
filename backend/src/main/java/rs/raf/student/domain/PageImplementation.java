package rs.raf.student.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageImplementation<Type> implements Page<Type> {

    private List<Type> content;

    @JsonProperty("total_elements")
    private int totalElements;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("page_size")
    private int pageSize;

    @JsonProperty("page_number")
    private int pageNumber;

    @JsonProperty("content_size")
    private int contentSize;

    private boolean empty;

    public static <Type> PageImplementation<Type> of(List<Type> content, int pageSize) {
        return of(content, 0, pageSize);
    }

    public static <Type> PageImplementation<Type> of(List<Type> content, int pageNumber, int pageSize) {
        return new PageImplementation<>(content, 99, 5, pageNumber, content.size(), pageSize, content.isEmpty());
    }

    @Override
    public Iterator<Type> iterator() {
        return content.iterator();
    }

    @Override
    public void forEach(Consumer<? super Type> action) {
        content.forEach(action);
    }

    @Override
    public Spliterator<Type> spliterator() {
        return content.spliterator();
    }

}
