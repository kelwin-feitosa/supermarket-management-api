package com.exemplo.meu_primeiro_projeto.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
import com.exemplo.meu_primeiro_projeto.repository.specification.ProdutoSpecification;

@Service
public class ProdutoService {
    
    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final ProdutoMapper mapper;
    
    public ProdutoService(ProdutoRepository repository, CategoriaRepository categoriaRepository, ProdutoMapper mapper) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.mapper = mapper;
    }


    public Page<ProdutoResponse> listarProdutos(ProdutoFiltro filtro, Pageable pageable) {
        return repository.findAll(ProdutoSpecification.comFiltro(filtro), pageable)
                .map(mapper::toResponse);
    }

    public ProdutoResponse criarProduto(ProdutoRequest request) {
        verificarDuplicidade(request);
        
        Categoria categoria = buscarCategoria(request.categoriaId());

        Produto salvo = repository.save(mapper.toEntity(request, categoria));

        return mapper.toResponse(salvo);
    }

    public ProdutoResponse atualizarProduto(Long id, ProdutoRequest request) {
        Produto produto = buscarEntidade(id);
        verificarDuplicidade(produto, request);
        Categoria categoria = buscarCategoria(request.categoriaId());

        produto.atualizar(
            request.nome(),
            request.preco(),
            request.descricao(),
            request.quantidadeEstoque(),
            categoria
        );

        return mapper.toResponse(repository.save(produto));
    }

    public ProdutoResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidade(id));
    }

    public void deletarProduto(Long id) {
        Produto produto = buscarEntidade(id);
        repository.delete(produto);                   
    }

    private Produto buscarEntidade(Long id) { //Para uso interno
        return repository.findById(id)
            .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado."));
    }

    private Categoria buscarCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> 
                    new CategoriaNaoEncontradaException("Categoria não encontrada.")
                );
    }
    
    private void verificarDuplicidade(ProdutoRequest request) {
        if(repository.existsByNome(request.nome())) {
            throw new ProdutoJaExisteException("Já existe um produto com esse nome.");
        }
    }

    private void verificarDuplicidade(Produto produto, ProdutoRequest request) {
        if(!produto.getNome().equals(request.nome())) {
            verificarDuplicidade(request);
        }
    }
}