package com.exemplo.meu_primeiro_projeto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.dto.request.FornecedorRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.FornecedorResponse;
import com.exemplo.meu_primeiro_projeto.exception.CnpjJaCadastradoException;
import com.exemplo.meu_primeiro_projeto.exception.FornecedorNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.mapper.FornecedorMapper;
import com.exemplo.meu_primeiro_projeto.model.Fornecedor;
import com.exemplo.meu_primeiro_projeto.repository.FornecedorRepository;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;
    private final FornecedorMapper mapper;

    public FornecedorService(FornecedorRepository repository, FornecedorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<FornecedorResponse> listarFornecedores() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<FornecedorResponse> listarFornecedoresAtivos() {
        return repository.findByAtivoTrue()
                .stream()
                .map(mapper::toResponse)
                .toList();
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