package com.exemplo.meu_primeiro_projeto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.dto.request.ClienteRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.ClienteResponse;
import com.exemplo.meu_primeiro_projeto.exception.ClienteEmailJaExisteException;
import com.exemplo.meu_primeiro_projeto.exception.ClienteNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.mapper.ClienteMapper;
import com.exemplo.meu_primeiro_projeto.model.Cliente;
import com.exemplo.meu_primeiro_projeto.repository.ClienteRepository;

@Service    
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public ClienteService(ClienteRepository repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ClienteResponse> listarClientes() {
        return repository.findAll()
                    .stream()
                    .map(mapper::toResponse)
                    .toList();
    }

    public ClienteResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidade(id));
    }

    public ClienteResponse criarCliente(ClienteRequest request) {
        verificarDuplicidade(request);

        Cliente clienteSalvo = repository.save(mapper.toEntity(request));

        return mapper.toResponse(clienteSalvo);
    }

    public ClienteResponse atualizarCliente(Long id, ClienteRequest request) {
        Cliente cliente = buscarEntidade(id);

        verificarDuplicidade(cliente, request);

        cliente.atualizar(
            request.nome(),
            request.email(),
            request.telefone()
        );

        return mapper.toResponse(repository.save(cliente));
    }

    public void deletarCliente(Long id) {
        Cliente cliente = buscarEntidade(id);
        
        repository.delete(cliente);
    }

    private Cliente buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado."));
    }

    private void verificarDuplicidade(ClienteRequest request) {
        if(repository.existsByEmail(request.email())) {
            throw new ClienteEmailJaExisteException("Esse email já está cadastrado.");
        }
    }

    private void verificarDuplicidade(Cliente cliente, ClienteRequest request) {
        if(!cliente.getEmail().equals(request.email())) {
            verificarDuplicidade(request);
        }
    }
}