package rs.raf.student.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDto {

    @NotBlank(message = "Content cannot be empty or null.")
    private String content;

    @JsonProperty("display_name")
    @NotBlank(message = "Display name cannot be empty or null.")
    @Size(max = 128, message = "Display name cannot have a length longer than 128 characters.")
    private String displayName;

    @JsonProperty("article_id")
    @NotNull(message = "Article id cannot be null.")
    private Long articleId;

}
