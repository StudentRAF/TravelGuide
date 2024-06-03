package rs.raf.student.service;

import jakarta.inject.Inject;
import rs.raf.student.domain.Page;
import rs.raf.student.domain.PageImplementation;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.destination.DestinationCreateDto;
import rs.raf.student.dto.destination.DestinationGetDto;
import rs.raf.student.dto.destination.DestinationUpdateDto;
import rs.raf.student.mapper.DestinationMapper;
import rs.raf.student.repository.IDestinationRepository;

import java.util.List;

public class DestinationService {

    @Inject
    private DestinationMapper mapper;

    @Inject
    private IDestinationRepository repository;

    public List<DestinationGetDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::mapDto)
                         .toList();
    }

    public Page<DestinationGetDto> getAll(Pageable pageable) {
        return PageImplementation.of(repository.findAll(pageable)
                                               .stream()
                                               .map(mapper::mapDto)
                                               .toList(),
                                     pageable.getPageNumber(),
                                     pageable.getPageSize());
    }

    public DestinationGetDto getById(Long id) {
        return mapper.mapDto(repository.findById(id));
    }

    public DestinationGetDto create(DestinationCreateDto createDto) {
        return mapper.mapDto(repository.create(createDto));
    }

    public DestinationGetDto update(DestinationUpdateDto updateDto) {
        return mapper.mapDto(repository.update(updateDto));
    }

    public void delete(Long id) {
        repository.delete(id);
    }

}
