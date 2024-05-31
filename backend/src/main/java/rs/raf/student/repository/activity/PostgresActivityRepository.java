package rs.raf.student.repository.activity;

import rs.raf.student.dto.activity.ActivityCreateDto;
import rs.raf.student.model.Activity;
import rs.raf.student.repository.IActivityRepository;
import rs.raf.student.repository.PostgresAbstractRepository;

import java.util.List;
import java.util.Optional;

public class PostgresActivityRepository extends PostgresAbstractRepository implements IActivityRepository {

    @Override
    public List<Activity> findAll() {
        return List.of();
    }

    @Override
    public List<Activity> findByDestination(Long destinationId) {
        return List.of();
    }

    @Override
    public Optional<Activity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Activity> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Activity> create(ActivityCreateDto createDto) {
        return Optional.empty();
    }

}
