package com.exemplo.meu_primeiro_projeto.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.meu_primeiro_projeto.dto.filter.UsuarioFiltro;
import com.exemplo.meu_primeiro_projeto.dto.request.UsuarioRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.UsuarioResponse;
import com.exemplo.meu_primeiro_projeto.exception.RespostaErro;
import com.exemplo.meu_primeiro_projeto.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('MANAGER')")
@Tag(
    name = "Usuário",
    description = "Operações relacionadas ao gerenciamento de usuários."
)
public class UsuarioController {

    private final UsuarioService service;

    @Operation(
        summary = "Listar usuários",
        description = "Lista os usuários cadastrados no sistema, permitindo filtragem e paginação."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuários listados com sucesso"
    )
    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> listarUsuarios(
            UsuarioFiltro filtro,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        return ResponseEntity.ok(service.listarUsuarios(filtro, pageable));
    }

    @Operation(
        summary = "Buscar usuário por ID",
        description = "Busca um usuário pelo identificador informado."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuário encontrado"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Usuário não encontrado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RespostaErro.class)
        )
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(
        summary = "Cadastrar usuário",
        description = "Cadastra um novo usuário no sistema e cria automaticamente seu carrinho."
    )
    @ApiResponse(
        responseCode = "201",
        description = "Usuário criado com sucesso"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Dados enviados inválidos",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RespostaErro.class)
        )
    )
    @ApiResponse(
        responseCode = "409",
        description = "Email já cadastrado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RespostaErro.class)
        )
    )
    @PostMapping
    public ResponseEntity<UsuarioResponse> criarUsuario(
            @Valid @RequestBody UsuarioRequest request) {

        UsuarioResponse resposta = service.criarUsuario(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @Operation(
        summary = "Atualizar usuário",
        description = "Atualiza os dados de um usuário existente."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Usuário atualizado com sucesso"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Dados enviados inválidos",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RespostaErro.class)
        )
    )
    @ApiResponse(
        responseCode = "404",
        description = "Usuário não encontrado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RespostaErro.class)
        )
    )
    @ApiResponse(
        responseCode = "409",
        description = "Email já cadastrado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RespostaErro.class)
        )
    )
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request) {

        return ResponseEntity.ok(service.atualizarUsuario(id, request));
    }

    @Operation(
        summary = "Excluir usuário",
        description = "Remove um usuário existente pelo identificador informado."
    )
    @ApiResponse(
        responseCode = "204",
        description = "Usuário removido com sucesso"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Usuário não encontrado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RespostaErro.class)
        )
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        service.deletarUsuario(id);

        return ResponseEntity.noContent().build();
    }
}