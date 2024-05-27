package rs.raf.student.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDto {

    @NotBlank(message = "Content cannot be empty or null.")
    private String content;

    @JsonProperty("author_id")
    @NotNull(message = "Author id cannot be null.")
    private Long authorId;

    @JsonProperty("article_id")
    @NotNull(message = "Article id cannot be null.")
    private Long articleId;

}
