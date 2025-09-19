package com.micro.piyush.movie.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.micro.piyush.movie.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    public static final String SECRET = "404D635166546THISSHOULDBE32CHARACTERLONG6C756E65416E6541646F7074";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractEmailId(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String emailId = extractEmailId(token);
        return (emailId.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(User user,String role) {
        Map<String, Object> claims = new HashMap<>();
        String userName = user.getName();
        claims.put("role", role);
        claims.put("userId", user.getId());
        claims.put("email", user.getEmailId());
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)  // 1. Add extra information (payload data)
                .setSubject(userName) // 2. Set the subject (who the token belongs to, usually the username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 3. When the token was created
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 4. Expiry time (30 minutes here)
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // 5. Sign the token with secret key + HS256 algorithm
                .compact(); // 6. Build and return the JWT as a String
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

