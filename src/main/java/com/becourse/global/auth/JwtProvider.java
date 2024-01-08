package com.becourse.global.auth;

import com.becourse.domain.user.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {
    private final Key secretKey;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public JwtProvider(@Value("${jwt.token.secret}") String secret, UserDetailsServiceImpl userDetailsServiceImpl) {
        byte[] secretByteKey = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(secretByteKey);
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    public TokenInfo generateToken(UserDetails user) {
        Long now = new Date().getTime();
        Long day = 1000L * 60 * 60 * 24;
        Long week = 1000L * 60 * 60 * 24 * 7;

        String authority = user.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.joining(""));

        String accessToken = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("auth", authority)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + day))
                .signWith(secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("auth", authority)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + week))
                .signWith(secretKey)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = this.parseToken(token);

        if(claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다");
        }
        UserDetails principal = userDetailsServiceImpl.loadUserByUsername(parseToken(token).getSubject());
        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        } else {
            return null;
        }
    }

    public boolean validateToken(String token) {
        return parseClaims(token) != null;
    }

    private Claims parseToken(String token) {
        try {
            return parseClaims(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private Jws<Claims> parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (Exception e) {
            return null;
        }
    }
}
