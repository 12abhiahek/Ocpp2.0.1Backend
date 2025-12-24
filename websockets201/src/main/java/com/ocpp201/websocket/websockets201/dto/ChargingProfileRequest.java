package com.ocpp201.websocket.websockets201.dto;

import lombok.Data;

@Data
public class ChargingProfileRequest {

    // Context
    private String chargeBoxId;
    private Integer evseId;

    // Profile selection (USER CHOOSES)
    private String chargingProfilePurpose;   // TxProfile, ChargePointMaxProfile, DefaultProfile
    private String chargingProfileKind;      // Absolute, Relative, Recurring

    // Power control (USER CHOOSES)
    private Integer limit;                   // watts or amps
    private String chargingRateUnit;         // W / A

    // Required only for TxProfile
    private Long transactionId;
}
