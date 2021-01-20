package io.ioak.emailflow.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
@Slf4j
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String extractUserId(String token) {
        Claims claims = null;
        try{
            claims = extractAllClaims(token);
        }catch(Exception e) {
            e.printStackTrace();
        }

        return claims.get("email").toString();
    }

    public User extractUser(String token) {
        Claims claims = extractAllClaims(token);

        User user = new User();
        user.setFirstName(claims.get("firstName").toString());
        user.setLastName(claims.get("lastName").toString());
        user.setEmail(claims.get("email").toString());
        return user;
    }

    public String extractUserWithSecurityKey(String token, String key) {
        Claims claims = extractAllClaimsWithKey(token, key);

        return claims.get("userId").toString();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaimsWithKey(String token, String key) {
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = Jwts.parser().setSigningKey(TextCodec.BASE64URL.encode(key)).parseClaimsJws(token);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return claimsJws.getBody();
    }

    private Claims extractAllClaims(String token) {
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return claimsJws.getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        return createToken(claims);
    }

    public String generateTokenWithUser(JwtResorce.UserResource user) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());

        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) {

        String token =  Jwts.builder().setClaims(claims).setSubject("emailservice User Authorization").setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();


        return token;
    }

}
