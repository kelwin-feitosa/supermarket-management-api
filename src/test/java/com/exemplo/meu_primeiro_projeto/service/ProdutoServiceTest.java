package com.exemplo.meu_primeiro_projeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.exemplo.meu_primeiro_projeto.dto.filter.ProdutoFiltro;
import com.exemplo.meu_primeiro_projeto.dto.request.ProdutoRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.ProdutoResponse;
import com.exemplo.meu_primeiro_projeto.exception.CategoriaNaoEncontradaException;
import com.exemplo.meu_primeiro_projeto.exception.ProdutoJaExisteException;
import com.exemplo.meu_primeiro_projeto.exception.ProdutoNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.mapper.ProdutoMapper;
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

    @Mock
    private ProdutoMapper mapper;

    @Mock
    private ProdutoFiltro filtro;

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

        when(mapper.toEntity(request, categoria))
            .thenReturn(produto);

        when(mapper.toResponse(produto))
            .thenReturn(criarResponsePadrao(produto));


        ProdutoResponse resposta = service.criarProduto(request);

        
        assertEquals(produto.getId(), resposta.id());
        assertEquals(request.nome(), resposta.nome());
        assertEquals(request.preco(), resposta.preco());
        assertEquals(request.descricao(), resposta.descricao());
        assertEquals(request.quantidadeEstoque(), resposta.quantidadeEstoque());
        assertEquals(request.categoriaId(), resposta.categoriaId());

        verify(produtoRepository).existsByNome(request.nome());
        verify(categoriaRepository).findById(request.categoriaId());
        verify(mapper).toEntity(request, categoria);
        verify(produtoRepository).save(produto);
        verify(mapper).toResponse(produto);
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
    void listarProdutos_deveRetornarPaginaDeProdutos() {
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

        Pageable pageable = PageRequest.of(0, 10);

        Page<Produto> pagina = new PageImpl<>(
            List.of(produto1, produto2),
            pageable,
            2
        );

        when(produtoRepository.findAll(ArgumentMatchers.<Specification<Produto>>any(), eq(pageable)))
            .thenReturn(pagina);

        when(mapper.toResponse(produto1))
            .thenReturn(criarResponsePadrao(produto1));

        when(mapper.toResponse(produto2))
            .thenReturn(criarResponsePadrao(produto2));

        
        Page<ProdutoResponse> resposta = service.listarProdutos(filtro, pageable);

        assertEquals(2, resposta.getContent().size());

        assertEquals(2, resposta.getTotalElements());
        assertEquals(1, resposta.getTotalPages());

        assertEquals(produto1.getNome(), resposta.getContent().get(0).nome());
        assertEquals(produto1.getPreco(), resposta.getContent().get(0).preco());
        assertEquals(produto1.getDescricao(), resposta.getContent().get(0).descricao());
        assertEquals(produto1.getQuantidadeEstoque(), resposta.getContent().get(0).quantidadeEstoque());
        assertEquals(produto1.getCategoria().getId(), resposta.getContent().get(0).categoriaId());

        assertEquals(produto2.getNome(), resposta.getContent().get(1).nome());
        assertEquals(produto2.getPreco(), resposta.getContent().get(1).preco());
        assertEquals(produto2.getDescricao(), resposta.getContent().get(1).descricao());
        assertEquals(produto2.getQuantidadeEstoque(), resposta.getContent().get(1).quantidadeEstoque());
        assertEquals(produto2.getCategoria().getId(), resposta.getContent().get(1).categoriaId());

        verify(produtoRepository).findAll(
            ArgumentMatchers.<Specification<Produto>>any(),
            eq(pageable)
        );
        verify(mapper).toResponse(produto1);
        verify(mapper).toResponse(produto2);
    }

    @Test
    void buscarPorId_deveRetornarProdutoQuandoExistir() {
        Produto produto = criarProdutoPadrao();

        when(produtoRepository.findById(produto.getId()))
            .thenReturn(Optional.of(produto));
            
        when(mapper.toResponse(produto))
            .thenReturn(criarResponsePadrao(produto));

        ProdutoResponse resposta = service.buscarPorId(produto.getId());

        assertEquals(produto.getId(), resposta.id());
        assertEquals(produto.getNome(), resposta.nome());
        assertEquals(produto.getPreco(), resposta.preco());
        assertEquals(produto.getDescricao(), resposta.descricao());
        assertEquals(produto.getQuantidadeEstoque(), resposta.quantidadeEstoque());
        assertEquals(produto.getCategoria().getId(), resposta.categoriaId());

        verify(produtoRepository).findById(produto.getId());
        verify(mapper).toResponse(produto);
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
        ProdutoRequest request = criarRequestAtualizacao();

        Produto produto = criarProdutoPadrao();

        when(produtoRepository.findById(produto.getId()))
            .thenReturn(Optional.of(produto));
        
        when(produtoRepository.existsByNome(request.nome()))
            .thenReturn(false);

        when(categoriaRepository.findById(request.categoriaId()))
            .thenReturn(Optional.of(categoria));

        when(produtoRepository.save(any(Produto.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        when(mapper.toResponse(any(Produto.class)))
        .thenAnswer(invocation -> criarResponsePadrao(invocation.getArgument(0)));


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
        verify(produtoRepository).save(produto);
        verify(mapper).toResponse(produto);
    }

    @Test
    void atualizarProduto_deveLancarExcecaoQuandoProdutoNaoExistir() {
        ProdutoRequest request = criarRequestAtualizacao();
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
        ProdutoRequest request = criarRequestAtualizacao();
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
        ProdutoRequest request = criarRequestAtualizacao();
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

    private ProdutoRequest criarRequestAtualizacao() {
        return new ProdutoRequest(
            "Coca-Cola Zero",
            new BigDecimal("6.99"),
            "Refrigerante sem açúcar",
            20,
            1L
        );
    }

    private ProdutoResponse criarResponsePadrao(Produto produto) {
        return new ProdutoResponse(
            produto.getId(),
            produto.getNome(),
            produto.getPreco(),
            produto.getDescricao(),
            produto.getQuantidadeEstoque(),
            produto.getCategoria().getId(),
            produto.getDataCadastro()
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