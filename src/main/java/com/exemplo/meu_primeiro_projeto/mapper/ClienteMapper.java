package com.exemplo.meu_primeiro_projeto.mapper;

import org.springframework.stereotype.Component;

import com.exemplo.meu_primeiro_projeto.dto.request.ClienteRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.ClienteResponse;
import com.exemplo.meu_primeiro_projeto.model.Cliente;

@Component
public class ClienteMapper {

    public ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone()
        );
    }

    public Cliente toEntity(ClienteRequest request) {
        return new Cliente(
                request.nome(),
                request.email(),
                request.telefone()
        );
    }
}