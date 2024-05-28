package rs.raf.student.repository;

import rs.raf.student.domain.Page;
import rs.raf.student.model.User;

import java.util.Optional;

public interface IUserRepository {

    Page<User> findAll(int page, int pageSize);

    Optional<User> findById(Long id);

}
