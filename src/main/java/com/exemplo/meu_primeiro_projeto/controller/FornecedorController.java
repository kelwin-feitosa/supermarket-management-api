package com.exemplo.meu_primeiro_projeto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.meu_primeiro_projeto.dto.FornecedorRequest;
import com.exemplo.meu_primeiro_projeto.dto.FornecedorResponse;
import com.exemplo.meu_primeiro_projeto.service.FornecedorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService service;

    public FornecedorController(FornecedorService service) {
        this.service = service;
    }
    
    @GetMapping
    public ResponseEntity<List<FornecedorResponse>> listarFornecedores() {
        return ResponseEntity.ok(service.listarFornecedores());
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<FornecedorResponse>> listarFornecedoresAtivos() {
        return ResponseEntity.ok(service.listarFornecedoresAtivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<FornecedorResponse> criarFornecedor( 
        @Valid @RequestBody FornecedorRequest request 
    ) {
        FornecedorResponse resposta = service.criarFornecedor(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponse> atualizarFornecedor(
        @PathVariable Long id, @Valid @RequestBody FornecedorRequest request
    ) {
        return ResponseEntity.ok(service.atualizarFornecedor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativarFornecedor(@PathVariable Long id) {
        service.encerrarFornecedor(id);

        return ResponseEntity.noContent().build();
    }
}
