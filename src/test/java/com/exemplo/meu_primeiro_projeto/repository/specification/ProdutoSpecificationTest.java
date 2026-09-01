package com.exemplo.meu_primeiro_projeto.repository.specification;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import com.exemplo.meu_primeiro_projeto.dto.filter.ProdutoFiltro;
import com.exemplo.meu_primeiro_projeto.model.Categoria;
import com.exemplo.meu_primeiro_projeto.model.Produto;
import com.exemplo.meu_primeiro_projeto.repository.CategoriaRepository;
import com.exemplo.meu_primeiro_projeto.repository.ProdutoRepository;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProdutoSpecificationTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Categoria categoria;

    @BeforeEach
    void preparar() {
        categoria = new Categoria(
            "Bebidas",
            "Produtos líquidos"
        );

        categoriaRepository.save(categoria);

        Produto coca = new Produto(
            "Coca-Cola",
            new BigDecimal("5.99"),
            "Refrigerante lata",
            10,
            categoria
        );

        Produto fanta = new Produto(
            "Fanta",
            new BigDecimal("4.99"),
            "Refrigerante garrafa",
            8,
            categoria
        );

        produtoRepository.saveAll(List.of(coca, fanta));
    }

    @Test
    void nomeContem_deveEncontrarProduto() {
        List<Produto> produtos = produtoRepository.findAll(ProdutoSpecification.nomeContem("Coca"));

        assertEquals(1, produtos.size());
        assertEquals("Coca-Cola", produtos.get(0).getNome());
    }

    @Test
    void categoriaIgual_deveEncontrarProdutosDaCategoria() {
        Categoria alimentos = new Categoria(
            "Alimentos",
            "Produtos alimentícios"
        );

        Produto arroz = new Produto(
            "Arroz",
            new BigDecimal("8.99"),
            "Arroz branco",
            20,
            alimentos
        );

        categoriaRepository.save(alimentos);
        produtoRepository.save(arroz);

        List<Produto> produtos = produtoRepository.findAll(
            ProdutoSpecification.categoriaIgual(alimentos.getId())
        );
        
        assertEquals(1, produtos.size());
        assertEquals("Arroz", produtos.get(0).getNome());
    }

    @Test
    void precoMaiorOuIgual_deveEncontrarProdutos() {
        List<Produto> produtos = produtoRepository.findAll(
            ProdutoSpecification.precoMaiorOuIgual(new BigDecimal("5.00"))
        );

        assertEquals(1, produtos.size());
        assertEquals("Coca-Cola", produtos.get(0).getNome());
    }

    @Test
    void precoMenorOuIgual_deveEncontrarProdutos() {
        List<Produto> produtos = produtoRepository.findAll(
            ProdutoSpecification.precoMenorOuIgual(new BigDecimal("5.00"))
        );

        assertEquals(1, produtos.size());
        assertEquals("Fanta", produtos.get(0).getNome());
    }

    @Test
    void estoqueMaiorOuIgual_deveEncontrarProdutos() {
        List<Produto> produtos = produtoRepository.findAll(
            ProdutoSpecification.estoqueMaiorOuIgual(10)
        );

        assertEquals(1, produtos.size());
        assertEquals("Coca-Cola", produtos.get(0).getNome());
    }

    @Test
    void estoqueMenorOuIgual_deveEncontrarProdutos() {
        List<Produto> produtos = produtoRepository.findAll(
            ProdutoSpecification.estoqueMenorOuIgual(8)
        );

        assertEquals(1, produtos.size());
        assertEquals("Fanta", produtos.get(0).getNome());
    }

    @Test
    void comFiltro_deveAplicarFiltros() {
        ProdutoFiltro filtro = new ProdutoFiltro(
            "Coca",
            null,
            new BigDecimal("5.00"),
            null,
            10,
            null
        );

        List<Produto> produtos = produtoRepository.findAll(
            ProdutoSpecification.comFiltro(filtro)
        );

        assertEquals(1, produtos.size());
        assertEquals("Coca-Cola", produtos.get(0).getNome());
    }
}
