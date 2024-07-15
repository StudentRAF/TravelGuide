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

package rs.raf.student.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.inject.Inject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import rs.raf.student.domain.Page;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserGetDto;
import rs.raf.student.dto.user.UserLoginDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.exception.ExceptionType;
import rs.raf.student.exception.TGException;
import rs.raf.student.mapper.UserMapper;
import rs.raf.student.model.User;
import rs.raf.student.model.UserRole;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.repository.IUserRoleRepository;
import rs.raf.student.utils.Utilities;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserService {

    @Inject
    private UserMapper mapper;

    @Inject
    private IUserRepository repository;

    @Inject
    private IUserRoleRepository userRoleRepository;

    private final Algorithm algorithm = Algorithm.HMAC512("travel-guide");

    public Page<UserGetDto> getAll(Pageable pageable) {
        List<User> page = repository.findAll(pageable);

        Map<Long, UserRole> userRoles = userRoleRepository.findByIds(page.stream()
                                                                         .map(User::getRoleId)
                                                                         .distinct()
                                                                         .toList())
                                                            .stream()
                                                            .collect(Collectors.toMap(UserRole::getId, Function.identity()));

        return Page.of(Utilities.createStream(page.iterator())
                                .map(user -> mapper.mapDto(user, userRoles.get(user.getRoleId())))
                                .toList(),
                       pageable);
    }

    public UserGetDto getById(Long id) {
        return mapUser(repository.findById(id));
    }

    public Pair<String, UserGetDto> login(UserLoginDto loginDto) {
        User user = repository.findByEmail(loginDto.getEmail());

        if (user == null)
            throw new TGException(ExceptionType.SERVICE_USER_LOGIN_INVALID_EMAIL,
                                  loginDto.getEmail(),
                                  loginDto.getPassword());

        if (!user.getPassword().equals(Utilities.hashPassword(loginDto.getPassword(), user.getSalt())))
            throw new TGException(ExceptionType.SERVICE_USER_LOGIN_INVALID_PASSWORD,
                                  loginDto.getEmail(),
                                  loginDto.getPassword());

        UserGetDto userDto = mapUser(user);

        Instant issuedAt  = Instant.now();
        Instant expiresAt = LocalDateTime.now()
                                         .plusHours(1)
                                         .toInstant(ZoneId.systemDefault()
                                                          .getRules()
                                                          .getOffset(issuedAt));

        String token = JWT.create()
                          .withIssuedAt(issuedAt)
                          .withExpiresAt(expiresAt)
                          .withSubject(user.getEmail())
                          .withPayload(Map.of("role", userDto.getUserRole()
                                                                .getName()))
                          .sign(algorithm);

        return ImmutablePair.of(token, userDto);
    }

    public UserRole decodeRole(String token) {
        try {
            return UserRole.valueOf(JWT.require(algorithm)
                                       .build()
                                       .verify(token)
                                       .getClaim("role")
                                       .asString())
                           .get();
        }
        catch (Exception exception) {
            throw new TGException(ExceptionType.SERVICE_USER_DECODE_INVALID_TOKEN,
                                  token,
                                  exception.getMessage());
        }

    }

    public UserGetDto create(UserCreateDto createDto) {
        return mapUser(repository.create(createDto));
    }

    public UserGetDto update(UserUpdateDto updateDto) {
        return mapUser(repository.update(updateDto));
    }

    private UserGetDto mapUser(User user) {
        UserRole userRole = userRoleRepository.findById(user.getRoleId());

        return mapper.mapDto(user, userRole);
    }

}
