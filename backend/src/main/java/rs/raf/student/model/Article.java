package rs.raf.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Long id;

    private String title;

    private String content;

    private Long authorId;

    private Long destinationId;

    private LocalDate createdAt;

    private Long visits;

}
