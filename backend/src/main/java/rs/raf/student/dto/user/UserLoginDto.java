package rs.raf.student.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Password cannot be empty or null.")
    private String password;

}
