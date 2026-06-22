package com.study.jwt_imp.filter;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.study.jwt_imp.service.JwtService;
import com.study.jwt_imp.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        log.info("Header {}", header);
         if(header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
         }
        String token = header.substring(7);
        log.info("token {}", token);
        String username = jwtService.extractUser(token);

        UserDetails userFromDb = userService.loadUserByUsername(username);
        
        if(jwtService.isTokenValid(token, userFromDb)){
            log.info("Token is valid");
            UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userFromDb,null,jwtService.extractRoles(token).stream().map(SimpleGrantedAuthority::new ).collect(Collectors.toList()));

             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } else {
            log.info("Token is invalid");
        }

        filterChain.doFilter(request, response);

    }

}
