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
        user.setRoleId(createDto.getUserRoleId());

        return user;
    }

    public User map(UserUpdateDto updateDto) {
        return map(new User(), updateDto);
    }

    public User map(User user, UserUpdateDto updateDto) {
        user.setEmail(updateDto.getEmail());
        user.setFirstName(updateDto.getFirstName());
        user.setLastName(updateDto.getLastName());
        user.setRoleId(updateDto.getUserRoleId());
        user.setEnabled(updateDto.getEnabled());

        return user;
    }

}
