package nt.uz.ecommerce.security;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import nt.uz.ecommerce.dto.ResponseDto;
import nt.uz.ecommerce.dto.UsersDto;
import nt.uz.ecommerce.service.additional.AppStatusCodes;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final Gson gson;
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if(authorization!=null && authorization.startsWith("Bearer ")){
            if(jwtService.isExpired(authorization.substring(7))){
                response.setContentType("application/json");
                response.getWriter().println(
                        ResponseDto.builder()
                                .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                                .success(false)
                                .build()
                );
            }
//            String subject = jwtService.getClaimsFromToken(authorization.substring(7)).getSubject();
            String authToken = authorization.substring(7);
//            gson.fromJson(subject, UsersDto.class);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(authToken, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request,response);
    }}
