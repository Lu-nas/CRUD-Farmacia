package com.farmacia.crudfarmacia.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex,         // falha de contrato
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();       // consumo por front-end

        ex.getBindingResult()                           //resultado do processo
          .getFieldErrors()
          .forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(),
         "Erro de validação","Existem campos invalidos", errors, request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(apiError);
    }
}
