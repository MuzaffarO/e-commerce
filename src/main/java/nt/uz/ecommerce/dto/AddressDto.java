package nt.uz.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nt.uz.ecommerce.service.additional.AppStatusMessages;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private Integer id;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    private String country;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    private String city;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    private String apartmentNumber;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    private String postalCode;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    private String phoneNumber;
}
