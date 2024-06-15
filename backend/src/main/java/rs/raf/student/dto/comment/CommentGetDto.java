package rs.raf.student.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.student.adapter.time.LocalDateSerializer;

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
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate createdAt;

}
