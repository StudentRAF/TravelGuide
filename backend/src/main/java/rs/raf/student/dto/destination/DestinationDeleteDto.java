package rs.raf.student.dto.destination;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDeleteDto {

    @NotNull(message = "Destination id cannot be null.")
    private Long id;

}
