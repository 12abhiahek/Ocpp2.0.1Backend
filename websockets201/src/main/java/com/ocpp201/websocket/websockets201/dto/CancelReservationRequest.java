package com.ocpp201.websocket.websockets201.dto;

import lombok.Data;

@Data
public class CancelReservationRequest {
    private Long reservationPk;
}
