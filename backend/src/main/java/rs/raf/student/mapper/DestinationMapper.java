package rs.raf.student.mapper;

import rs.raf.student.dto.destination.DestinationCreateDto;
import rs.raf.student.dto.destination.DestinationGetDto;
import rs.raf.student.dto.destination.DestinationUpdateDto;
import rs.raf.student.model.Destination;

public class DestinationMapper {

    public DestinationGetDto mapDto(Destination destination) {
        return new DestinationGetDto
            (
                destination.getId(),
                destination.getName(),
                destination.getDescription()
            );
    }

    public Destination map(Destination destination, DestinationCreateDto createDto) {
        destination.setName(createDto.getName());
        destination.setDescription(createDto.getDescription());

        return destination;
    }

    public Destination map(Destination destination, DestinationUpdateDto updateDto) {
        destination.setName(updateDto.getName());
        destination.setDescription(updateDto.getDescription());

        return destination;
    }

}
