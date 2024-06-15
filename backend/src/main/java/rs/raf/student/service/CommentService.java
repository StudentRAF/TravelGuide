package rs.raf.student.service;

import jakarta.inject.Inject;
import rs.raf.student.dto.comment.CommentCreateDto;
import rs.raf.student.dto.comment.CommentGetDto;
import rs.raf.student.mapper.CommentMapper;
import rs.raf.student.repository.ICommentRepository;

public class CommentService {

    @Inject
    private CommentMapper mapper;

    @Inject
    private ICommentRepository repository;

    public CommentGetDto create(CommentCreateDto createDto) {
        return mapper.mapDto(repository.create(createDto));
    }

}
