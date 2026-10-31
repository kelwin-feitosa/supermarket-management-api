package com.exemplo.meu_primeiro_projeto.shared.exception.handler;

import com.exemplo.meu_primeiro_projeto.shared.exception.BusinessRuleException;
import com.exemplo.meu_primeiro_projeto.shared.exception.ResourceConflictException;
import com.exemplo.meu_primeiro_projeto.shared.exception.ResourceNotFoundException;
import com.exemplo.meu_primeiro_projeto.shared.exception.RespostaErro;
import com.exemplo.meu_primeiro_projeto.shared.util.DataHoraUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RespostaErro> notFound(ResourceNotFoundException ex) {
        return resposta(HttpStatus.NOT_FOUND, "Recurso não encontrado.", ex.getMessage());
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<RespostaErro> conflict(ResourceConflictException ex) {
        return resposta(HttpStatus.CONFLICT, "Conflito na operação.", ex.getMessage());
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<RespostaErro> businessRule(BusinessRuleException ex) {
        return resposta(HttpStatus.BAD_REQUEST, "Regra de negócio violada.", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaErro> validation(MethodArgumentNotValidException ex) {
        var field = ex.getBindingResult().getFieldError();
        String detail = field == null ? "Dados inválidos." : field.getDefaultMessage();
        return resposta(HttpStatus.BAD_REQUEST,
                "Dados enviados não passam nas regras de validação.", detail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RespostaErro> malformedJson(HttpMessageNotReadableException ex) {
        return resposta(HttpStatus.BAD_REQUEST, "Corpo da requisição inválido (JSON malformado).",
                "Certifique-se de que o JSON enviado está com a sintaxe correta.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespostaErro> generic(Exception ex) {
        return resposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.",
                "Ocorreu uma falha inesperada no backend.");
    }

    private ResponseEntity<RespostaErro> resposta(HttpStatus status, String title, String detail) {
        return ResponseEntity.status(status)
                .body(new RespostaErro(title, detail, DataHoraUtil.agora()));
    }
}
