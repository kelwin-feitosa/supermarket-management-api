package com.exemplo.meu_primeiro_projeto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FornecedorRequest(

    @NotBlank(message = "O nome é obrigatório.")
    String nome,

    @NotBlank(message = "O CNPJ é obrigatório.")
    @Pattern(
        // D de dígitos inteiros, 14 de quantidade de digitos  ^ para começo e $ para fim
        regexp = "^\\d{14}$",  
        message = "O CNPJ deve conter exatamente 14 dígitos."
    )
    String cnpj,

    @NotBlank(message = "O telefone é obrigatório.")
    @Size(min = 10, max = 11)
    String telefone

) {}