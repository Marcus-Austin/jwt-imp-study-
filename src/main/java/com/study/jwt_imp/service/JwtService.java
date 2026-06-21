package com.study.jwt_imp.service;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.study.jwt_imp.security.AuthenticatedUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String generateToken(AuthenticatedUser principal){
        log.info("Secret Key: {}", secretKey);
        return Jwts.builder()
        .subject(principal.getUsername())
        .claim("roles",principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
        .signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey)))
        .compact();
    }

}
