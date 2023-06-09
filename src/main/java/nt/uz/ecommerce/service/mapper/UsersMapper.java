package nt.uz.ecommerce.service.mapper;

import nt.uz.ecommerce.dto.UsersDto;
import nt.uz.ecommerce.model.Users;
import nt.uz.ecommerce.security.UserRoles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


@Mapper(componentModel = "spring")
public abstract class UsersMapper implements CommonMapper<UsersDto, Users> {

    @Autowired
    protected PasswordEncoder encoder;

    UserRoles defUser = UserRoles.ROLE_USER;

    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "enabled", expression = "java(true)")
//    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "roles", expression = "java(defUser)", ignore = true)
    @Mapping(target = "password", expression = "java(encoder.encode(dto.getPassword()))")
    public abstract Users toEntity(UsersDto dto);

    @Mapping(target = "password", expression = "java(null)")
    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    public abstract UsersDto toDto(Users entity);
}
