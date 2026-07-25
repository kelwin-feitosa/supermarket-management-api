package com.exemplo.meu_primeiro_projeto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.meu_primeiro_projeto.dto.CompraRequest;
import com.exemplo.meu_primeiro_projeto.dto.CompraResponse;
import com.exemplo.meu_primeiro_projeto.service.CompraService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/compras")
public class CompraController {
    private final CompraService compraService;
    
    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<CompraResponse> realizarCompra(@Valid @RequestBody CompraRequest request) {
        CompraResponse resposta = compraService.realizarCompra(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CompraResponse> buscarCompra(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.buscarCompra(id));
    }

    @GetMapping
    public ResponseEntity<List<CompraResponse>> listarCompras() {
        return ResponseEntity.ok(compraService.listarCompras());
    }
}
