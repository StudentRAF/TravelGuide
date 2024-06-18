package rs.raf.student.service;

import jakarta.inject.Inject;
import rs.raf.student.domain.Page;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.activity.ActivityCreateDto;
import rs.raf.student.dto.activity.ActivityGetDto;
import rs.raf.student.mapper.ActivityMapper;
import rs.raf.student.repository.IActivityRepository;

public class ActivityService {

    @Inject
    private ActivityMapper mapper;

    @Inject
    private IActivityRepository repository;


    public Page<ActivityGetDto> getAll(Pageable pageable) {
        return Page.of(repository.findAll(pageable)
                                 .stream()
                                 .map(mapper::mapDto)
                                 .toList(),
                       pageable);
    }

    public ActivityGetDto getById(Long id) {
        return mapper.mapDto(repository.findById(id));
    }

    public ActivityGetDto create(ActivityCreateDto createDto) {
        return mapper.mapDto(repository.create(createDto));
    }

}
