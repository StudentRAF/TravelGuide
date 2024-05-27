package rs.raf.student.dto.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateDto {

    @NotNull(message = "Article id cannot be null.")
    private Long id;

    @NotBlank(message = "Title cannot be empty or null.")
    @Size(max = 256, message = "Title cannot have a length longer than 256 characters.")
    private String title;

    @NotBlank(message = "Content cannot be empty or null.")
    private String content;

}
