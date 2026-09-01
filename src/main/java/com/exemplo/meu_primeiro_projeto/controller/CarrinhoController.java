package com.exemplo.meu_primeiro_projeto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.meu_primeiro_projeto.dto.request.ItemCarrinhoRequest;
import com.exemplo.meu_primeiro_projeto.dto.response.CarrinhoResponse;
import com.exemplo.meu_primeiro_projeto.exception.RespostaErro;
import com.exemplo.meu_primeiro_projeto.service.CarrinhoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carrinhos")
@Tag(
    name = "Carrinho",
    description = "Operações relacionadas ao gerenciamento de carrinhos."
)
public class CarrinhoController {

    private final CarrinhoService service;

    @Operation(
        summary = "Adicionar item ao carrinho",
        description = "Adiciona um produto ao carrinho informado."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Item adicionado ao carrinho com sucesso"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos ou estoque insuficiente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Carrinho ou produto não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @PostMapping("/itens")
    public ResponseEntity<CarrinhoResponse> adicionarItem(@Valid @RequestBody ItemCarrinhoRequest request) {
        return ResponseEntity.ok(service.adicionarItem(request));
    }

    @Operation(
        summary = "Alterar quantidade de item",
        description = "Atualiza a quantidade de um produto já existente no carrinho."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Quantidade atualizada com sucesso"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Quantidade inválida ou estoque insuficiente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Carrinho ou item não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @PutMapping("/itens")
    public ResponseEntity<CarrinhoResponse> alterarItem(@Valid @RequestBody ItemCarrinhoRequest request) {
        return ResponseEntity.ok(service.alterarQuantidade(request));
    }

    @Operation(
        summary = "Remover item do carrinho",
        description = "Remove um produto existente do carrinho."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Item removido com sucesso"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Carrinho ou item não encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RespostaErro.class)
            )
        )
    })
    @DeleteMapping("/itens")
    public ResponseEntity<Void> removerItem(@Valid @RequestBody ItemCarrinhoRequest request) {
        service.removerItem(request);

        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Limpar carrinho",
        description = "Remove todos os itens de um carrinho existente."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Carrinho limpo com sucesso"
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
    @DeleteMapping("/{idCarrinho}")
    public ResponseEntity<CarrinhoResponse> limparCarrinho(@PathVariable Long idCarrinho) {
        CarrinhoResponse resposta = service.limparCarrinho(idCarrinho);

        return ResponseEntity.ok(resposta);
    }
}
