package rs.raf.student.service;

import jakarta.inject.Inject;
import rs.raf.student.dto.activity.ActivityCreateDto;
import rs.raf.student.dto.activity.ActivityGetDto;
import rs.raf.student.mapper.ActivityMapper;
import rs.raf.student.repository.IActivityRepository;

import java.util.List;

public class ActivityService {

    @Inject
    private ActivityMapper mapper;

    @Inject
    private IActivityRepository repository;


    public List<ActivityGetDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::mapDto)
                         .toList();
    }

    public ActivityGetDto getById(Long id) {
        return mapper.mapDto(repository.findById(id));
    }

    public ActivityGetDto create(ActivityCreateDto createDto) {
        return mapper.mapDto(repository.create(createDto));
    }

}
