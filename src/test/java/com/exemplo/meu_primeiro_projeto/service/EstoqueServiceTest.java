package com.exemplo.meu_primeiro_projeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.exemplo.meu_primeiro_projeto.exception.EstoqueInsuficienteException;
import com.exemplo.meu_primeiro_projeto.model.Produto;

public class EstoqueServiceTest {

    private final EstoqueService service = new EstoqueService();

    @Test
    void verificarEstoque_devePermitirQuandoQuantidadeDisponivel() {
        Produto produto = criarProduto(10);

        service.verificarEstoque(produto, 5);

        assertEquals(10, produto.getQuantidadeEstoque());
    }

    @Test
    void verificarEstoque_deveLancarExcecaoQuandoEstoqueInsuficiente() {
        Produto produto = criarProduto(10);

        assertThrows(
            EstoqueInsuficienteException.class,
            () -> service.verificarEstoque(produto, 11)
        );
    }

    @Test
    void baixarEstoque_deveDiminuirQuantidadeCorretamente() {
        Produto produto = criarProduto(10);

        service.baixarEstoque(produto, 4);

        assertEquals(6, produto.getQuantidadeEstoque());
    }

    @Test
    void baixarEstoque_naoDeveAlterarQuandoEstoqueInsuficiente() {
        Produto produto = criarProduto(10);

        assertThrows(
            EstoqueInsuficienteException.class,
            () -> service.baixarEstoque(produto, 11)
        );

        assertEquals(10, produto.getQuantidadeEstoque());
    }

    @Test
    void aumentarEstoque_deveAumentarQuantidadeCorretamente() {
        Produto produto = criarProduto(10);

        service.aumentarEstoque(produto, 5);

        assertEquals(15, produto.getQuantidadeEstoque());
    }

    private Produto criarProduto(int quantidadeEstoque) {
        return new Produto(
            "Produto Teste",
            new BigDecimal("10.00"),
            "Descrição teste",
            quantidadeEstoque,
            null
        );
    }
}