package nt.uz.ecommerce.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import static nt.uz.ecommerce.service.additional.AppStatusMessages.*;

public class ReviewDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    @Size(min = 1, message = SIZE_MISMATCH)
    private String description;
    @Positive(message = NEGATIVE_VALUE)
    @Max(value = 5, message = SIZE_MISMATCH)
    private Integer rank;

}
