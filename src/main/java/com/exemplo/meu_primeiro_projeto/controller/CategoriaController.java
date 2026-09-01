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

import com.exemplo.meu_primeiro_projeto.dto.filter.CategoriaFiltro;
import com.exemplo.meu_primeiro_projeto.dto.request.CategoriaRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.CategoriaResponse;
import com.exemplo.meu_primeiro_projeto.exception.RespostaErro;
import com.exemplo.meu_primeiro_projeto.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categorias")

@Tag(
    name = "Categoria",
    description = "Operações relacionadas ao gerenciamento de categorias."
)
public class CategoriaController {

    private final CategoriaService service;
    
    @Operation(
        summary = "Listar categorias",
        description = "Lista as categorias cadastradas no sistema, permitindo filtragem e paginação."
    )
    @ApiResponses(
        @ApiResponse(
            responseCode = "200",
            description = "Categorias listadas com sucesso"
        )
    )
    @GetMapping
    public ResponseEntity<Page<CategoriaResponse>> listarCategorias(
            CategoriaFiltro filtro,
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {

        return ResponseEntity.ok(service.listarCategorias(filtro, pageable));
    }

    @Operation(
        summary = "Buscar categoria por ID",
        description = "Busca uma categoria pelo identificador informado."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
        @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(
        summary = "Cadastrar categoria",
        description = "Cadastra uma nova categoria no sistema."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
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
            description = "Categoria já cadastrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<CategoriaResponse> criarCategoria(@Valid @RequestBody CategoriaRequest categoriaNova) {
        CategoriaResponse resposta = service.criarCategoria(categoriaNova);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    @Operation(
        summary = "Atualizar categoria",
        description = "Atualiza os dados de uma categoria existente."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
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
            description = "Categoria não encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Categoria já cadastrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> atualizarCategoria(
        @PathVariable Long id, 
        @Valid @RequestBody CategoriaRequest categoriaAtualizada) {

        return ResponseEntity.ok(service.atualizarCategoria(id, categoriaAtualizada));
    }

    @Operation(
        summary = "Excluir categoria",
        description = "Remove uma categoria existente pelo identificador informado."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso"),
        @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        service.deletarCategoria(id);

        return ResponseEntity.noContent().build();
    }

}
