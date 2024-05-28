package rs.raf.student;

import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import rs.raf.student.mapper.ActivityMapper;
import rs.raf.student.mapper.ArticleMapper;
import rs.raf.student.mapper.CommentMapper;
import rs.raf.student.mapper.DestinationMapper;
import rs.raf.student.mapper.UserMapper;
import rs.raf.student.mapper.UserRoleMapper;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.repository.user.PostgresUserRepository;
import rs.raf.student.service.UserService;

public class InjectionBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(PostgresUserRepository.class).to(IUserRepository.class).in(Singleton.class);

        bindAsContract(UserService.class);

        bindAsContract(ActivityMapper.class);
        bindAsContract(ArticleMapper.class);
        bindAsContract(CommentMapper.class);
        bindAsContract(DestinationMapper.class);
        bindAsContract(UserMapper.class);
        bindAsContract(UserRoleMapper.class);
    }

}
