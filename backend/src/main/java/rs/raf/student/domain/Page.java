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
public class Page<Type> implements Iterable<Type> {

    private List<Type> content;

    @JsonProperty("total_elements")
    private int totalElements;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("page_size")
    private int pageSize;

    @JsonProperty("page")
    private int page;

    @JsonProperty("content_size")
    private int contentSize;

    private boolean empty;

    public static <Type> Page<Type> of(List<Type> content, Pageable pageable) {
        return new Page<>
            (
                content,
                pageable.getTotalElements(),
                pageable.getTotalElements() == 0 ? 0 : pageable.getTotalElements() / pageable.getPageSize() + 1,
                pageable.getPageSize(),
                pageable.getPage(),
                content.size(),
                content.isEmpty()
            );
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
