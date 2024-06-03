package rs.raf.student.service;

import jakarta.inject.Inject;
import rs.raf.student.domain.Page;
import rs.raf.student.domain.PageImplementation;
import rs.raf.student.domain.PageableImplementation;
import rs.raf.student.dto.user.UserCreateDto;
import rs.raf.student.dto.user.UserGetDto;
import rs.raf.student.mapper.UserMapper;
import rs.raf.student.model.User;
import rs.raf.student.model.UserRole;
import rs.raf.student.repository.IUserRepository;
import rs.raf.student.repository.IUserRoleRepository;
import rs.raf.student.utils.Utilities;

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

    public Page<UserGetDto> getAll() {
        Page<User> page = repository.findAll(PageableImplementation.of(0, 10));

        Map<Long, UserRole> userRoles = userRoleRepository.findByIds(page.getContent()
                                                                         .stream()
                                                                         .map(User::getRoleId)
                                                                         .distinct()
                                                                         .toList())
                                                            .stream()
                                                            .collect(Collectors.toMap(UserRole::getId, Function.identity()));

        return PageImplementation.of(Utilities.createStream(page.iterator())
                                              .map(user -> mapper.mapDto(user, userRoles.get(user.getRoleId())))
                                              .toList(),
                                     page.getPageSize());
    }

    public UserGetDto getById(Long id) {
        User     user     = repository.findById(id);
        UserRole userRole = userRoleRepository.findById(user.getRoleId());

        return mapper.mapDto(user, userRole);
    }

    public UserGetDto create(UserCreateDto createDto) {
        User     user     = repository.create(createDto);
        UserRole userRole = userRoleRepository.findById(user.getRoleId());

        return mapper.mapDto(user, userRole);
    }

}
