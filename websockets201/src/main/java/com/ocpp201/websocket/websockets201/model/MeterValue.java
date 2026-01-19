package com.ocpp201.websocket.websockets201.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;


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

    @Column(name = "reading_context")
    private String readingContext;

    @Column(name = "format")
    private String format;

    @Column(name = "measurand")
    private String measurand;

    @Column(name = "location")
    private String location;

    @Column(name = "unit")
    private String unit;

    @Column(name = "phase")
    private String phase;

    // not in DB
    @Transient
    private LocalDateTime createdAt = LocalDateTime.now();
}
