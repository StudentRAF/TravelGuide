package rs.raf.student.mapper;

import jakarta.inject.Inject;
import rs.raf.student.dto.article.ArticleCreateDto;
import rs.raf.student.dto.article.ArticleGetDto;
import rs.raf.student.dto.article.ArticleUpdateDto;
import rs.raf.student.model.Article;
import rs.raf.student.model.Destination;
import rs.raf.student.model.User;

import java.time.LocalDate;

public class ArticleMapper {

    @Inject
    private UserMapper userMapper;

    @Inject
    private DestinationMapper destinationMapper;

    public ArticleGetDto mapDto(Article article, User user,Destination destination) {
        return new ArticleGetDto
            (
                article.getId(),
                article.getTitle(),
                article.getContent(),
                userMapper.mapDto(user, null),
                destinationMapper.mapDto(destination),
                article.getCreatedAt(),
                article.getVisits()
            );
    }

    public Article map(ArticleCreateDto createDto) {
        return map(new Article(), createDto);
    }

    public Article map(Article article, ArticleCreateDto createDto) {
        article.setTitle(createDto.getTitle());
        article.setContent(createDto.getContent());
        article.setAuthorId(createDto.getAuthorId());
        article.setDestinationId(createDto.getDestinationId());
        article.setCreatedAt(LocalDate.now());
        article.setVisits(0L);

        return article;
    }

    public Article map(ArticleUpdateDto updateDto) {
        return map(new Article(), updateDto);
    }

    public Article map(Article article, ArticleUpdateDto updateDto) {
        article.setTitle(updateDto.getTitle());
        article.setContent(updateDto.getContent());

        return article;
    }

}
