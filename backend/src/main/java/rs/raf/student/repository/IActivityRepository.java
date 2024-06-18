package rs.raf.student.repository;

import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.activity.ActivityCreateDto;
import rs.raf.student.model.Activity;

import java.util.List;

public interface IActivityRepository {

    List<Activity> findAll(Pageable pageable);

    Activity findById(Long id);

    Activity findByName(String name);

    List<Activity> findByDestination(Long destinationId); //NOTE: for ?

    Activity create(ActivityCreateDto createDto);

}
