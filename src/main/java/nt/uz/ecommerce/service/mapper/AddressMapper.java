package nt.uz.ecommerce.service.mapper;

import nt.uz.ecommerce.dto.AddressDto;
import nt.uz.ecommerce.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper extends CommonMapper<AddressDto, Address>{
}
