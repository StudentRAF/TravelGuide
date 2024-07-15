/*
 * Copyright (C) 2024. Nemanja Radovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rs.raf.student.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    @JsonProperty("first_name")
    @NotBlank(message = "First name cannot be empty or null.")
    @Size(max = 64, message = "First name cannot have a length longer than 64 characters.")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Last name cannot be empty or null.")
    @Size(max = 64, message = "Last name cannot have a length longer than 64 characters.")
    private String lastName;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Email does not have valid format.")
    private String email;

    @NotBlank(message = "Password cannot be empty or null.")
    @Size(min = 8, message = "Password must contain at least 8 characters.")
    private String password;

    @JsonProperty("confirm_password")
    @NotBlank(message = "Password cannot be empty or null.")
    @Size(min = 8, message = "Password must contain at least 8 characters.")
    private String confirmPassword;

    @JsonProperty("role_id")
    @NotNull(message = "User role id cannot be null.")
    private Long roleId;

}
