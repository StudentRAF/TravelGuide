package rs.raf.student.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    @NotNull(message = "User id cannot be null.")
    private Long id;

    @JsonProperty("first_name")
    @NotEmpty(message = "First name cannot be empty.")
    @Size(max = 64, message = "First name cannot have a length longer than 64 characters.")
    private String firstName;

    @JsonProperty("last_name")
    @NotEmpty(message = "Last name cannot be empty.")
    @Size(max = 64, message = "Last name cannot have a length longer than 64 characters.")
    private String lastName;

    @Email(message = "Email does not have valid format.")
    private String email;

    @JsonProperty("role_id")
    private Long roleId;

    private Boolean enabled;

}
