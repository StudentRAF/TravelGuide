package rs.raf.student.repository;

import rs.raf.student.domain.Page;
import rs.raf.student.model.UserRole;

import java.util.Optional;

public interface IUserRoleRepository {

    Page<UserRole> findAll();

    Optional<UserRole> findById(Long id);

    Optional<UserRole> findByName(String name);

}
