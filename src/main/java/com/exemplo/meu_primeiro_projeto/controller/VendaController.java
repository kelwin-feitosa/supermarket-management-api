package com.exemplo.meu_primeiro_projeto.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.meu_primeiro_projeto.dto.VendaResponse;
import com.exemplo.meu_primeiro_projeto.exception.RespostaErro;
import com.exemplo.meu_primeiro_projeto.service.VendaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/vendas")
@Tag(
    name = "Venda",
    description = "Operações relacionadas ao gerenciamento de vendas."
)
public class VendaController {

    private final VendaService service;

    public VendaController(VendaService service) {
        this.service = service;
    }

    @Operation(
        summary = "Realizar venda",
        description = "Finaliza uma venda utilizando os produtos presentes em um carrinho."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Venda realizada com sucesso"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Carrinho vazio ou estoque insuficiente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Carrinho não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @PostMapping("{idCarrinho}")
    public ResponseEntity<VendaResponse> realizarVenda(@PathVariable Long idCarrinho) {
        return ResponseEntity.ok(service.realizarVenda(idCarrinho));
    }

    @Operation(
        summary = "Buscar venda por ID",
        description = "Busca uma venda pelo identificador informado."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Venda encontrada"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Venda não encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @GetMapping("{idVenda}")
    public ResponseEntity<VendaResponse> buscarVenda(@PathVariable Long idVenda) {
        return ResponseEntity.ok(service.buscarVenda(idVenda));
    }

     @Operation(
        summary = "Listar vendas",
        description = "Lista todas as vendas realizadas no sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Vendas listadas com sucesso"
        )
    })
    @GetMapping 
    public ResponseEntity<List<VendaResponse>> listarVendas() {
        return ResponseEntity.ok(service.listarVendas());
    }

}
