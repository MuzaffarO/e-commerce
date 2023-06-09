package nt.uz.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTokenDto {
    private String email;
    private String password;
}
