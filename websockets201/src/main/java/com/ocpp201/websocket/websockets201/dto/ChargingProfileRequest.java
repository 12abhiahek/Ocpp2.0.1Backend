package com.ocpp201.websocket.websockets201.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ChargingProfileRequest {

    // Context
    private String chargeBoxId;
    private Integer evseId;

    // Profile selection (USER CHOOSES)
    private String chargingProfilePurpose;   // TxProfile, ChargePointMaxProfile, DefaultProfile
    private String chargingProfileKind;      // Absolute, Relative, Recurring
    private String recurrenceKind;            // Daily / Weekly (optional)

    // Power control (USER CHOOSES)
    private Integer limit;                   // watts or amps
    private String chargingRateUnit;         // W / A

    // Required only for TxProfile
    private Long transactionId;

    private Integer stackLevel;

    // TIME FIELDS
    private Instant validFrom;
    private Instant validTo;
    private Integer durationInSeconds;
    private Instant startSchedule;
}
