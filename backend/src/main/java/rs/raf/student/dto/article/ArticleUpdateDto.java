package rs.raf.student.dto.article;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.student.validation.NullOrNotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateDto {

    @NotNull(message = "Article id cannot be null.")
    private Long id;

    @NullOrNotBlank(message = "Article title cannot be blank.")
    @Size(max = 256, message = "Title cannot have a length longer than 256 characters.")
    private String title;

    @NullOrNotBlank(message = "Article content cannot be blank.")
    private String content;

}
