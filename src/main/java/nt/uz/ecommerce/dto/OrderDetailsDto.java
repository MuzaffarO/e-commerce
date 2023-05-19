package nt.uz.ecommerce.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
    private Integer id;
    private List<ProductDto> products;
}
