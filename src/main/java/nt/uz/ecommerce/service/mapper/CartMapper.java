package nt.uz.ecommerce.service.mapper;

import nt.uz.ecommerce.dto.CartDto;
import nt.uz.ecommerce.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper extends CommonMapper<CartDto, Cart> {
}
