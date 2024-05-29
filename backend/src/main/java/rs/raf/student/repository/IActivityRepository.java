package rs.raf.student.repository;

import rs.raf.student.model.Activity;

import java.util.Optional;

public interface IActivityRepository {

    Optional<Activity> findById(Long id);

}
