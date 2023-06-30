package ru.otus.book_storage.controllers;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.book_storage.dto.ExceptionDto;
import ru.otus.book_storage.exceptions.NotFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity
                .badRequest()
                .body(errors);
    }

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<ExceptionDto> handleCallNotPermittedException(CallNotPermittedException ex) {
        log.error(ex.getMessage());
        var exceptionMessageResponse = new ExceptionDto("The called command is not working at the moment");
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(exceptionMessageResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException exception) {
        ExceptionDto exceptionDto = new ExceptionDto(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(exceptionDto);
    }
}
