package nt.uz.ecommerce.rest;

import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.dto.AddressDto;
import nt.uz.ecommerce.dto.OrderDto;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderResources {
    private final OrderService orderService;

    @PostMapping("/{cartId}")
    ResponseDto<OrderDto> createOrder(@PathVariable Integer cartId, @RequestBody AddressDto addressDto){
        return orderService.createOrder(cartId, addressDto);
    }
}
