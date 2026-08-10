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

import com.exemplo.meu_primeiro_projeto.dto.request.FornecedorRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.FornecedorResponse;
import com.exemplo.meu_primeiro_projeto.exception.RespostaErro;
import com.exemplo.meu_primeiro_projeto.service.FornecedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/fornecedores")
@Tag(
    name = "Fornecedor",
    description = "Operações relacionadas ao gerenciamento de fornecedores."
)
public class FornecedorController {

    private final FornecedorService service;

    public FornecedorController(FornecedorService service) {
        this.service = service;
    }
    
    @Operation(
        summary = "Listar fornecedores",
        description = "Lista todos os fornecedores cadastrados no sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Fornecedores listados com sucesso"
        )
    })
    @GetMapping 
    public ResponseEntity<List<FornecedorResponse>> listarFornecedores() {
        return ResponseEntity.ok(service.listarFornecedores());
    }

    @Operation(
        summary = "Listar fornecedores ativos",
        description = "Lista somente os fornecedores que estão ativos no sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Fornecedores ativos listados com sucesso"
        )
    })
    @GetMapping("/ativos")
    public ResponseEntity<List<FornecedorResponse>> listarFornecedoresAtivos() {
        return ResponseEntity.ok(service.listarFornecedoresAtivos());
    }

     @Operation(
        summary = "Buscar fornecedor por ID",
        description = "Busca um fornecedor pelo identificador informado."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Fornecedor encontrado"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Fornecedor não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(
        summary = "Cadastrar fornecedor",
        description = "Cadastra um novo fornecedor no sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Fornecedor criado com sucesso"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados enviados inválidos",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Fornecedor já cadastrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<FornecedorResponse> criarFornecedor( 
        @Valid @RequestBody FornecedorRequest request 
    ) {
        FornecedorResponse resposta = service.criarFornecedor(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @Operation(
        summary = "Atualizar fornecedor",
        description = "Atualiza os dados de um fornecedor existente."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Fornecedor atualizado com sucesso"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados enviados inválidos",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Fornecedor não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Fornecedor já cadastrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponse> atualizarFornecedor(
        @PathVariable Long id, @Valid @RequestBody FornecedorRequest request
    ) {
        return ResponseEntity.ok(service.atualizarFornecedor(id, request));
    }

    @Operation(
        summary = "Desativar fornecedor",
        description = "Desativa um fornecedor existente pelo identificador informado."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Fornecedor desativado com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Fornecedor não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativarFornecedor(@PathVariable Long id) {
        service.encerrarFornecedor(id);

        return ResponseEntity.noContent().build();
    }
}
