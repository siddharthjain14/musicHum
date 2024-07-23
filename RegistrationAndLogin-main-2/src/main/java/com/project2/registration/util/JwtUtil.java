package com.project2.registration.util;

import com.project2.registration.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String SECRET_KEY = "secret";
    public String extractUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
        System.out.println(claims.get("email").toString());
        return claims.get("email").toString();
    }
    public String extractChannelId(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
        System.out.println(claims.get("channelId").toString());
        return claims.get("channelId").toString();

    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    public String generateToken(Users users) {
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1000000000))
                .claim("email", users.getEmail())
                .claim("userId", users.getUserId())
                .claim("profileImage", users.getProfileImage())
                .claim("username", users.getUsername())
                .claim("channelId", users.getChannelId())
                .compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
