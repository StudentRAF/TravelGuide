package rs.raf.student.mapper;

import jakarta.inject.Inject;
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.comment.CommentGetDto;
import rs.raf.student.model.Comment;
import rs.raf.student.model.User;

import java.time.LocalDate;

public class CommentMapper {

    @Inject
    private UserMapper userMapper;

    public CommentGetDto mapDto(Comment comment, User user) {
        return new CommentGetDto
            (
                comment.getId(),
                comment.getContent(),
                userMapper.mapDto(user, null),
                comment.getCreatedAt()

            );
    }

    public Comment map(Comment comment, CommentCreateDto createDto) {
        comment.setContent(createDto.getContent());
        comment.setAuthorId(createDto.getAuthorId());
        comment.setArticleId(createDto.getArticleId());
        comment.setCreatedAt(LocalDate.now());

        return comment;
    }

}
