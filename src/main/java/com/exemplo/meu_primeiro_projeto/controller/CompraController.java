package com.exemplo.meu_primeiro_projeto.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.meu_primeiro_projeto.dto.filter.CompraFiltro;
import com.exemplo.meu_primeiro_projeto.dto.request.CompraRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.CompraResponse;
import com.exemplo.meu_primeiro_projeto.exception.RespostaErro;
import com.exemplo.meu_primeiro_projeto.service.CompraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/compras")
@Tag(
    name = "Compra",
    description = "Operações relacionadas ao gerenciamento de compras."
)
public class CompraController {
    
    private final CompraService compraService;

    @Operation(
        summary = "Realizar compra",
        description = "Registra uma nova compra de produtos junto a um fornecedor."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Compra realizada com sucesso"
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
            description = "Fornecedor ou produto não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @PostMapping
    public ResponseEntity<CompraResponse> realizarCompra(@Valid @RequestBody CompraRequest request) {
        CompraResponse resposta = compraService.realizarCompra(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }
    
    @Operation(
        summary = "Buscar compra por ID",
        description = "Busca uma compra pelo identificador informado."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Compra encontrada"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Compra não encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CompraResponse> buscarCompra(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.buscarCompra(id));
    }

    @Operation(
        summary = "Listar compras",
        description = "Lista todas as compras registradas no sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Compras listadas com sucesso"
        )
    })
    @GetMapping
    public ResponseEntity<Page<CompraResponse>> listarCompras(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable,
            CompraFiltro filtro) {

        return ResponseEntity.ok(compraService.listarCompras(filtro, pageable));
    }
}
