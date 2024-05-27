package rs.raf.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleActivity {

    private Long id;

    private Long articleId;

    private Long activityId;

}
