package com.example.pgk.security;



import com.example.pgk.exception.IncorrectJwtTokenException;
import com.example.pgk.exception.NotValidJwtTokenException;
import com.example.pgk.exception.NotValidRequestException;
import com.example.pgk.model.dto.RoleDTO;
import com.example.pgk.model.dto.UserDTO;
import com.google.gson.Gson;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
public class JwtUtils {
    private final SecretKey jwtAccessToken;
    private final SecretKey jwtRefreshToken;
    private final Gson gson = new Gson();

    public JwtUtils(@Value("${jwt.accessToken}")String jwtAccessToken, @Value("${jwt.refreshToken}") String jwtRefreshToken) {
        this.jwtAccessToken = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessToken));
        this.jwtRefreshToken = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshToken));
    }
    public String generateAccessToken(final UserDTO userDTO){
        final LocalDateTime time = LocalDateTime.now();
        final Instant accessInst = time.plusMinutes(100).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessTime = Date.from(accessInst);
        return Jwts.builder()
                .setSubject(userDTO.getEmail())
                .setExpiration(accessTime)
                .claim("id",gson.toJson(userDTO.getId()))
                .claim("roles", userDTO.getRoles())
                .claim("type","access")
                .signWith(jwtAccessToken)
                .compact();
    }
    public String generateRefreshToken(final UserDTO userDTO){
        final LocalDateTime time = LocalDateTime.now();
        final Instant refreshInst = time.plusDays(5).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshTime = Date.from(refreshInst);
        return Jwts.builder()
                .setSubject(userDTO.getEmail())
                .setExpiration(refreshTime)
                .claim("roles",gson.toJson(userDTO.getRoles()))
                .claim("type","refresh")
                .signWith(jwtRefreshToken)
                .compact();
    }
    public boolean validateAccessToken(final String accessToken){
        try {
            final Claims claims = getAccessClaims(accessToken);
            if(claims == null){
                return false;
            }
            if(!((String) claims.get("type")).equals("access")){
                return false;
            }
            return validateToken(accessToken,jwtAccessToken);
        } catch (NullPointerException n){
            System.out.println(n.getMessage());
            return false;
        }
    }
    public boolean validateRefreshToken(final String refreshToken){
        final Claims claims = getAccessClaims(refreshToken);
        if(!((String) claims.get("type")).equals("refresh")){
            throw new IncorrectJwtTokenException("Incorrect token type");
        }
        return validateToken(refreshToken,jwtRefreshToken);
    }
    private boolean validateToken(final String typeToken,final Key key){
        try {
            if(typeToken == null || typeToken.equals("")){
                System.out.println("falsee");
                return false;
            }
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(typeToken);
            return true;
        } catch (ExpiredJwtException expEx) {
            throw new NotValidJwtTokenException();
        } catch (UnsupportedJwtException unsEx) {
            System.out.println(unsEx.getMessage());
            throw new NotValidJwtTokenException();
        } catch (MalformedJwtException mjEx) {
            System.out.println(mjEx.getMessage());
            throw new NotValidJwtTokenException();
        } /*catch (SignatureException sEx) {
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
    }
    public Claims getAccessClaims(final String token) {
        return getClaims(token, jwtAccessToken);
    }

    public Claims getRefreshClaims(final String token) {
        return getClaims(token, jwtRefreshToken);
    }

    private Claims getClaims(final String token, final Key secret) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException m){
            System.out.println(m.getMessage());
            return null;
        } catch (ExpiredJwtException e){
            return null;
        }
    }
    public JwtAuth generate(final Claims claims){
        final JwtAuth jwtAuth = new JwtAuth();
        jwtAuth.setRoles(getRoles(claims));
        jwtAuth.setEmail(claims.getSubject());
        return jwtAuth;
    }
    private RoleDTO[] getRoles(final Claims claims){
        try {
            final RoleDTO[] roles = gson.fromJson(String.valueOf(claims.get("roles")), RoleDTO[].class);
            return roles;
        } catch (NullPointerException n){
            n.printStackTrace();
            throw new NotValidRequestException("Not valid request (null roles)");
        }
    }
    public UserDTO parseRefreshToken(final String refresh){
        final Claims claims = getRefreshClaims(refresh);
        return new UserDTO(claims.getSubject(),getRoles(claims));
    }

}
