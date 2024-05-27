package rs.raf.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Long id;

    private String content;

    private Long authorId;

    private Long articleId;

    private LocalDate createdAt;

}
