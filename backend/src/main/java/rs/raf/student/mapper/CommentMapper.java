package rs.raf.student.mapper;

import jakarta.inject.Inject;
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.comment.CommentGetDto;
import rs.raf.student.model.Comment;

import java.time.LocalDate;

public class CommentMapper {

    @Inject
    private UserMapper userMapper;

    public CommentGetDto mapDto(Comment comment) {
        return new CommentGetDto
            (
                comment.getId(),
                comment.getContent(),
                comment.getDisplayName(),
                comment.getCreatedAt()

            );
    }

    public Comment map(CommentCreateDto createDto) {
        return map(new Comment(), createDto);
    }

    public Comment map(Comment comment, CommentCreateDto createDto) {
        comment.setContent(createDto.getContent());
        comment.setDisplayName(createDto.getDisplayName());
        comment.setArticleId(createDto.getArticleId());
        comment.setCreatedAt(LocalDate.now());

        return comment;
    }

}
