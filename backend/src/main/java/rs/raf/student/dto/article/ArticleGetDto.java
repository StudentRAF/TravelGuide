package rs.raf.student.dto.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.student.dto.activity.ActivityGetDto;
import rs.raf.student.dto.comment.CommentGetDto;
import rs.raf.student.dto.destination.DestinationGetDto;
import rs.raf.student.dto.user.UserGetDto;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleGetDto {

    private Long id;

    private String title;

    private String content;

    private UserGetDto author;

    private DestinationGetDto destination;

    private List<ActivityGetDto> activities;

    private List<CommentGetDto> comments;

    @JsonProperty("created_at")
    private LocalDate createdAt;

    private Long visits;

}
