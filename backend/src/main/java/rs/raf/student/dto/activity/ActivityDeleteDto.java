package rs.raf.student.dto.activity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDeleteDto {

    @NotNull(message = "Activity id cannot be null.")
    private Long id;

}
