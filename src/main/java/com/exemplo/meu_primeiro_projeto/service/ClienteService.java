package com.exemplo.meu_primeiro_projeto.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.meu_primeiro_projeto.dto.request.ClienteRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.ClienteResponse;
import com.exemplo.meu_primeiro_projeto.exception.ClienteEmailJaExisteException;
import com.exemplo.meu_primeiro_projeto.exception.ClienteNaoEncontradoException;
import com.exemplo.meu_primeiro_projeto.model.Cliente;
import com.exemplo.meu_primeiro_projeto.repository.ClienteRepository;

@Service    
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<ClienteResponse> listarClientes() {
        return repository.findAll()
                    .stream()
                    .map(this::converterParaResponse)
                    .toList();
    }

    public ClienteResponse buscarPorId(Long id) {
        return converterParaResponse(buscarEntidade(id));
    }

    public ClienteResponse criarCliente(ClienteRequest request) {
        verificarDuplicidade(request);

        Cliente clienteSalvo = repository.save(converterParaCliente(request));

        return converterParaResponse(clienteSalvo);
    }

    public ClienteResponse atualizarCliente(Long id, ClienteRequest request) {
        Cliente cliente = buscarEntidade(id);

        verificarDuplicidade(cliente, request);

        cliente.atualizar(
            request.nome(),
            request.email(),
            request.telefone()
        );

        return converterParaResponse(repository.save(cliente));
    }

    public void deletarCliente(Long id) {
        Cliente cliente = buscarEntidade(id);
        
        repository.delete(cliente);
    }

    private Cliente converterParaCliente(ClienteRequest request) {
        return new Cliente(
            request.nome(),
            request.email(),
            request.telefone()
        );
    }

    private ClienteResponse converterParaResponse(Cliente cliente) {
        return new ClienteResponse(
            cliente.getId(),
            cliente.getNome(),
            cliente.getEmail(),
            cliente.getTelefone()
        );
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
