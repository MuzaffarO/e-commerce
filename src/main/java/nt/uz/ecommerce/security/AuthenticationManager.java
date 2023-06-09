package nt.uz.ecommerce.security;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import nt.uz.ecommerce.model.Users;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements org.springframework.security.authentication.AuthenticationManager {

    private final JwtService jwtService;
    private final Gson gson;

    public Authentication authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        String username;
        try {
            username = jwtService.extractUsername(token);
        } catch (Exception e) {
            username = null;
            System.out.println(e.getMessage());
        }

        if (username != null && jwtService.validateToken(token)){
            List<String> role = jwtService.getClaim(token, "role", List.class);
            List<SimpleGrantedAuthority> roles = role.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
//            User user = gson.fromJson("{'email':'" + jwtUtil.getClaim(token, "sub", String.class) + "'}", User.class);
            Users user = gson.fromJson(jwtService.getClaim(token, "sub", String.class), Users.class);

            UsernamePasswordAuthenticationToken authenticatedUser =
                    new UsernamePasswordAuthenticationToken(user, null, roles);
            return authenticatedUser;
        }else {
            return null;
        }
    }
}
