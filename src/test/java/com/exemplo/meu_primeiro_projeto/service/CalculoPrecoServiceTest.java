package com.exemplo.meu_primeiro_projeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.exemplo.meu_primeiro_projeto.model.ItemCarrinho;
import com.exemplo.meu_primeiro_projeto.model.ItemCompra;
import com.exemplo.meu_primeiro_projeto.model.ItemVenda;

public class CalculoPrecoServiceTest {

    private final CalculoPrecoService service = new CalculoPrecoService();

    @Test
    void calcularValorTotal_deveSomarSubtotaisDoCarrinho() {
        ItemCarrinho item1 = new ItemCarrinho(
            null,
            2,
            new BigDecimal("10.00")
        );

        ItemCarrinho item2 = new ItemCarrinho(
            null,
            3,
            new BigDecimal("5.00")
        );

        BigDecimal resultado = service.calcularValorTotal(
            List.of(item1, item2)
        );

        assertEquals(
            new BigDecimal("35.00"),
            resultado
        );
    }

    @Test
    void calcularValorTotal_deveRetornarZeroQuandoCarrinhoVazio() {
        BigDecimal resultado = service.calcularValorTotal(List.of());

        assertEquals(BigDecimal.ZERO, resultado);
    }

    @Test
    void calcularValorTotalVenda_deveSomarSubtotaisDaVenda() {
        ItemVenda item1 = new ItemVenda(
            null,
            2,
            new BigDecimal("50.00")
        );

        ItemVenda item2 = new ItemVenda(
            null,
            3,
            new BigDecimal("25.00")
        );

        BigDecimal resultado = service.calcularValorTotalVenda(
            List.of(item1, item2)
        );

        assertEquals(
            new BigDecimal("175.00"),
            resultado
        );
    }

    @Test
    void calcularValorTotalVenda_deveRetornarZeroQuandoListaVazia() {
        BigDecimal resultado = service.calcularValorTotalVenda(List.of());

        assertEquals(BigDecimal.ZERO, resultado);
    }

    @Test
    void calcularValorTotalCompra_deveSomarSubtotaisDaCompra() {
        ItemCompra item1 = new ItemCompra(
            null,
            2,
            new BigDecimal("100.00")
        );

        ItemCompra item2 = new ItemCompra(
            null,
            3,
            new BigDecimal("50.00")
        );

        BigDecimal resultado = service.calcularValorTotalCompra(
            List.of(item1, item2)
        );

        assertEquals(
            new BigDecimal("350.00"),
            resultado
        );
    }

    @Test
    void calcularValorTotalCompra_deveRetornarZeroQuandoListaVazia() {
        BigDecimal resultado = service.calcularValorTotalCompra(List.of());

        assertEquals(BigDecimal.ZERO, resultado);
    }
}