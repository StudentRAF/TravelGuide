package rs.raf.student.repository;

import rs.raf.student.domain.Page;
import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.model.User;

import java.util.Optional;

public interface IUserRepository {

    Page<User> findAll(int page, int pageSize);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> create(UserCreateDto createDto);

    Optional<User> update(UserUpdateDto updateDto);

    Optional<User> disable(Long id);

}
