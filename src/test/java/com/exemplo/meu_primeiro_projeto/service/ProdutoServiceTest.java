package com.exemplo.meu_primeiro_projeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exemplo.meu_primeiro_projeto.dto.ProdutoRequest;
import com.exemplo.meu_primeiro_projeto.dto.ProdutoResponse;
import com.exemplo.meu_primeiro_projeto.exception.CategoriaNaoEncontradaException;
import com.exemplo.meu_primeiro_projeto.exception.ProdutoJaExisteException;
import com.exemplo.meu_primeiro_projeto.exception.ProdutoNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.model.Categoria;
import com.exemplo.meu_primeiro_projeto.model.Produto;
import com.exemplo.meu_primeiro_projeto.repository.CategoriaRepository;
import com.exemplo.meu_primeiro_projeto.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {
    
    @Mock
    CategoriaRepository categoriaRepository;

    @Mock
    ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService service;

    @Test
    void criarProduto_deveCriarComSucesso() {
        ProdutoRequest request = criarRequestPadrao();
        Produto produto = criarProdutoPadrao();
        Categoria categoria = criarCategoriaPadrao();

        when(produtoRepository.existsByNome(request.nome()))
            .thenReturn(false);
        
        when(categoriaRepository.findById(request.categoriaId()))
            .thenReturn(Optional.of(categoria));
        
        when(produtoRepository.save(any(Produto.class)))
            .thenReturn(produto);


        ProdutoResponse resposta = service.criarProduto(request);

        
        assertEquals(request.nome(), resposta.nome());
        assertEquals(request.preco(), resposta.preco());
        assertEquals(request.descricao(), resposta.descricao());
        assertEquals(request.quantidadeEstoque(), resposta.quantidadeEstoque());
        assertEquals(request.categoriaId(), resposta.categoriaId());

        verify(produtoRepository).existsByNome(request.nome());
        verify(categoriaRepository).findById(request.categoriaId());
        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    void criarProduto_deveLancarExcecaoQuandoNomeJaExiste() {
        ProdutoRequest request = criarRequestPadrao();

        when(produtoRepository.existsByNome(request.nome()))
            .thenReturn(true);

        assertThrows(
            ProdutoJaExisteException.class,
            () -> service.criarProduto(request)
        );

        verify(produtoRepository).existsByNome(request.nome());
        verify(categoriaRepository, never()).findById(request.categoriaId());
        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void criarProduto_deveLancarExcecaoQuandoCategoriaNaoExistir() {
        ProdutoRequest request = criarRequestPadrao();

        when(produtoRepository.existsByNome(request.nome()))
            .thenReturn(false);
        
        when(categoriaRepository.findById(request.categoriaId()))
            .thenReturn(Optional.empty());

        assertThrows(
            CategoriaNaoEncontradaException.class,
            () -> service.criarProduto(request)
        );

        verify(produtoRepository).existsByNome(request.nome());
        verify(categoriaRepository).findById(request.categoriaId());
        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void listarProdutos_deveRetornarLista() {
        Categoria categoria = criarCategoriaPadrao();

        Produto produto1 = criarProdutoPadrao();
        Produto produto2 = new Produto(
            "Fanta",
            new BigDecimal("4.99"),
            "Refrigerante Garrafa",
            8,
            categoria
        );
        produto2.setId(2L);


        when(produtoRepository.findAll())
            .thenReturn(List.of(produto1, produto2));

        
        List<ProdutoResponse> resposta = service.listarProdutos();

        assertEquals(2, resposta.size());

        assertEquals(produto1.getNome(), resposta.get(0).nome());
        assertEquals(produto1.getPreco(), resposta.get(0).preco());
        assertEquals(produto1.getDescricao(), resposta.get(0).descricao());
        assertEquals(produto1.getQuantidadeEstoque(), resposta.get(0).quantidadeEstoque());
        assertEquals(produto1.getCategoria().getId(), resposta.get(0).categoriaId());

        assertEquals(produto2.getNome(), resposta.get(1).nome());
        assertEquals(produto2.getPreco(), resposta.get(1).preco());
        assertEquals(produto2.getDescricao(), resposta.get(1).descricao());
        assertEquals(produto2.getQuantidadeEstoque(), resposta.get(1).quantidadeEstoque());
        assertEquals(produto2.getCategoria().getId(), resposta.get(1).categoriaId());

        verify(produtoRepository).findAll();
    }

    @Test
    void buscarPorId_deveRetornarProdutoQuandoExistir() {
        Produto produto = criarProdutoPadrao();

        when(produtoRepository.findById(produto.getId()))
            .thenReturn(Optional.of(produto));

        ProdutoResponse resposta = service.buscarPorId(produto.getId());

        assertEquals(produto.getId(), resposta.id());
        assertEquals(produto.getNome(), resposta.nome());
        assertEquals(produto.getPreco(), resposta.preco());
        assertEquals(produto.getDescricao(), resposta.descricao());
        assertEquals(produto.getQuantidadeEstoque(), resposta.quantidadeEstoque());
        assertEquals(produto.getCategoria().getId(), resposta.categoriaId());

        verify(produtoRepository).findById(produto.getId());
    }

    @Test
    void buscarPorId_deveLancarExcecaoQuandoProdutoNaoExistir() {
        Long id = 1L;

        when(produtoRepository.findById(id))
            .thenReturn(Optional.empty());


        assertThrows(
            ProdutoNaoEncontradoException.class,
            () -> service.buscarPorId(id)
        );

        verify(produtoRepository).findById(id);
    }

    @Test
    void atualizarProduto_deveAtualizarComSucesso() {
        Categoria categoria = criarCategoriaPadrao();
        ProdutoRequest request = new ProdutoRequest(
            "Coca-Cola Zero",
            new BigDecimal("6.99"),
            "Refrigerante sem açúcar",
            20,
            1L
        );

        Produto produto = criarProdutoPadrao();

        when(produtoRepository.findById(produto.getId()))
            .thenReturn(Optional.of(produto));
        
        when(produtoRepository.existsByNome(request.nome()))
            .thenReturn(false);

        when(categoriaRepository.findById(request.categoriaId()))
            .thenReturn(Optional.of(categoria));

        when(produtoRepository.save(any(Produto.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        ProdutoResponse resposta = service.atualizarProduto(produto.getId(), request);


        assertEquals(produto.getId(), resposta.id());
        assertEquals(request.nome(), resposta.nome());
        assertEquals(request.preco(), resposta.preco());
        assertEquals(request.descricao(), resposta.descricao());
        assertEquals(request.quantidadeEstoque(), resposta.quantidadeEstoque());
        assertEquals(request.categoriaId(), resposta.categoriaId());

        verify(produtoRepository).findById(produto.getId());
        verify(produtoRepository).existsByNome(request.nome());
        verify(categoriaRepository).findById(request.categoriaId());
        verify(produtoRepository).save(any(Produto.class));
    }

    @Test
    void atualizarProduto_deveLancarExcecaoQuandoProdutoNaoExistir() {
        ProdutoRequest request = new ProdutoRequest(
            "Coca-Cola Zero",
            new BigDecimal("6.99"),
            "Refrigerante sem açúcar",
            20,
            1L
        );
        Produto produto = criarProdutoPadrao();

        when(produtoRepository.findById(produto.getId()))
            .thenReturn(Optional.empty());

        assertThrows(
            ProdutoNaoEncontradoException.class,
            () -> service.atualizarProduto(produto.getId(), request)
        );

        verify(produtoRepository).findById(produto.getId());
        verify(produtoRepository, never()).existsByNome(request.nome());
        verify(categoriaRepository, never()).findById(request.categoriaId());
        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void atualizarProduto_deveLancarExcecaoQuandoNovoNomeJaExiste() {
        ProdutoRequest request = new ProdutoRequest(
            "Coca-Cola Zero",
            new BigDecimal("6.99"),
            "Refrigerante sem açúcar",
            20,
            1L
        );
        Produto produto = criarProdutoPadrao();

        when(produtoRepository.findById(produto.getId()))
            .thenReturn(Optional.of(produto));

        when(produtoRepository.existsByNome(request.nome()))
            .thenReturn(true);

        assertThrows(
            ProdutoJaExisteException.class,
            () -> service.atualizarProduto(produto.getId(), request)
        );

        verify(produtoRepository).findById(produto.getId());
        verify(produtoRepository).existsByNome(request.nome());
        verify(categoriaRepository, never()).findById(request.categoriaId());
        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void atualizarProduto_deveLancarExcecaoQuandoCategoriaNaoExistir() {
        ProdutoRequest request = new ProdutoRequest(
            "Coca-Cola Zero",
            new BigDecimal("6.99"),
            "Refrigerante sem açúcar",
            20,
            1L
        );
        Produto produto = criarProdutoPadrao();

        when(produtoRepository.findById(produto.getId()))
            .thenReturn(Optional.of(produto));

        when(produtoRepository.existsByNome(request.nome()))
            .thenReturn(false);

        when(categoriaRepository.findById(request.categoriaId()))
            .thenReturn(Optional.empty());

        assertThrows(
            CategoriaNaoEncontradaException.class,
            () -> service.atualizarProduto(produto.getId(), request)
        );

        verify(produtoRepository).findById(produto.getId());
        verify(produtoRepository).existsByNome(request.nome());
        verify(categoriaRepository).findById(request.categoriaId());
        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    void deletarProduto_deveExcluirComSucesso() {
        Produto produto = criarProdutoPadrao();

        when(produtoRepository.findById(produto.getId()))
            .thenReturn(Optional.of(produto));

        service.deletarProduto(produto.getId());

        verify(produtoRepository).findById(produto.getId());
        verify(produtoRepository).delete(produto);
    }

    @Test
    void deletarProduto_deveLancarExcecaoQuandoProdutoNaoExistir() {
        Long id = 1L;

        when(produtoRepository.findById(id))
            .thenReturn(Optional.empty());

        assertThrows(
            ProdutoNaoEncontradoException.class,
            () -> service.deletarProduto(id)
        );

        verify(produtoRepository).findById(id);
        verify(produtoRepository, never()).delete(any(Produto.class));
    }


    private Produto criarProdutoPadrao() {
        Produto produto = new Produto(
            "Coca-Cola",
            new BigDecimal("5.99"),
            "Refrigerante lata",
            10,
            criarCategoriaPadrao()
        );

        produto.setId(1L);

        return produto;
    }

    private ProdutoRequest criarRequestPadrao() {
        return new ProdutoRequest(
            "Coca-Cola",
            new BigDecimal("5.99"),
            "Refrigerante lata",
            10,
            1L
        );
    }

    private Categoria criarCategoriaPadrao() {
        Categoria categoria = new Categoria(
            "Bebidas",
            "Produtos líquidos"
        );

        categoria.setId(1L);

        return categoria;
    }
}