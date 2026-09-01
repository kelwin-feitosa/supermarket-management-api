package com.exemplo.meu_primeiro_projeto.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.dto.filter.FornecedorFiltro;
import com.exemplo.meu_primeiro_projeto.dto.request.FornecedorRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.FornecedorResponse;
import com.exemplo.meu_primeiro_projeto.exception.CnpjJaCadastradoException;
import com.exemplo.meu_primeiro_projeto.exception.FornecedorNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.mapper.FornecedorMapper;
import com.exemplo.meu_primeiro_projeto.model.Fornecedor;
import com.exemplo.meu_primeiro_projeto.repository.FornecedorRepository;
import com.exemplo.meu_primeiro_projeto.repository.specification.FornecedorSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository repository;
    private final FornecedorMapper mapper;

    public Page<FornecedorResponse> listarFornecedores(FornecedorFiltro filtro, Pageable pageable) {
        return repository.findAll(FornecedorSpecification.comFiltro(filtro), pageable)
                .map(mapper::toResponse);
    }

    public FornecedorResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidade(id));
    }

    public FornecedorResponse criarFornecedor(FornecedorRequest request) {
        verificarDuplicidade(request);

        Fornecedor fornecedor = repository.save(mapper.toEntity(request));

        return mapper.toResponse(fornecedor);
    }

    public FornecedorResponse atualizarFornecedor(Long id, FornecedorRequest request) {
        Fornecedor fornecedor = buscarEntidade(id);

        verificarDuplicidade(request, fornecedor);

        fornecedor.atualizar(
            request.nome(),
            request.cnpj(),
            request.telefone()
        );
        
        fornecedor = repository.save(fornecedor);

        return mapper.toResponse(fornecedor);   
    }

    public void encerrarFornecedor(Long id) {
        Fornecedor fornecedor = buscarEntidade(id);

        if (!fornecedor.getCompras().isEmpty()) {
            fornecedor.setAtivo(false);
            repository.save(fornecedor);
            return;
        }

        repository.delete(fornecedor);
    }


    private Fornecedor buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new FornecedorNaoEncontradoException(
                    "Fornecedor não encontrado.")
                );
    }

    private void verificarDuplicidade(FornecedorRequest request) {
        if(repository.existsByCnpj(request.cnpj())) {
            throw new CnpjJaCadastradoException("Esse cnpj já está cadastrado.");
        }
    }

    private void verificarDuplicidade(FornecedorRequest request, Fornecedor fornecedor) {
        if(!request.cnpj().equals(fornecedor.getCnpj()) ) {
            verificarDuplicidade(request);
        }
    }
}