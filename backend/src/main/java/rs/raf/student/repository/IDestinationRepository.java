package rs.raf.student.repository;

import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.destination.DestinationCreateDto;
import rs.raf.student.dto.destination.DestinationUpdateDto;
import rs.raf.student.model.Destination;

import java.util.List;

public interface IDestinationRepository {

    List<Destination> findAll();

    List<Destination> findAll(Pageable pageable);

    Destination findById(Long id);

    Destination create(DestinationCreateDto createDto);

    Destination update(DestinationUpdateDto updateDto);

    void delete(Long id);

}
