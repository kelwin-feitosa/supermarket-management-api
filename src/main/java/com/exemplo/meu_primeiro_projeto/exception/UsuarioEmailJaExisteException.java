package com.exemplo.meu_primeiro_projeto.exception;

public class UsuarioEmailJaExisteException extends RuntimeException{
    public UsuarioEmailJaExisteException(String mensagem) {
        super(mensagem);
    }
}

