package nt.uz.ecommerce.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class LoginDto {
    private String username;
    private String password;

}
