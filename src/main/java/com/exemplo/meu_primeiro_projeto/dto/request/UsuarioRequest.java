package com.exemplo.meu_primeiro_projeto.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados necessários para cadastrar ou atualizar um usuário.")
public record UsuarioRequest(

    @Schema(
        description = "Nome completo do usuário.",
        example = "João da Silva"
    )
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    String nome,


    @Schema(
        description = "Endereço de e-mail do usuário.",
        example = "joao@email.com"
    )
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    String email,


    @Schema(
        description = "Número de telefone do usuário.",
        example = "61999999999"
    )
    @NotBlank(message = "O telefone é obrigatório.")
    @Size(min = 10, max = 11, message = "Telefone inválido.")
    String telefone,

    @Schema(
        description = "Senha de acesso do usuário.",
        example = "Senha@123"
    )
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres.")
    String senha

) {
}