package rs.raf.student.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {

    @NotNull(message = "Email cannot be null or empty.")
    @Email(message = "Email does not have valid format.")
    private String email;

    @Size(min = 8, message = "password must contain at least 8 characters.")
    private String password;

}
