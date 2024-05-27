package rs.raf.student.dto.user;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.student.dto.user_role.UserRoleGetDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGetDto {

    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    @JsonProperty("user_role")
    private UserRoleGetDto userRole;

    private Boolean enabled;

}
