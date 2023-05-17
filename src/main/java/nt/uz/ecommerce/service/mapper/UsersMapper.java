package nt.uz.ecommerce.service.mapper;

import nt.uz.ecommerce.dto.UsersDto;
import nt.uz.ecommerce.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public abstract class UsersMapper implements CommonMapper<UsersDto, Users> {
    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "role", ignore = true)
    public abstract Users toEntity(UsersDto dto);

    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    public abstract UsersDto toDto(Users entity);
}
