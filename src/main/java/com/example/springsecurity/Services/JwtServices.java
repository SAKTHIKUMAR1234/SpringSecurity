package com.example.springsecurity.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtServices {
    private static final String SECRET_KEY = "5f787bf595208ce61a0105d4e8762643adaaeda1fac8202a7f6b410492ac02a8";
    public String extractUserName(String token){
        return extractClaims(token,Claims::getSubject);
    }

    public String getJwtToken(UserDetails userDetails){
        return getJwtToken(new HashMap<>(),userDetails);
    }

    public String getJwtToken(
            Map<String,Object> claims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000*60*24)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaims(String token, Function<Claims,T> ClaimsResolver){
        final Claims claims = extractAllDetails(token);
        return  ClaimsResolver.apply(claims);
    }

    public Claims extractAllDetails(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && isNotExpired(token));
    }

    private boolean isNotExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }
}
