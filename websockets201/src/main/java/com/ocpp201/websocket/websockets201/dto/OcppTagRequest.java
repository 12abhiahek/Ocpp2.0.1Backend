package com.ocpp201.websocket.websockets201.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OcppTagRequest {
    private String idTag;
    private String status;        // Accepted / Blocked / Expired / Invalid
    private String tagType;       // RFID / APP / ISO15118
    private LocalDateTime expiryDate;
}
