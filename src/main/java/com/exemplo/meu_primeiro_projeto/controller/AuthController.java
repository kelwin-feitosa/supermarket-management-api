package com.exemplo.meu_primeiro_projeto.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.meu_primeiro_projeto.dto.request.LoginRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.LoginResponse;
import com.exemplo.meu_primeiro_projeto.exception.RespostaErro;
import com.exemplo.meu_primeiro_projeto.security.service.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
    name = "Autenticação",
    description = "Operações relacionadas à autenticação de usuários."
)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Operation(
        summary = "Realizar login",
        description = "Autentica um usuário e retorna um token JWT."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Login realizado com sucesso"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Dados enviados inválidos",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RespostaErro.class)
        )
    )
    @ApiResponse(
        responseCode = "401",
        description = "Email ou senha inválidos",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RespostaErro.class)
        )
    )
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(),
                request.senha()
            )
        );

        String token = jwtService.gerarToken(request.email());

        return new LoginResponse(token);
    }
}