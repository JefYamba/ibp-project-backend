package com.jefy.ibp.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.HttpStatus.*;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 10/05/2024
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException (AccessDeniedException  exception){
        return ResponseEntity.status(FORBIDDEN).body(
                ExceptionResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(FORBIDDEN)
                        .statusCode(FORBIDDEN.value())
                        .error(exception.getMessage())
                        .build()
        );
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException exception){
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .error(exception.getMessage())
                        .build()
        );
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException exception){
        return ResponseEntity.status(NOT_FOUND).body(
                ExceptionResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(NOT_FOUND)
                        .statusCode(NOT_FOUND.value())
                        .error(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(EntityNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotValidException(EntityNotValidException exception){
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .error(exception.getMessage())
                        .build()
        );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotValidException(MethodArgumentNotValidException exception){
        Set<String> errors = new HashSet<>();
        exception.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        
        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .error("form is not valid")
                        .formErrors(errors)
                        .build()
        );
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionResponse> handleIOException(IOException exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(INTERNAL_SERVER_ERROR)
                        .statusCode(INTERNAL_SERVER_ERROR.value())
                        .error(exception.getMessage())
                        .build()
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .status(INTERNAL_SERVER_ERROR)
                        .statusCode(INTERNAL_SERVER_ERROR.value())
                        .error("unknown exception : " + exception.getMessage())
                        .build()
        );
    }
}
