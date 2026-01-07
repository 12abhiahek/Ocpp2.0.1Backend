package com.ocpp201.websocket.websockets201.exception;

import com.ocpp201.websocket.websockets201.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(
            IllegalArgumentException ex
    ) {
        return ResponseEntity.badRequest().body(
                ApiResponse.<Void>builder()
                        .status("FAILED")
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleConflict(
            IllegalStateException ex
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse.<Void>builder()
                        .status("FAILED")
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(
            Exception ex
    ) {
        return ResponseEntity.internalServerError().body(
                ApiResponse.<Void>builder()
                        .status("FAILED")
                        .message("Internal server error")
                        .build()
        );
    }




}
