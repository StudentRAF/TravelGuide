package rs.raf.student.repository;

import rs.raf.student.dto.activity.ActivityCreateDto;
import rs.raf.student.model.Activity;

import java.util.List;
import java.util.Optional;

public interface IActivityRepository {

    List<Activity> findAll();

    List<Activity> findByDestination(Long destinationId); //NOTE: for ?

    Optional<Activity> findById(Long id);

    Optional<Activity> findByName(String name);

    Optional<Activity> create(ActivityCreateDto createDto);

}
