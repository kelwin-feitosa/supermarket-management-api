package com.exemplo.meu_primeiro_projeto.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<RespostaErro> tratarProdutoNaoEncontrado(ProdutoNaoEncontradoException ex) {
        return resposta(HttpStatus.NOT_FOUND, "Produto não encontrado.", ex.getMessage());
    }

    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ResponseEntity<RespostaErro> tratarCategoriaNaoEncontrada(CategoriaNaoEncontradaException ex) {
        return resposta(HttpStatus.NOT_FOUND, "Categoria não encontrada.", ex.getMessage());
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<RespostaErro> tratarClienteNaoEncontrado(ClienteNaoEncontradoException ex) {
        return resposta(HttpStatus.NOT_FOUND, "Cliente não encontrado.", ex.getMessage());
    }

    @ExceptionHandler(FornecedorNaoEncontradoException.class)
    public ResponseEntity<RespostaErro> tratarFornecedorNaoEncontrado(FornecedorNaoEncontradoException ex) {
        return resposta(HttpStatus.NOT_FOUND, "Fornecedor não encontrado.", ex.getMessage());
    }

    @ExceptionHandler(CarrinhoNaoEncontradoException.class)
    public ResponseEntity<RespostaErro> tratarCarrinhoNaoEncontrado(CarrinhoNaoEncontradoException ex) {
        return resposta(HttpStatus.NOT_FOUND, "Carrinho não encontrado.", ex.getMessage());
    }

    @ExceptionHandler(ItemCarrinhoNaoEncontradoException.class)
    public ResponseEntity<RespostaErro> tratarItemCarrinhoNaoEncontrado(ItemCarrinhoNaoEncontradoException ex) {
        return resposta(HttpStatus.NOT_FOUND, "Item não encontrado no carrinho.", ex.getMessage());
    }

    @ExceptionHandler(VendaNaoEncontradaException.class)
    public ResponseEntity<RespostaErro> tratarVendaNaoEncontrada(VendaNaoEncontradaException ex) {
        return resposta(HttpStatus.NOT_FOUND, "Venda não encontrada.", ex.getMessage());
    }

    @ExceptionHandler(CompraNaoEncontradaException.class)
    public ResponseEntity<RespostaErro> tratarCompraNaoEncontrada(CompraNaoEncontradaException ex) {
        return resposta(HttpStatus.NOT_FOUND, "Compra não encontrada.", ex.getMessage());
    }

    @ExceptionHandler(ProdutoJaExisteException.class)
    public ResponseEntity<RespostaErro> tratarProdutoJaExistente(ProdutoJaExisteException ex) {
        return resposta(HttpStatus.CONFLICT, "Já existe um produto com esse nome.", ex.getMessage());
    }

    @ExceptionHandler(CategoriaJaExisteException.class)
    public ResponseEntity<RespostaErro> tratarCategoriaJaExistente(CategoriaJaExisteException ex) {
        return resposta(HttpStatus.CONFLICT, "Já existe uma categoria com esse nome.", ex.getMessage());
    }

    @ExceptionHandler(ClienteEmailJaExisteException.class)
    public ResponseEntity<RespostaErro> tratarClienteEmailJaExistente(ClienteEmailJaExisteException ex) {
        return resposta(HttpStatus.CONFLICT, "Esse email já está cadastrado.", ex.getMessage());
    }

    @ExceptionHandler(CnpjJaCadastradoException.class)
    public ResponseEntity<RespostaErro> tratarCnpjJaCadastrado(CnpjJaCadastradoException ex) {
        return resposta(HttpStatus.CONFLICT, "Esse cnpj já está cadastrado.", ex.getMessage());
    }

    @ExceptionHandler(CarrinhoVazioException.class)
    public ResponseEntity<RespostaErro> tratarCarrinhoVazio(CarrinhoVazioException ex) {
        return resposta(HttpStatus.BAD_REQUEST, "Carrinho vazio.", ex.getMessage());
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<RespostaErro> tratarEstoqueInsuficiente(EstoqueInsuficienteException ex) {
        return resposta(HttpStatus.BAD_REQUEST, "Estoque insuficiente.", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaErro> tratarErroValidacao(MethodArgumentNotValidException ex) {
        var campoErro = ex.getBindingResult().getFieldError();

        String mensagem = campoErro != null
                ? campoErro.getDefaultMessage()
                : "Dados inválidos.";

        return resposta(
                HttpStatus.BAD_REQUEST,
                "Dados enviados não passam nas regras de validação.",
                mensagem
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RespostaErro> tratarJsonErro(HttpMessageNotReadableException ex) {
        return resposta(
                HttpStatus.BAD_REQUEST,
                "Corpo da requisição inválido (JSON malformado).",
                "Certifique-se de que o JSON enviado está com a sintaxe correta."
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespostaErro> tratarErroGenerico(Exception ex) {
        return resposta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno no servidor.",
                "Ocorreu uma falha inesperada no backend."
        );
    }

    private ResponseEntity<RespostaErro> resposta(HttpStatus status, String titulo, String detalhes) {
        return ResponseEntity.status(status)
            .body(new RespostaErro (
                titulo,
                detalhes,
                LocalDateTime.now()
            ));
    }
}