package com.exemplo.meu_primeiro_projeto.identity.repository;

import com.exemplo.meu_primeiro_projeto.identity.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario>{
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}