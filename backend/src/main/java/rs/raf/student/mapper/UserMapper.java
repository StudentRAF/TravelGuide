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

package rs.raf.student.mapper;

import jakarta.inject.Inject;
import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserGetDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.model.User;
import rs.raf.student.model.UserRole;
import rs.raf.student.utils.Utilities;

public class UserMapper {

    @Inject
    private UserRoleMapper userRoleMapper;

    public UserGetDto mapDto(User user, UserRole userRole) {
        return new UserGetDto
            (
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                userRole == null ? null : userRoleMapper.mapDto(userRole),
                user.getEnabled()
            );
    }

    public User map(UserCreateDto createDto) {
        return map(new User(), createDto);
    }

    public User map(User user, UserCreateDto createDto) {
        String salt = Utilities.generateSalt();

        user.setEmail(createDto.getEmail());
        user.setFirstName(createDto.getFirstName());
        user.setLastName(createDto.getLastName());
        user.setSalt(salt);
        user.setPassword(Utilities.hashPassword(createDto.getPassword(), salt));
        user.setRoleId(createDto.getRoleId());

        return user;
    }

    public User map(UserUpdateDto updateDto) {
        return map(new User(), updateDto);
    }

    public User map(User user, UserUpdateDto updateDto) {
        user.setEmail(updateDto.getEmail());
        user.setFirstName(updateDto.getFirstName());
        user.setLastName(updateDto.getLastName());
        user.setRoleId(updateDto.getRoleId());
        user.setEnabled(updateDto.getEnabled());

        return user;
    }

}
