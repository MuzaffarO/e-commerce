package nt.uz.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static nt.uz.ecommerce.service.additional.AppStatusMessages.EMPTY_STRING;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    private String name;
    @Positive @NotNull
    private Integer price;
    @Positive
    private Integer amount;
    private String description;
    @NotEmpty
    private CategoryDto category;
    private BrandDto brand;
//    private String imageUrl;
    private Boolean isAvailable;
}
