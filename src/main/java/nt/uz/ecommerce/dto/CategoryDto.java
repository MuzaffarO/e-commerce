package nt.uz.ecommerce.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import static nt.uz.ecommerce.service.additional.AppStatusMessages.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    @Size(max = 100, min = 3, message = SIZE_MISMATCH)
    private String name;
    @Positive(message = NEGATIVE_VALUE)
    @Max(value = 500, message = SIZE_MISMATCH)
    private Integer parentId;
}
