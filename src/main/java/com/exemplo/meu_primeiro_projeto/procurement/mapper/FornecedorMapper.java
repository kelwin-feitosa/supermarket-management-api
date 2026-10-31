package com.exemplo.meu_primeiro_projeto.procurement.mapper;

import com.exemplo.meu_primeiro_projeto.procurement.dto.request.FornecedorRequest;
import com.exemplo.meu_primeiro_projeto.procurement.dto.response.FornecedorResponse;
import com.exemplo.meu_primeiro_projeto.procurement.model.Fornecedor;

import org.springframework.stereotype.Component;

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