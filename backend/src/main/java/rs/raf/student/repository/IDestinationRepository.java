package rs.raf.student.repository;

import rs.raf.student.domain.Page;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.destination.DestinationCreateDto;
import rs.raf.student.dto.destination.DestinationUpdateDto;
import rs.raf.student.model.Destination;

import java.util.List;
import java.util.Optional;

public interface IDestinationRepository {

    List<Destination> findAll();

    Page<Destination> findAll(Pageable pageable);

    Optional<Destination> findById(Long id);

    Optional<Destination> create(DestinationCreateDto createDto);

    Optional<Destination> update(DestinationUpdateDto updateDto);

    Optional<Destination> delete(Long id);

}
