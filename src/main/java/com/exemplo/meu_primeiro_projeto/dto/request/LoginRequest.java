package com.exemplo.meu_primeiro_projeto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciais necessárias para realizar a autenticação.")
public record LoginRequest(

    @Schema(
        description = "E-mail cadastrado do usuário.",
        example = "usuario@email.com"
    )
    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O email deve ser válido.")
    String email,

    @Schema(
        description = "Senha cadastrada do usuário.",
        example = "senha123",
        format = "password"
    )
    @NotBlank(message = "A senha é obrigatória.")
    String senha

) {
}