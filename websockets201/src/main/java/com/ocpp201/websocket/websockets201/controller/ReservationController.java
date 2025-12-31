package com.ocpp201.websocket.websockets201.controller;

import com.ocpp201.websocket.websockets201.dto.ApiResponse;
import com.ocpp201.websocket.websockets201.dto.ReservationResponse;
import com.ocpp201.websocket.websockets201.model.Reservation;
import com.ocpp201.websocket.websockets201.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

//    @PostMapping("/reserve")
//    public ResponseEntity<?> reserve(
//            @RequestParam String chargeBoxId,
//            @RequestParam Integer connectorPk,
//            @RequestParam String idTag,
//            @RequestParam int expiryMinutes
//    ) {
//        service.reserveNow(
//                chargeBoxId,
//                connectorPk,
//                idTag,
//                expiryMinutes
//        );
//        return ResponseEntity.ok("Reservation sent");
//    }
//
//    @PostMapping("/cancel")
//    public ResponseEntity<?> cancel(
//            @RequestParam String chargeBoxId,
//            @RequestParam Long reservationPk
//    ) {
//        service.cancel(chargeBoxId, reservationPk);
//        return ResponseEntity.ok("Reservation cancelled");
//    }




    // ===============================
    // RESERVE NOW
    // ===============================
    @PostMapping("/reserve")
    public ResponseEntity<ApiResponse<ReservationResponse>> reserve(
            @RequestParam String chargeBoxId,
            @RequestParam Integer connectorPk,
            @RequestParam String idTag,
            @RequestParam int expiryMinutes
    ) {

        Reservation reservation =
                service.reserveNow(
                        chargeBoxId,
                        connectorPk,
                        idTag,
                        expiryMinutes
                );

        ReservationResponse response =
                ReservationResponse.builder()
                        .reservationPk(reservation.getReservationPk())
                        .chargeBoxId(reservation.getChargeBoxId())
                        .connectorPk(reservation.getConnectorPk())
                        .status(reservation.getStatus().name())
                        .expiryDatetime(
                                reservation.getExpiryDatetime()
                        )
                        .build();

        return ResponseEntity.ok(
                ApiResponse.<ReservationResponse>builder()
                        .status("SUCCESS")
                        .message("Reservation created and sent to charge point")
                        .data(response)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    // ===============================
    // CANCEL RESERVATION
    // ===============================
    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse<ReservationResponse>> cancel(
            @RequestParam String chargeBoxId,
            @RequestParam Long reservationPk
    ) {

        Reservation reservation =
                service.cancel(chargeBoxId, reservationPk);

        return ResponseEntity.ok(
                ApiResponse.<ReservationResponse>builder()
                        .status("SUCCESS")
                        .message("Reservation cancelled")
                        .data(
                                ReservationResponse.builder()
                                        .reservationPk(
                                                reservation.getReservationPk()
                                        )
                                        .chargeBoxId(
                                                reservation.getChargeBoxId()
                                        )
                                        .connectorPk(
                                                reservation.getConnectorPk()
                                        )
                                        .status(
                                                reservation.getStatus().name()
                                        )
                                        .expiryDatetime(
                                                reservation.getExpiryDatetime()
                                        )
                                        .build()
                        )
                        .build()
        );
    }

}
