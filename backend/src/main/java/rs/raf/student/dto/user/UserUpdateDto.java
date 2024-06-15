package rs.raf.student.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.student.validation.NullOrNotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    @NotNull(message = "User id cannot be null.")
    private Long id;

    @JsonProperty("first_name")
    @NullOrNotBlank(message = "Users first name cannot be blank.")
    @Size(max = 64, message = "First name cannot have a length longer than 64 characters.")
    private String firstName;

    @JsonProperty("last_name")
    @NullOrNotBlank(message = "Users last name cannot be blank.")
    @Size(max = 64, message = "Last name cannot have a length longer than 64 characters.")
    private String lastName;

    @Email(message = "Email does not have valid format.")
    private String email;

    @JsonProperty("role_id")
    private Long roleId;

    private Boolean enabled;

}
