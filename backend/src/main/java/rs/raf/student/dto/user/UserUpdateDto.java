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
