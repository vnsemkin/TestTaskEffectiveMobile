package org.vnsemkin.taskmanagementsystem.configuration.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        Date issueDate = new Date();
        Date expiredDate = new Date(issueDate.getTime() + jwtLifetime.toMillis());

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(issueDate)
                .expiration(expiredDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }


    public Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String getUsername(String token) {
        String username;
        if (Objects.nonNull(token) && token.startsWith("Bearer ")) {
            String prettyToken = token.substring(7);
            username = getAllClaims(prettyToken).getSubject();
        } else {
            username = getAllClaims(token).getSubject();
        }
        return username;
    }

    public List<String> getRoles(String token) {
        Object rolesObject = getAllClaims(token).get("roles");
        if (rolesObject instanceof List<?> rolesList) {
            // Check if all elements in the list are of type String
            if (rolesList.stream().allMatch(element -> element instanceof String)) {
                return rolesList.stream()
                        .map(Object::toString) // Map each element to String
                        .collect(Collectors.toList());
            } else {
                throw new IllegalStateException("Roles list contains non-String elements");
            }
        } else {
            throw new IllegalStateException("Roles claim is not a List");
        }
    }
}
