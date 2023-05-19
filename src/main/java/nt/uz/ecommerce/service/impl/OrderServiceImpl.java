package nt.uz.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.AddressDto;
import nt.uz.ecommerce.dto.OrderDto;
import nt.uz.ecommerce.dto.OrderStatus;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.model.Cart;
import nt.uz.ecommerce.model.Order;
import nt.uz.ecommerce.repository.CartRepository;
import nt.uz.ecommerce.repository.OrderRepository;
import nt.uz.ecommerce.repository.ProductRepository;
import nt.uz.ecommerce.repository.UsersRepository;
import nt.uz.ecommerce.service.OrderService;
import nt.uz.ecommerce.service.mapper.AddressMapper;
import nt.uz.ecommerce.service.mapper.CartMapper;
import nt.uz.ecommerce.service.mapper.OrderMapper;
import nt.uz.ecommerce.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static nt.uz.ecommerce.service.additional.AppStatusMessages.*;
import static nt.uz.ecommerce.service.additional.AppStatusCodes.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CartRepository cartRepository;
//    private final CartMapper cartMapper;
//    private final UsersRepository usersRepository;
    private final OrderMapper orderMapper;
    private final AddressMapper addressMapper;
    private final OrderRepository orderRepository;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    @Override
    public ResponseDto<OrderDto> createOrder(Integer cartId, AddressDto addressDto) {
        Optional<Cart> cartById = cartRepository.findById(cartId);
        if (cartById.isEmpty())
            return ResponseDto.<OrderDto>builder()
                    .message("Cart " + NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
        Order order = Order.builder()
                .address(addressMapper.toEntity(addressDto))
                .user(cartById.get().getUser())
                .orderStatus(OrderStatus.BEING_COLLECTED)
                .products(cartById.get().getProducts())
                .totalPrice(cartById.get().getTotalPrice())
                .build();

        orderRepository.save(order);
        return ResponseDto.<OrderDto>builder()
                .data(orderMapper.toDto(order))
                .message(OK)
                .code(OK_CODE)
                .build();

    }
}
