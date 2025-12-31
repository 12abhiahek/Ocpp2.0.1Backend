package com.ocpp201.websocket.websockets201.dto;

import lombok.Data;

@Data
public class ReserveNowRequest {
    private Integer connectorPk;
    private String idTag;
    private int expiryMinutes;

}
