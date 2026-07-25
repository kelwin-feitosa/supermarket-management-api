package com.exemplo.meu_primeiro_projeto.exception;

public class FornecedorEmUsoException extends RuntimeException {
    public FornecedorEmUsoException(String mensagem) {
        super(mensagem);
    }
}