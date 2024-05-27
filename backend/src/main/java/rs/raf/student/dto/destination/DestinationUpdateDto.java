package rs.raf.student.dto.destination;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationUpdateDto {

    @NotNull(message = "Destination id cannot be null.")
    private Long id;

    @NotBlank(message = "Destination name cannot be empty or null.")
    @Size(max = 256, message = "Destination name cannot have a length longer than 64 characters.")
    private String name;

    @NotBlank(message = "Description cannot be empty or null.")
    private String description;

}
