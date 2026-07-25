package com.exemplo.meu_primeiro_projeto.exception;

public class CompraNaoEncontradaException extends RuntimeException{
    public CompraNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
