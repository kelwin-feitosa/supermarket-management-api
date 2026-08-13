package com.exemplo.meu_primeiro_projeto.mapper;

import org.springframework.stereotype.Component;

import com.exemplo.meu_primeiro_projeto.dto.request.FornecedorRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.FornecedorResponse;
import com.exemplo.meu_primeiro_projeto.model.Fornecedor;

@Component
public class FornecedorMapper {

    public FornecedorResponse toResponse(Fornecedor fornecedor) {
        return new FornecedorResponse(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getCnpj(),
                fornecedor.getTelefone()
        );
    }

    public Fornecedor toEntity(FornecedorRequest request) {
        return new Fornecedor(
                request.nome(),
                request.cnpj(),
                request.telefone()
        );
    }
}