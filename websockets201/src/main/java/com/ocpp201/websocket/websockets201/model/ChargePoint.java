package com.ocpp201.websocket.websockets201.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

//@Data
//@Entity
//@Table(name = "charge_points")
//public class ChargePoint {
//    @Id
//    private String id;
//    private String status;         // Available, Charging, Faulted, etc.
//    private String vendor;
//    private String model;
//    private Instant lastHeartbeat;
//}


@Data
@Entity
@Table(name = "charge_box")
public class ChargePoint {

    // Internal DB PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charge_box_pk")
    private Integer id;

    // OCPP Charge Point ID (used in WebSocket URL)
    @Column(name = "charge_box_id", unique = true, nullable = false)
    private String chargeBoxId;

    @Column(name = "charge_point_vendor")
    private String vendor;

    @Column(name = "charge_point_model")
    private String model;

    @Column(name = "last_heartbeat_timestamp")
    private Instant lastHeartbeat;

    @Column(name = "registration_status")
    private String registrationStatus; // Accepted / Rejected / Pending

    @Column(name = "status")
    private String status; // Available, Charging, Faulted

//    // ---- OCPP CONFIG ----
//    @Column(name = "ocpp_protocol")
//    private String ocppProtocol; // OCPP1.6 / OCPP2.0.1
//
//    @Column(name = "endpoint_address")
//    private String endpointAddress;
}
