package com.exemplo.meu_primeiro_projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemplo.meu_primeiro_projeto.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    boolean existsByCnpj(String cnpj);
    List<Fornecedor> findByAtivoTrue();
}