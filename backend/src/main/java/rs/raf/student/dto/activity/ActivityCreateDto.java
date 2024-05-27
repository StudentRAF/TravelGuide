package rs.raf.student.dto.activity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityCreateDto {

    @NotBlank(message = "Activity name cannot be empty or null.")
    @Size(max = 256, message = "Activity name cannot have a length longer than 256 characters.")
    private String name;

}
