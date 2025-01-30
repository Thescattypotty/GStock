package org.gestionstock.stock.Util;

import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String subject , Collection<? extends GrantedAuthority> authorities){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
            .withSubject(subject)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
            .withClaim("authorities", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .sign(algorithm);
    }

    public String getSubjectFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
            .build().verify(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
