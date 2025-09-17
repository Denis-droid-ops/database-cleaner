package com.kuznetsov.databasecleaner.exceptionHandler;

import com.kuznetsov.databasecleaner.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder stringBuilder = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                stringBuilder.append("field: " + error.getField()
                        + " error: " + error.getDefaultMessage())
        );
        return new ErrorResponse(stringBuilder.toString(), Instant.now());
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleDatabaseException(DataAccessException ex) {
        log.error("Ошибка работы с базой: {}", ex.getMessage(), ex);
        return new ErrorResponse("Ошибка работы с базой: " + ex.getMessage(), Instant.now());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedException(RuntimeException ex) {
        log.error("Внутренняя ошибка сервера: {}", ex.getMessage(), ex);
        return new ErrorResponse("Внутренняя ошибка сервера: " + ex.getMessage(), Instant.now());
    }
}
