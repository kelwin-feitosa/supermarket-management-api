package com.exemplo.meu_primeiro_projeto.shared.exception;

/** Conflito de estado ou dados (ex.: duplicidade, categoria em uso). HTTP 409. */
public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) { super(message); }
}
