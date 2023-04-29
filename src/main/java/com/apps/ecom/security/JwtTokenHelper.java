package com.apps.ecom.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtTokenHelper {

    public  static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // token valid or expired

    private static final String secret = "244226452948404D6351655468576D5A7134743777217A25432A462D4A614E64";

    // retrieve username from jwt token
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    // retrieve expiration data from jwt token
    public Date getExpirationDataFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDataFromToken(token);
        return expiration.before(new Date());
    }

    // generate token for user
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token:
    //1. Define claims of the token, like Issuer, Expiration, subject, and the ID
    //2. Sign the JWT using the HSS12 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-)
    //4. Compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//        Instant now = Instant.now();
//        Instant expirationTime = now.plus(JWT_TOKEN_VALIDITY, ChronoUnit.SECONDS);
//
//        String token = Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(Date.from(now))
//                .setExpiration(Date.from(expirationTime))
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact();
//
//        return "Bearer " + token;
//    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // validate token
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
