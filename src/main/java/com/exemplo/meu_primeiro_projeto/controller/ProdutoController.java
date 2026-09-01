package com.exemplo.meu_primeiro_projeto.controller;

import org.springframework.web.bind.annotation.RestController;

import com.exemplo.meu_primeiro_projeto.dto.filter.ProdutoFiltro;
import com.exemplo.meu_primeiro_projeto.dto.request.ProdutoRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.ProdutoResponse;
import com.exemplo.meu_primeiro_projeto.exception.RespostaErro;
import com.exemplo.meu_primeiro_projeto.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/produtos")
@Tag(
    name = "Produto",
    description = "Operações relacionadas ao gerenciamento de produtos."
)
public class ProdutoController {
    
    private final ProdutoService service;

    @Operation(
        summary = "Listar produtos",
        description = """
        Lista os produtos cadastrados de forma paginada.

        Parâmetros suportados:
        - page: número da página (inicia em 0)
        - size: quantidade de registros por página
        - sort: campo e direção da ordenação (ex.: nome,asc)
        """
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Produtos listados com sucesso"
        )
    })
    @GetMapping
    public ResponseEntity<Page<ProdutoResponse>> obterProdutos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable,
            ProdutoFiltro filtro) {

        return ResponseEntity.ok(service.listarProdutos(filtro, pageable));
    }

    @Operation(
        summary = "Buscar produto por ID",
        description = "Busca um produto pelo identificador informado."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Produto encontrado"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(
        summary = "Cadastrar produto",
        description = "Cadastra um novo produto no sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Produto criado com sucesso"
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
            description = "Produto já cadastrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<ProdutoResponse> criarProduto(@Valid @RequestBody ProdutoRequest novoProduto) {
        
        ProdutoResponse response = service.criarProduto(novoProduto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
        summary = "Atualizar produto",
        description = "Atualiza os dados de um produto existente."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Produto atualizado com sucesso"
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
            description = "Produto não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Produto já cadastrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizarProduto(@PathVariable Long id, @Valid @RequestBody ProdutoRequest produtoAtualizado) {
        return ResponseEntity.ok(service.atualizarProduto(id, produtoAtualizado));
    }   
    
    @Operation(
        summary = "Excluir produto",
        description = "Remove um produto existente pelo identificador informado."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Produto removido com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Produto não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        service.deletarProduto(id);

        return ResponseEntity.noContent().build();
    }
}