package rs.raf.student.dto.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateDto {

    @NotBlank(message = "Title cannot be empty or null.")
    @Size(max = 256, message = "Title cannot have a length longer than 256 characters.")
    private String title;

    @NotBlank(message = "Content cannot be empty or null.")
    private String content;

    @JsonProperty("author_id")
    @NotNull(message = "Author id cannot be null.")
    private Long authorId;

    @JsonProperty("destination_id")
    @NotNull(message = "Destination id cannot be null.")
    private Long destinationId;

    @NotEmpty
    private List<Long> activities;

}
