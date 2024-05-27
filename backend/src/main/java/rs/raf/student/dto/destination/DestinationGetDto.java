package rs.raf.student.dto.destination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationGetDto {

    private Long id;

    private String name;

    private String description;

}
