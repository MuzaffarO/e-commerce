package nt.uz.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import nt.uz.ecommerce.dto.UsersDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Component
public class JwtService {

    @Value("${security.secret.key}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private String expirationTime;

    public String generateToken(String user, List<String> roles) {

        long expirationSeconds = Long.parseLong(expirationTime);

        Date expirationDate = new Date(Date.from(Instant.now()).getTime() + expirationSeconds * 1000);

        return Jwts.builder()
//                .setClaims(claims)
                .setSubject(user)
                .claim("role", roles)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public String extractUsername(String authToken) {
        return getClaimsFromToken(authToken)
                .getSubject();
    }

    public Claims getClaimsFromToken(String authToken) {
//        String key = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(authToken)
                .getBody();
    }

    public boolean isExpired(String token){
        return getClaimsFromToken(token).getExpiration().getTime() < System.currentTimeMillis();
    }

    public boolean validateToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .after(Date.from(Instant.now()));
    }


    public <T> T getClaim(String token, String claimName, Class<T> type){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(claimName, type);
    }
}
