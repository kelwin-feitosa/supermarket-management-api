package com.exemplo.meu_primeiro_projeto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.dto.request.CategoriaRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.CategoriaResponse;
import com.exemplo.meu_primeiro_projeto.exception.CategoriaEmUsoException;
import com.exemplo.meu_primeiro_projeto.exception.CategoriaJaExisteException;
import com.exemplo.meu_primeiro_projeto.exception.CategoriaNaoEncontradaException;
import com.exemplo.meu_primeiro_projeto.mapper.CategoriaMapper;
import com.exemplo.meu_primeiro_projeto.model.Categoria;
import com.exemplo.meu_primeiro_projeto.repository.CategoriaRepository;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;

    public CategoriaService(CategoriaRepository repository, CategoriaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CategoriaResponse> listarCategorias() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CategoriaResponse criarCategoria(CategoriaRequest request) {
        verificarDuplicidade(request); //Verifica se não existe um igual

        Categoria categoria = mapper.toEntity(request);
        Categoria categoriaSalva = repository.save(categoria);

        return mapper.toResponse(categoriaSalva);
    }

    public CategoriaResponse atualizarCategoria(Long id, CategoriaRequest request) {
        Categoria categoria = buscarEntidade(id);

        // Se o nome mudou, verifica se já existe outra categoria com esse nome.
        verificarDuplicidade(categoria, request); 

        categoria.atualizar(
            request.nome(),
            request.descricao()
        );

        return mapper.toResponse(repository.save(categoria));
    }

    public CategoriaResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidade(id));
    }

    public void deletarCategoria(Long id) {
        Categoria categoria = buscarEntidade(id);

        if(!categoria.getProdutos().isEmpty()) {
            throw new CategoriaEmUsoException(
                "Não é possível excluir uma categoria com produtos vinculados."
            );
        }
        repository.delete(categoria);
    }

    private Categoria buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontradaException("Categoria não encontrada."));
    }

    private void verificarDuplicidade(CategoriaRequest request) {
        if(repository.existsByNome(request.nome())) {
            throw new CategoriaJaExisteException("Já existe uma categoria com esse nome.");
        }
    }

    private void verificarDuplicidade(Categoria categoria, CategoriaRequest request) {
        if(!categoria.getNome().equals(request.nome())) {
            verificarDuplicidade(request);
        }
    }
}
