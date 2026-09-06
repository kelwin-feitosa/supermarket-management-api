package com.exemplo.meu_primeiro_projeto.security.service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.config.JwtProperties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service 
public class JwtService {

    private final SecretKey secretKey;

    public JwtService(JwtProperties properties) {
        this.secretKey = Keys.hmacShaKeyFor(
            properties.secret().getBytes(StandardCharsets.UTF_8)
        );
    }

    private final Duration expiration = Duration.ofHours(1);

    public String gerarToken(String email) {

        Instant agora = Instant.now();
        Instant expiracao = agora.plus(expiration);

        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(agora))
                .expiration(Date.from(expiracao))
                .signWith(secretKey)
                .compact();
    }

    public String extrairEmail(String token) {
        return Jwts.parser()
                .verifyWith(secretKey) //verificar assinatura
                .build()
                .parseSignedClaims(token) 
                .getPayload()  //conteudo do token
                .getSubject(); //pega o email
    }

    public boolean validarToken(String token, UserDetails userDetails) {
        String email = extrairEmail(token);

        return email.equals(userDetails.getUsername()) && !tokenExpirado(token);
    }

    public boolean tokenExpirado(String token) {
        Date expiracao = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expiracao.before(new Date());
    }
}
