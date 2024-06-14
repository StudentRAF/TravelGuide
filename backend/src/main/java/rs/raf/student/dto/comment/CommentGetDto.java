package rs.raf.student.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetDto {

    private Long id;

    private String content;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("created_at")
    private LocalDate createdAt;

}
