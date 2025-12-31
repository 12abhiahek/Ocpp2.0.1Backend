package com.ocpp201.websocket.websockets201.service;

import com.ocpp201.websocket.websockets201.model.Reservation;
import com.ocpp201.websocket.websockets201.repository.ReservationRepository;
import com.ocpp201.websocket.websockets201.util.ReservationStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository repo;
    private final DeviceManagementService deviceService;

    // ===============================
    // RESERVE NOW
    // ===============================
    @Transactional
    public Reservation reserveNow(
            String cpId,
            Integer connectorPk,
            String idTag,
            int expiryMinutes
    ) {

        //  Save reservation
        Reservation reservation = new Reservation();
        reservation.setChargeBoxId(cpId);
        reservation.setConnectorPk(connectorPk);
        reservation.setIdTag(idTag);
        reservation.setStartDatetime(Instant.now());
        reservation.setExpiryDatetime(
                Instant.now().plusSeconds(expiryMinutes * 60L)
        );
        reservation.setStatus(ReservationStatus.ACCEPTED);

        repo.save(reservation);

        //  Send OCPP ReserveNow
        Map<String, Object> payload = Map.of(
                "reservationId", reservation.getReservationPk(),
                "evseId", connectorPk,
                "idToken", Map.of(
                        "idToken", idTag,
                        "type", "Central"
                ),
                "expiryDateTime", reservation.getExpiryDatetime().toString()
        );

        deviceService.send(cpId, "ReserveNow", payload);

        log.info(
                "[RESERVE][OUT] cp={} evse={} resId={}",
                cpId, connectorPk, reservation.getReservationPk()
        );
        return reservation;

    }

    // ===============================
    // CANCEL RESERVATION
    // ===============================
    @Transactional
    public Reservation cancel(
            String cpId,
            Long reservationPk
    ) {
        Reservation reservation =
                repo.findById(reservationPk)
                        .orElseThrow();

        reservation.setStatus(ReservationStatus.CANCELLED);
        repo.save(reservation);

        deviceService.send(
                cpId,
                "CancelReservation",
                Map.of("reservationId", reservationPk)
        );

        log.info(
                "[RESERVE][CANCEL] cp={} resId={}",
                cpId, reservationPk
        );
        return reservation;
    }

}
