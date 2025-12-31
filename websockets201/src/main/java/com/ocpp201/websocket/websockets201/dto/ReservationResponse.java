package com.ocpp201.websocket.websockets201.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ReservationResponse {
    private Long reservationPk;
    private String chargeBoxId;
    private Integer connectorPk;
    private String status;
    private Instant expiryDatetime;
}
