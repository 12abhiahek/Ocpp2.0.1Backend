package com.ocpp201.websocket.websockets201.model;

import com.ocpp201.websocket.websockets201.util.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "reservation")
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_pk")
    private Long reservationPk;

    @Column(name = "connector_pk", nullable = false)
    private Integer connectorPk; // EVSE / Connector

    @Column(name = "transaction_pk")
    private Long transactionPk;

    @Column(name = "id_tag", nullable = false)
    private String idTag;

    @Column(name = "start_datetime", nullable = false)
    private Instant startDatetime;

    @Column(name = "expiry_datetime", nullable = false)
    private Instant expiryDatetime;

    @Column(name = "charge_box_id", nullable = false)
    private String chargeBoxId;


//    @Column(name = "status", nullable = false)
//    private String status;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    // helper
    public boolean isExpired() {
        return Instant.now().isAfter(expiryDatetime);
    }
}
