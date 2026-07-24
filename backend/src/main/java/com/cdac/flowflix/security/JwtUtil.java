package com.cdac.flowflix.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cdac.flowflix.dto.JWTLogin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

    private final String SECRET = "FLOWFLIX_SECRET_KEY";

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);

    }

    public Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);

    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);

    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()

                .setSigningKey(SECRET)

                .parseClaimsJws(token)

                .getBody();

    }

    private boolean isExpired(String token) {

        return extractExpiration(token)

                .before(new Date());

    }

    public String generateToken(JWTLogin jwtLogin) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("role", jwtLogin.getRole());

        return Jwts.builder()

                .setClaims(claims)

                .setSubject(jwtLogin.getUsername())

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + 1000 * 60 * 60 * 10))

                .signWith(SignatureAlgorithm.HS256, SECRET)

                .compact();

    }

    public boolean validateToken(
            String token,
            UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isExpired(token);

    }

}