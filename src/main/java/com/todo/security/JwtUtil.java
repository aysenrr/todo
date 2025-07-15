package com.todo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component //Spring, bu sınıfı otomatik olarak bean olarak tanır ve gerektiğinde bağımlılık olarak enjekte edebilir.
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;

    private static final int MINUTES = 60;

    public String generateToken(String username){
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(MINUTES, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parserBuilder().setSigningKey(SECRET).build().
                parseClaimsJws(token).getBody().getSubject();//getBody ->claims, getSubject -> username
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
