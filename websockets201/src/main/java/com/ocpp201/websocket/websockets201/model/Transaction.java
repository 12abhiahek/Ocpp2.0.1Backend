package com.ocpp201.websocket.websockets201.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
//    @Id
//    private String transactionId;
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "transaction_id")
private Long transactionId;
    private String evseId;
    private String idToken;
    private Instant startTime;
    private Instant stopTime;
    private String status;

    // ---- OCPP 2.0.1 METER FIELDS ----
    private Double startMeterValue;       // Wh meter at start
    private Double stopMeterValue;        // Wh meter at end
    private Double lastKnownMeterValue;   // live meter updates

    // energy consumed in kWh
    private Double energyKwh;
}
