package rs.raf.student.repository.destination;

import rs.raf.student.domain.Page;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.destination.DestinationCreateDto;
import rs.raf.student.dto.destination.DestinationUpdateDto;
import rs.raf.student.model.Destination;
import rs.raf.student.repository.IDestinationRepository;
import rs.raf.student.repository.PostgresAbstractRepository;

import java.util.List;
import java.util.Optional;

public class PostgresDestinationRepository extends PostgresAbstractRepository implements IDestinationRepository {

    @Override
    public List<Destination> findAll() {
        return List.of();
    }

    @Override
    public Page<Destination> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Destination> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Destination> create(DestinationCreateDto createDto) {
        return Optional.empty();
    }

    @Override
    public Optional<Destination> update(DestinationUpdateDto updateDto) {
        return Optional.empty();
    }

    @Override
    public Optional<Destination> delete(Long id) {
        return Optional.empty();
    }

}
