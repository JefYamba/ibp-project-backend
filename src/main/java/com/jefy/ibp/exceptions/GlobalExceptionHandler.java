package com.jefy.ibp.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

/**
 * @Author JefYamba
 * @Email joph.e.f.yamba@gmail.com
 * @Since 10/05/2024
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OperationNotAuthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleOperationNotAuthorizedException(OperationNotAuthorizedException exception){
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

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRecordNotFoundException(RecordNotFoundException exception){
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
