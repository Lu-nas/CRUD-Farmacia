package com.farmacia.crudfarmacia.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
@ExceptionHandler(Exception.class)
public ResponseEntity<ApiError> handleAllExceptions(Exception ex, HttpServletRequest request) {

    Map<String, String> errors = new LinkedHashMap<>();

    if (ex instanceof MethodArgumentNotValidException manvEx) {
        manvEx.getBindingResult()
               .getFieldErrors()
               .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    } else if (ex instanceof ConstraintViolationException cvEx) {
        cvEx.getConstraintViolations()
            .forEach(violation -> {
                String field = violation.getPropertyPath().toString();
                errors.put(field, violation.getMessage());
            });
    } else {
        // fallback para outros tipos de erro
        errors.put("exception", ex.getMessage());
    }
        
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(),
        "Erro de validação", "Existem campos inválidos", errors,                   
        request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ApiError apiError = new ApiError(
            HttpStatus.NOT_FOUND.value(),
            "Recurso não encontrado",
            ex.getMessage(),
            null, // sem detalhes
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
}
