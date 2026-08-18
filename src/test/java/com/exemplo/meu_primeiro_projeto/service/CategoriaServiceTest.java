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
import static org.mockito.ArgumentMatchers.eq;

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

import com.exemplo.meu_primeiro_projeto.dto.filter.CategoriaFiltro;
import com.exemplo.meu_primeiro_projeto.dto.request.CategoriaRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.CategoriaResponse;
import com.exemplo.meu_primeiro_projeto.exception.CategoriaEmUsoException;
import com.exemplo.meu_primeiro_projeto.exception.CategoriaJaExisteException;
import com.exemplo.meu_primeiro_projeto.exception.CategoriaNaoEncontradaException;
import com.exemplo.meu_primeiro_projeto.mapper.CategoriaMapper;
import com.exemplo.meu_primeiro_projeto.model.Categoria;
import com.exemplo.meu_primeiro_projeto.model.Produto;
import com.exemplo.meu_primeiro_projeto.repository.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @Mock
    private CategoriaMapper mapper;

    @Mock
    private CategoriaFiltro filtro;

    @InjectMocks
    private CategoriaService service;

    @Test
    void criarCategoria_deveCriarComSucesso() {
        
        CategoriaRequest request = criarRequestPadrao();

        Categoria categoria = criarCategoriaPadrao();

        CategoriaResponse response = criarResponsePadrao();

        when(repository.existsByNome(request.nome()))
            .thenReturn(false);

        when(mapper.toEntity(request))
            .thenReturn(categoria);

        when(repository.save(any(Categoria.class)))
            .thenReturn(categoria);

        when(mapper.toResponse(categoria))
            .thenReturn(response);

        CategoriaResponse resposta = service.criarCategoria(request);


        assertEquals(1L, resposta.id());
        assertEquals("Bebidas", resposta.nome());
        assertEquals("Produtos líquidos", resposta.descricao());


        verify(repository).existsByNome(request.nome());
        verify(mapper).toEntity(request);
        verify(repository).save(any(Categoria.class));
        verify(mapper).toResponse(categoria);
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
    void listarCategorias_deveRetornarPagina() {
        Categoria categoria1 = criarCategoriaPadrao();

        Categoria categoria2 = new Categoria(
            "Limpeza",
            "Produtos de limpeza"
        );
        categoria2.setId(2L);

        CategoriaResponse response1 = criarResponsePadrao();

        CategoriaResponse response2 = new CategoriaResponse(
            2L,
            "Limpeza",
            "Produtos de limpeza"
        );

        Pageable pageable = PageRequest.of(0, 10);

        Page<Categoria> pagina = new PageImpl<>(
            List.of(categoria1, categoria2),
            pageable,
            2
        );

        when(repository.findAll(
            ArgumentMatchers.<Specification<Categoria>>any(),
            eq(pageable)
        )).thenReturn(pagina);

        when(mapper.toResponse(categoria1))
            .thenReturn(response1);

        when(mapper.toResponse(categoria2))
            .thenReturn(response2);
        

        Page<CategoriaResponse> resposta = service.listarCategorias(filtro, pageable);

        assertEquals(2, resposta.getContent().size());

        assertEquals(2, resposta.getTotalElements());
        assertEquals(1, resposta.getTotalPages());

        assertEquals(categoria1.getNome(), resposta.getContent().get(0).nome());
        assertEquals(categoria1.getDescricao(), resposta.getContent().get(0).descricao());

        assertEquals(categoria2.getNome(), resposta.getContent().get(1).nome());
        assertEquals(categoria2.getDescricao(), resposta.getContent().get(1).descricao());

        
        verify(repository).findAll(
            ArgumentMatchers.<Specification<Categoria>>any(),
            eq(pageable)
        );
        verify(mapper).toResponse(categoria1);
        verify(mapper).toResponse(categoria2);
    }

    @Test
    void buscarPorId_deveRetornarCategoriaQuandoExistir() {
        Categoria categoria = criarCategoriaPadrao();

        CategoriaResponse response = criarResponsePadrao();

        when(repository.findById(categoria.getId()))
            .thenReturn(Optional.of(categoria));

        when(mapper.toResponse(categoria))
            .thenReturn(response);

        CategoriaResponse resposta = service.buscarPorId(categoria.getId());

        assertEquals(categoria.getId(), resposta.id());
        assertEquals(categoria.getNome(), resposta.nome());
        assertEquals(categoria.getDescricao(), resposta.descricao());

        verify(repository).findById(categoria.getId());
        verify(mapper).toResponse(categoria);
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

        CategoriaResponse response = new CategoriaResponse(
            1L,
            request.nome(),
            request.descricao()
        );

        when(repository.findById(1L))
            .thenReturn(Optional.of(categoria));

        when(repository.existsByNome(request.nome()))
            .thenReturn(false);

        when(repository.save(any(Categoria.class)))
            .thenReturn(categoria);
        
        when(mapper.toResponse(categoria))
            .thenReturn(response);

        CategoriaResponse resposta = service.atualizarCategoria(categoria.getId(), request);

        assertEquals(request.nome(), resposta.nome());
        assertEquals(request.descricao(), resposta.descricao());

        verify(repository).findById(1L);
        verify(repository).save(any(Categoria.class));
        verify(mapper).toResponse(categoria);
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

    private CategoriaResponse criarResponsePadrao() {
        return new CategoriaResponse(
            1L,
            "Bebidas",
            "Produtos líquidos"
        );
    }
}