package com.ocpp201.websocket.websockets201.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class NotifyEventRequest {

    private String eventType;     // Error / Warning / Info
    private Integer severity;     // 0–9
    private String component;
    private String description;
    private Instant timestamp;
}
