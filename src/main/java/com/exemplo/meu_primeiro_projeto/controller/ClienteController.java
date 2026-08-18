package com.exemplo.meu_primeiro_projeto.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.exemplo.meu_primeiro_projeto.dto.filter.ClienteFiltro;
import com.exemplo.meu_primeiro_projeto.dto.request.ClienteRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.ClienteResponse;
import com.exemplo.meu_primeiro_projeto.exception.RespostaErro;
import com.exemplo.meu_primeiro_projeto.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
@Tag(
    name = "Cliente",
    description = "Operações relacionadas ao gerenciamento de clientes."
)
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @Operation(
        summary = "Listar clientes",
        description = "Lista os clientes cadastrados no sistema, permitindo filtragem e paginação."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Clientes listados com sucesso"
        )
    })
    @GetMapping
    public ResponseEntity<Page<ClienteResponse>> listarClientes(
            ClienteFiltro filtro,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        return ResponseEntity.ok(service.listarClientes(filtro, pageable));
    }

    @Operation(
        summary = "Buscar cliente por ID",
        description = "Busca um cliente pelo identificador informado."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Cliente encontrado"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Cliente não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(
        summary = "Cadastrar cliente",
        description = "Cadastra um novo cliente no sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Cliente criado com sucesso"
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
            description = "Email já cadastrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<ClienteResponse> criarCliente(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse resposta = service.criarCliente(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @Operation(
        summary = "Atualizar cliente",
        description = "Atualiza os dados de um cliente existente."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Cliente atualizado com sucesso"
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
            description = "Cliente não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Email já cadastrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizarCliente(
        @PathVariable Long id, @Valid @RequestBody ClienteRequest request) {

            return ResponseEntity.ok(service.atualizarCliente(id, request));
    }


    @Operation(
        summary = "Excluir cliente",
        description = "Remove um cliente existente pelo identificador informado."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Cliente removido com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Cliente não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        service.deletarCliente(id);

        return ResponseEntity.noContent().build();
    }
}
