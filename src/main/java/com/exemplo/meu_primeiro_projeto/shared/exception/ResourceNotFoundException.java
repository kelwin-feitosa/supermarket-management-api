package com.exemplo.meu_primeiro_projeto.shared.exception;

/** Recurso solicitado não existe. Resposta HTTP 404. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { super(message); }
}
