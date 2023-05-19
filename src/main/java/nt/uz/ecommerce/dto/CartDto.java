package nt.uz.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Integer id;
    private Integer totalPrice;
    private UsersDto user;
    private List<ProductDto> products;
}
