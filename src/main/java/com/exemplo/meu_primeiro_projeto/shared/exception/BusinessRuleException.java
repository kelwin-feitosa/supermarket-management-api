package com.exemplo.meu_primeiro_projeto.shared.exception;

/** Regra de negócio impede a operação. Resposta HTTP 400. */
public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) { super(message); }
}
