package rs.raf.student.service;

import jakarta.inject.Inject;
import rs.raf.student.domain.Page;
import rs.raf.student.domain.PageImplementation;
import rs.raf.student.domain.Pageable;
import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserGetDto;
import rs.raf.student.dto.user.UserUpdateDto;
import rs.raf.student.mapper.UserMapper;
import rs.raf.student.model.User;
import rs.raf.student.model.UserRole;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.repository.IUserRoleRepository;
import rs.raf.student.utils.Utilities;

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

    public Page<UserGetDto> getAll(Pageable pageable) {
        List<User> page = repository.findAll(pageable);

        Map<Long, UserRole> userRoles = userRoleRepository.findByIds(page.stream()
                                                                         .map(User::getRoleId)
                                                                         .distinct()
                                                                         .toList())
                                                            .stream()
                                                            .collect(Collectors.toMap(UserRole::getId, Function.identity()));

        return PageImplementation.of(Utilities.createStream(page.iterator())
                                              .map(user -> mapper.mapDto(user, userRoles.get(user.getRoleId())))
                                              .toList(),
                                     pageable.getPage(),
                                     pageable.getPageSize());
    }

    public UserGetDto getById(Long id) {
        return mapUser(repository.findById(id));
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
