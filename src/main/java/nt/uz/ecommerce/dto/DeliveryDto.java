package nt.uz.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nt.uz.ecommerce.model.Address;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {
    private Integer id;
    private UsersDto userDto;
    private Address address;
//    private OrderDto orderDto;
    private Boolean isActive;
}
