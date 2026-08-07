package com.cdac.flowflix.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.JWTLogin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

        @Value("${jwt.secret:FLOWFLIX_SECRET_KEY}")
        private String SECRET;

        private static final long JWT_VALIDITY = 1000 * 60 * 60 * 10; // 10 Hours

    // ============================
    // Generate Token
    // ============================

    public String generateToken(JWTLogin jwtLogin) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("role", "ROLE_" + jwtLogin.getRole());

        return createToken(claims, jwtLogin.getUsername());

    }

    // ============================
    // Create Token
    // ============================

    private String createToken(
            Map<String, Object> claims,
            String subject) {

        return Jwts.builder()

                .setClaims(claims)

                .setSubject(subject)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(System.currentTimeMillis() + JWT_VALIDITY))

                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET)

                .compact();

    }

    // ============================
    // Extract Username
    // ============================

    public String extractUsername(String token) {

        return extractClaim(
                token,
                Claims::getSubject);

    }

    // ============================
    // Extract Expiration
    // ============================

    public Date extractExpiration(String token) {

        return extractClaim(
                token,
                Claims::getExpiration);

    }

    // ============================
    // Extract Role
    // ============================

    public String extractRole(String token) {

        Claims claims = extractAllClaims(token);

        return (String) claims.get("role");

    }
    // ============================
    // Extract Claim
    // ============================

    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver) {

        Claims claims =
                extractAllClaims(token);

        return resolver.apply(claims);

    }

    // ============================
    // Extract Claims
    // ============================

    private Claims extractAllClaims(
            String token) {

        return Jwts.parser()

                .setSigningKey(SECRET)

                .parseClaimsJws(token)

                .getBody();

    }

    // ============================
    // Expired?
    // ============================

    private boolean isExpired(String token) {

        return extractExpiration(token)
                .before(new Date());

    }

    // ============================
    // Validate Token
    // ============================

    public boolean validateToken(
            String token,
            UserDetails userDetails) {

        String username =
                extractUsername(token);

        return username.equals(
                userDetails.getUsername())
                &&
                !isExpired(token);

    }

}