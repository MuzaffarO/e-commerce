package nt.uz.ecommerce.service.mapper;

import nt.uz.ecommerce.dto.DeliveryDto;
import nt.uz.ecommerce.dto.UsersDto;
import nt.uz.ecommerce.model.Delivery;
import nt.uz.ecommerce.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class DeliveryMapper implements CommonMapper<DeliveryDto, Delivery> {
    @Mapping(target = "isActive", expression = "java(true)")
    public abstract Delivery toEntity(DeliveryDto dto);

}
