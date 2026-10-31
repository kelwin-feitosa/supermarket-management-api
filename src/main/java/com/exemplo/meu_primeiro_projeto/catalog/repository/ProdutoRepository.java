package com.exemplo.meu_primeiro_projeto.catalog.repository;

import com.exemplo.meu_primeiro_projeto.catalog.model.Produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto>  {
    boolean existsByNome(String nome);
}
