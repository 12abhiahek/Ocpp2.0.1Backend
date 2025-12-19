package com.ocpp201.websocket.websockets201.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

//@Data
//@Entity
//@Table(name = "meter_values")
//@Getter
//@Setter
//@NoArgsConstructor
//public class MeterValue {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Long transactionId;
//
//    private String evseId;
//
//    private LocalDateTime timestamp;
//
//    private Double meterValue; // numeric Wh value
//
//
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//}


@Data
@Entity
@Table(name = "connector_meter_value")
@NoArgsConstructor
public class MeterValue {

    @Id
    @Column(name = "id")
    private Long id;   // existing column (no auto-generate assumption)

    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    // using connector_pk to store EVSE id
    @Column(name = "connector_pk", nullable = false)
    private Integer connectorPk;

    @Column(name = "value_timestamp", nullable = false)
    private LocalDateTime timestamp;

    // meter value stored as TEXT
    @Column(name = "value", columnDefinition = "TEXT")
    private String meterValue;

    // not in DB
    @Transient
    private LocalDateTime createdAt = LocalDateTime.now();
}
