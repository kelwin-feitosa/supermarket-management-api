package com.exemplo.meu_primeiro_projeto.exception;

public class UsuarioNaoEncontradoException extends RuntimeException{
    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
