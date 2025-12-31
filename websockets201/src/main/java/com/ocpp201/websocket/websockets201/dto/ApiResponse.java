package com.ocpp201.websocket.websockets201.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ApiResponse <T>{
    private Instant timestamp;
    private String status;   // SUCCESS / FAILED
    private String message;
    private T data;
}
