package rs.raf.student.repository.comment;

import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.model.Comment;
import rs.raf.student.repository.ICommentRepository;
import rs.raf.student.repository.PostgresAbstractRepository;

import java.util.List;
import java.util.Optional;

public class PostgresCommentRepository extends PostgresAbstractRepository implements ICommentRepository {

    @Override
    public List<Comment> findByArticle(Long articleId) {
        return List.of();
    }

    @Override
    public Optional<Comment> create(CommentCreateDto createDto) {
        return Optional.empty();
    }

}
