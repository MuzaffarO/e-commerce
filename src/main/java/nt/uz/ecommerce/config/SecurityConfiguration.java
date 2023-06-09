package nt.uz.ecommerce.config;

import com.google.gson.Gson;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.security.AuthenticationManager;
import nt.uz.ecommerce.security.JwtFilter;
import nt.uz.ecommerce.service.additional.AppStatusCodes;
import nt.uz.ecommerce.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    @Lazy
    UsersServiceImpl usersService;
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    Gson gson;
    @Autowired
    AuthenticationManager authenticationManager;

    private AuthenticationEntryPoint entryPoint(){
        return ((request, response, exception) -> {
            response.getWriter().println(gson.toJson(ResponseDto.builder()
                    .message("Token is not valid "+exception.getMessage()+
                            (exception.getCause()!=null?exception.getCause().getMessage():""))
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .build()));
            response.setContentType("application/json");
        });
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource());
        return http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/users/get-token/**", "/users/sign-up/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling(e -> e.authenticationEntryPoint(entryPoint()))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedHeaders(List.of("Authentication","SECRET-HEADER","Content-Type"));
        cors.addAllowedMethod("*");
        cors.addAllowedOrigin("null");

        UrlBasedCorsConfigurationSource url = new UrlBasedCorsConfigurationSource();
        url.registerCorsConfiguration("/**",cors);

        return url;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
