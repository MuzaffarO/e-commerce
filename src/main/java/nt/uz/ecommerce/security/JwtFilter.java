package nt.uz.ecommerce.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if(authorization!=null && authorization.startsWith("Bearer ")){
            if(jwtService.isExpire(authorization.substring(7))){
                response.setContentType("application/json");
                response.getWriter().println(
                        ResponseDto.builder()
                                .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                                .success(false)
                                .build()
                );
            }
            UsersDto subject = jwtService.subject(authorization.substring(7));
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(subject,null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response);
    }}
