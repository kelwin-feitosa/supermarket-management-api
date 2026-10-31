package com.exemplo.meu_primeiro_projeto.shared.exception;

import java.time.OffsetDateTime;

public record RespostaErro(
    String mensagem,
    String detalhes,
    OffsetDateTime timestamp
) { }
