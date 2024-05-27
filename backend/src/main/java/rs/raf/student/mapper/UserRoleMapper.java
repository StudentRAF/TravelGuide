package rs.raf.student.mapper;

import rs.raf.student.dto.user_role.UserRoleGetDto;
import rs.raf.student.model.UserRole;

public class UserRoleMapper {

    public UserRoleGetDto mapDto(UserRole userRole) {
        return new UserRoleGetDto
            (
                userRole.getId(),
                userRole.getName()
            );
    }

}
