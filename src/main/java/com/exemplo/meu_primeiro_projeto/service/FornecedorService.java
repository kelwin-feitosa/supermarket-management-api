package com.exemplo.meu_primeiro_projeto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.dto.FornecedorRequest;
import com.exemplo.meu_primeiro_projeto.dto.FornecedorResponse;
import com.exemplo.meu_primeiro_projeto.exception.CnpjJaCadastradoException;
import com.exemplo.meu_primeiro_projeto.exception.FornecedorEmUsoException;
import com.exemplo.meu_primeiro_projeto.exception.FornecedorNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.model.Fornecedor;
import com.exemplo.meu_primeiro_projeto.repository.FornecedorRepository;

@Service
public class FornecedorService {

    private final FornecedorRepository repository;

    public FornecedorService(FornecedorRepository repository) {
        this.repository = repository;
    }

    public List<FornecedorResponse> listarFornecedores() {
        return repository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public FornecedorResponse buscarPorId(Long id) {
        return converterParaResponse(buscarEntidade(id));
    }

    public FornecedorResponse criarFornecedor(FornecedorRequest request) {
        verificarDuplicidade(request);

        Fornecedor fornecedor = repository.save(converterParaFornecedor(request));

        return converterParaResponse(fornecedor);
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

        return converterParaResponse(fornecedor);   
    }

    public void deletarFornecedor(Long id) {
        Fornecedor fornecedor = buscarEntidade(id);

        if (!fornecedor.getProdutos().isEmpty()) {
            throw new FornecedorEmUsoException(
                "Não é possível excluir um fornecedor que possui produtos cadastrados."
            );
        }

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

    private FornecedorResponse converterParaResponse(Fornecedor fornecedor) {
        return new FornecedorResponse(
            fornecedor.getId(),
            fornecedor.getNome(),
            fornecedor.getCnpj(),
            fornecedor.getTelefone()
        );
    }

    private Fornecedor converterParaFornecedor(FornecedorRequest request) {
        return new Fornecedor(
            request.nome(),
            request.cnpj(),
            request.telefone()
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
