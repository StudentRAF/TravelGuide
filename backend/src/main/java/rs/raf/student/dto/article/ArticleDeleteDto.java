package rs.raf.student.dto.article;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDeleteDto {

    @NotNull(message = "Article id cannot be null.")
    private Long id;

}
