package rs.raf.student.repository;

import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.model.Comment;

import java.util.List;
import java.util.Optional;

public interface ICommentRepository {

    List<Comment> findByArticle(Long articleId);

    Optional<Comment> create(CommentCreateDto createDto);
}
