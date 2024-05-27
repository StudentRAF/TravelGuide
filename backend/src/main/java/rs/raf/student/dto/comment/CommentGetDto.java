package rs.raf.student.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.student.dto.user.UserGetDto;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentGetDto {

    private Long id;

    private String content;

    private UserGetDto author;

    @JsonProperty("created_at")
    private LocalDate createdAt;

}
