package rs.raf.student.repository;

import rs.raf.student.domain.Page;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.exception.TGException;
import rs.raf.student.model.User;

import java.util.List;

public interface IUserRepository {

    Page<User> findAll(Pageable pageable) throws TGException;

    User findById(Long id) throws TGException;

    List<User> findByIds(List<Long> ids) throws TGException;

    User findByEmail(String email) throws TGException;

    User create(UserCreateDto createDto) throws TGException;

    User update(UserUpdateDto updateDto) throws TGException;

}
