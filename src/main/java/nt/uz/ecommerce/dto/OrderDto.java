package nt.uz.ecommerce.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Integer id;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private UsersDto user;
    private AddressDto address;
//    private OrderDetailsDto orderDetails;
    List<ProductDto> products;
}
