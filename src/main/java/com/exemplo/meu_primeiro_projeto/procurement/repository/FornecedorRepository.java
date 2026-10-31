package com.exemplo.meu_primeiro_projeto.procurement.repository;

import com.exemplo.meu_primeiro_projeto.procurement.model.Fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>, JpaSpecificationExecutor<Fornecedor> {
    boolean existsByCnpj(String cnpj);
}