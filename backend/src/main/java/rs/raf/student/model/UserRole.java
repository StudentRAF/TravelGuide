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

package rs.raf.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Optional;

@Data
@Accessors(chain = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    public static final UserRole ANYONE = new UserRole(0L, "Anyone");
    public static final UserRole ADMIN  = new UserRole(1L, "Admin");
    public static final UserRole EDITOR = new UserRole(2L, "Editor");

    private static final List<UserRole> roles = List.of(
        ADMIN,
        EDITOR
    );

    public static Optional<UserRole> valueOf(String name) {
        return roles.stream()
                    .filter(role -> role.name.equalsIgnoreCase(name))
                    .findFirst();
    }

    private Long id;

    private String name;

}
