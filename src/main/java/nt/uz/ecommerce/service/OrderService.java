package nt.uz.ecommerce.service;


import nt.uz.ecommerce.dto.AddressDto;
import nt.uz.ecommerce.dto.OrderDto;
import nt.uz.ecommerce.dto.ResponseDto;

public interface OrderService {
    ResponseDto<OrderDto> createOrder(Integer cartId, AddressDto addressDto);
}
