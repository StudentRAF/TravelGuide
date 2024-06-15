package rs.raf.student;

import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import rs.raf.student.mapper.ActivityMapper;
import rs.raf.student.mapper.ArticleMapper;
import rs.raf.student.mapper.CommentMapper;
import rs.raf.student.mapper.DestinationMapper;
import rs.raf.student.mapper.UserMapper;
import rs.raf.student.mapper.UserRoleMapper;
import rs.raf.student.repository.IActivityRepository;
import rs.raf.student.repository.IArticleActivityRepository;
import rs.raf.student.repository.IArticleRepository;
import rs.raf.student.repository.ICommentRepository;
import rs.raf.student.repository.IDestinationRepository;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.repository.IUserRoleRepository;
import rs.raf.student.repository.activity.PostgresActivityRepository;
import rs.raf.student.repository.article.PostgresArticleRepository;
import rs.raf.student.repository.article_activity.PostgresArticleActivityRepository;
import rs.raf.student.repository.comment.PostgresCommentRepository;
import rs.raf.student.repository.destination.PostgresDestinationRepository;
import rs.raf.student.repository.user.PostgresUserRepository;
import rs.raf.student.repository.user_role.PostgresUserRoleRepository;
import rs.raf.student.service.ArticleService;
import rs.raf.student.service.CommentService;
import rs.raf.student.service.DestinationService;
import rs.raf.student.service.UserService;

public class InjectionBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(PostgresActivityRepository.class)       .to(IActivityRepository.class)       .in(Singleton.class);
        bind(PostgresArticleActivityRepository.class).to(IArticleActivityRepository.class).in(Singleton.class);
        bind(PostgresArticleRepository.class)        .to(IArticleRepository.class)        .in(Singleton.class);
        bind(PostgresCommentRepository.class)        .to(ICommentRepository.class)        .in(Singleton.class);
        bind(PostgresDestinationRepository.class)    .to(IDestinationRepository.class)    .in(Singleton.class);
        bind(PostgresUserRepository.class)           .to(IUserRepository.class)           .in(Singleton.class);
        bind(PostgresUserRoleRepository.class)       .to(IUserRoleRepository.class)       .in(Singleton.class);

        bindAsContract(ArticleService.class);
        bindAsContract(CommentService.class);
        bindAsContract(DestinationService.class);
        bindAsContract(UserService.class);

        bindAsContract(ActivityMapper.class);
        bindAsContract(ArticleMapper.class);
        bindAsContract(CommentMapper.class);
        bindAsContract(DestinationMapper.class);
        bindAsContract(UserMapper.class);
        bindAsContract(UserRoleMapper.class);
    }

}
