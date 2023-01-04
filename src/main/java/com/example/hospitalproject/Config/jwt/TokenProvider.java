package com.example.hospitalproject.Config.jwt;

import com.example.hospitalproject.Dto.Token.RefreshTokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component // Bean으로 등록해주어야하기 때문에 @Component 어노테이션 선언
@Slf4j
public class TokenProvider implements InitializingBean {

    private static final String AUTHENTICATION_KEY = "auth";

    private String secret;
    private Key key;
    private Long tokenValidationTime;
    private Long refreshTokenValidationTime;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] secret_key = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(secret_key);
    }

    public TokenProvider(@Value("${jwt.tokenValidationTime}") Long tokenValidationTime,
                         @Value("${jwt.secret}") String secret) {
        this.secret = secret;
        this.tokenValidationTime = tokenValidationTime * 1000;
        this.refreshTokenValidationTime = tokenValidationTime * 2 * 1000;
    }

    public RefreshTokenDto createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream().map(s -> s.getAuthority()).collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        Date expirationTime = new Date(now + tokenValidationTime);
        Date refreshTokenExpirationTime = new Date(now + refreshTokenValidationTime);

        String originToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(expirationTime)
                .claim(AUTHENTICATION_KEY, authorities)
                .signWith(this.key, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .signWith(this.key, SignatureAlgorithm.HS512)
                .setExpiration(refreshTokenExpirationTime)
                .compact();

        return RefreshTokenDto.builder()
                .originToken(originToken)
                .refreshToken(refreshToken)
                .expirationTime(expirationTime.getTime())
                .grantedType("Bearer ")
                .build();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<SimpleGrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHENTICATION_KEY).toString().split(","))
                .map(s -> new SimpleGrantedAuthority(s))
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().build().parseClaimsJws(token);
            return true;
        } catch(SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT입니다.");
        } catch(ExpiredJwtException e) {
            log.info("이미 만료된 JWT입니다.");
        } catch(UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT입니다.");
        } catch(IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }

        return false;
    }
}
