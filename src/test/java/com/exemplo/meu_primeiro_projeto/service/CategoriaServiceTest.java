package com.exemplo.meu_primeiro_projeto.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exemplo.meu_primeiro_projeto.dto.CategoriaRequest;
import com.exemplo.meu_primeiro_projeto.dto.CategoriaResponse;
import com.exemplo.meu_primeiro_projeto.exception.CategoriaEmUsoException;
import com.exemplo.meu_primeiro_projeto.exception.CategoriaJaExisteException;
import com.exemplo.meu_primeiro_projeto.exception.CategoriaNaoEncontradaException;
import com.exemplo.meu_primeiro_projeto.model.Categoria;
import com.exemplo.meu_primeiro_projeto.model.Produto;
import com.exemplo.meu_primeiro_projeto.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaService service;

    @Test
    void criarCategoria_deveCriarComSucesso() {
        
        CategoriaRequest request = criarRequestPadrao();

        Categoria categoriaSalva = criarCategoriaPadrao();

        when(repository.existsByNome(request.nome()))
            .thenReturn(false);

        when(repository.save(any(Categoria.class)))
            .thenReturn(categoriaSalva);



        CategoriaResponse resposta = service.criarCategoria(request);


        assertEquals(1L, resposta.id());
        assertEquals("Bebidas", resposta.nome());
        assertEquals("Produtos líquidos", resposta.descricao());


        verify(repository).existsByNome(request.nome());
        verify(repository).save(any(Categoria.class));
    }

    @Test
    void criarCategoria_deveLancarExcecaoQuandoNomeJaExiste() {

        CategoriaRequest request = criarRequestPadrao();

        when(repository.existsByNome(request.nome()))
                .thenReturn(true);


        assertThrows(
            CategoriaJaExisteException.class,
            () -> service.criarCategoria(request)
        );

        verify(repository, never()).save(any(Categoria.class));   
        verify(repository).existsByNome(request.nome());
    }

    @Test
    void listarCategorias_deveRetornarLista() {
        Categoria categoria1 = criarCategoriaPadrao();

        Categoria categoria2 = new Categoria(
            "Limpeza",
            "Produtos de limpeza"
        );
        categoria2.setId(2L);



        when(repository.findAll())
            .thenReturn(List.of(categoria1, categoria2));


        List<CategoriaResponse> resposta = service.listarCategorias();

        assertEquals(2, resposta.size());

        assertEquals(categoria1.getNome(), resposta.get(0).nome());
        assertEquals(categoria1.getDescricao(), resposta.get(0).descricao());

        assertEquals(categoria2.getNome(), resposta.get(1).nome());
        assertEquals(categoria2.getDescricao(), resposta.get(1).descricao());


        verify(repository).findAll();
    }

    @Test
    void buscarPorId_deveRetornarCategoriaQuandoExistir() {
        Categoria categoria = criarCategoriaPadrao();

        when(repository.findById(categoria.getId()))
            .thenReturn(Optional.of(categoria));

        CategoriaResponse resposta = service.buscarPorId(categoria.getId());

        assertEquals(categoria.getId(), resposta.id());
        assertEquals(categoria.getNome(), resposta.nome());
        assertEquals(categoria.getDescricao(), resposta.descricao());

        verify(repository).findById(categoria.getId());
    }

    @Test
    void buscarPorId_deveLancarExcecaoQuandoCategoriaNaoExistir(){
        Long id = 1L;

        when(repository.findById(id))
            .thenReturn(Optional.empty());

        assertThrows(
            CategoriaNaoEncontradaException.class,
            () -> service.buscarPorId(id)
        );

        verify(repository).findById(id);
    }

    @Test
    void atualizarCategoria_deveAtualizarComSucesso() {
        CategoriaRequest request = new CategoriaRequest(
            "Bebidas Geladas",
            "Refrigerantes e sucos"
        );

        Categoria categoria = criarCategoriaPadrao();

        when(repository.findById(1L))
            .thenReturn(Optional.of(categoria));

        when(repository.existsByNome(request.nome()))
            .thenReturn(false);

        when(repository.save(any(Categoria.class)))
            .thenReturn(categoria);

        CategoriaResponse resposta = service.atualizarCategoria(categoria.getId(), request);

        assertEquals(request.nome(), resposta.nome());
        assertEquals(request.descricao(), resposta.descricao());

        verify(repository).findById(1L);
        verify(repository).save(any(Categoria.class));
    }

    @Test
    void atualizarCategoria_deveLancarExcecaoQuandoCategoriaNaoExistir() {
        CategoriaRequest request = criarRequestPadrao();

        when(repository.findById(1L))
            .thenReturn(Optional.empty());
        
        assertThrows(
            CategoriaNaoEncontradaException.class,
            () -> service.atualizarCategoria(1L, request)
        );

        verify(repository).findById(1L);
        verify(repository, never()).save(any(Categoria.class));
    }

    @Test
    void atualizarCategoria_deveLancarExcecaoQuandoNovoNomeJaExiste() {
        CategoriaRequest request = new CategoriaRequest(
            "Limpeza",
            "Produtos de limpeza"
        );  

        Categoria categoria = criarCategoriaPadrao();

        when(repository.findById(categoria.getId()))
            .thenReturn(Optional.of(categoria));

        when(repository.existsByNome(request.nome()))
            .thenReturn(true);

        assertThrows(
            CategoriaJaExisteException.class,
            () -> service.atualizarCategoria(categoria.getId(), request)
        );
        
        verify(repository).findById(categoria.getId());
        verify(repository).existsByNome(request.nome());
        verify(repository, never()).save(any(Categoria.class));
    }

    @Test
    void deletarCategoria_deveExcluirComSucesso()  {
        Categoria categoria = criarCategoriaPadrao();

        when(repository.findById(categoria.getId()))
            .thenReturn(Optional.of(categoria));
            

        service.deletarCategoria(categoria.getId());

        verify(repository).findById(categoria.getId());
        verify(repository).delete(categoria);
    }

    @Test
    void deletarCategoria_deveLancarExcecaoQuandoCategoriaNaoExistir() {

        when(repository.findById(1L))
            .thenReturn(Optional.empty());

        assertThrows(
            CategoriaNaoEncontradaException.class,
            () -> service.deletarCategoria(1L)
        );

        verify(repository).findById(1L);
        verify(repository, never()).delete(any(Categoria.class));
    }

    @Test
    void deletarCategoria_deveLancarExcecaoQuandoCategoriaPossuiProdutos() {
        Categoria categoria = criarCategoriaPadrao();

        Produto produto = new Produto(
            "Coca-Cola",
            new BigDecimal("5.99"),
            "Refrigerante lata",
            10,
            categoria
        );

        categoria.getProdutos().add(produto);

        when(repository.findById(categoria.getId()))
            .thenReturn(Optional.of(categoria));

        assertThrows(
            CategoriaEmUsoException.class,
            () -> service.deletarCategoria(categoria.getId())
        );

        verify(repository).findById(categoria.getId());
        verify(repository, never()).delete(any(Categoria.class));
    }

    private Categoria criarCategoriaPadrao() {
        Categoria categoria = new Categoria(
            "Bebidas",
            "Produtos líquidos"
        );
        categoria.setId(1L);

        return categoria;
    }

    private CategoriaRequest criarRequestPadrao() {
        return new CategoriaRequest(
            "Bebidas",
            "Produtos líquidos"
        );
    }
}