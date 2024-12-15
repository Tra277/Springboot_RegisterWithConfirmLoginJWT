package com.tsubakistudio.codeAmigos_Security.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY="eyJhbGciOiJIUzI1NiJ9eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTczNDAxMTg3NCwiaWF0IjoxNzM0MDExODc0fQwta5ORnICvV1CFRMBqHBwjxDlWSaBP28qsBmmJCfY";

    //Subject is usually username...
    public String extractUsername(String jwt) {
        return extractClaim(jwt,Claims::getSubject);
    }
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    //Using in extractAllClaims(String jwt) method
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    //NOTE: private accesser
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }






    //generate token with extra claims
    public String generateToken(Map<String,Object> extractClaims, UserDetails userDetails){
        return Jwts.builder().claims(extractClaims)
                .subject(userDetails.getUsername())
                //issuedAt ~= created at
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 24) )
                .signWith(getSignInKey(),Jwts.SIG.HS256)
                //generate and return token
                .compact();
    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()))  && !isTokenExpired(token);
    }

    public boolean isTokenValid(String token) {
        final String username = extractUsername(token);
        return (username != null) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //obtain a new access token
    private String generateRefreshToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 24 * 7) )
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();

    }
}
