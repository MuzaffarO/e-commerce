package nt.uz.ecommerce.service.mapper;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.OrderDto;
import nt.uz.ecommerce.dto.ProductDto;
import nt.uz.ecommerce.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderMapper implements CommonMapper<OrderDto, Order> {
    @Autowired
    protected ProductMapper productMapper;
    @Autowired
    protected UsersMapper usersMapper;
    @Autowired
    protected AddressMapper addressMapper;

    @Mapping(target = "products", expression = "java(order.getProducts().stream().map(productMapper::toDto).toList())")
    @Mapping(target = "user", expression = "java(usersMapper.toDto(order.getUser()))")
    @Mapping(target = "address", expression = "java(addressMapper.toDto(order.getAddress()))")
    public abstract OrderDto toDto(Order order);
    public abstract Order toEntity(OrderDto orderDto);
}
