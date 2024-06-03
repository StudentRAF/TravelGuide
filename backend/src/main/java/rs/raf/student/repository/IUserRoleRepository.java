package rs.raf.student.repository;

import rs.raf.student.exception.TGException;
import rs.raf.student.model.UserRole;

import java.util.List;

public interface IUserRoleRepository {

    List<UserRole> findAll() throws TGException;

    List<UserRole> findByIds(List<Long> ids) throws TGException;

    UserRole findById(Long id) throws TGException;

    UserRole findByName(String name) throws TGException;

}
