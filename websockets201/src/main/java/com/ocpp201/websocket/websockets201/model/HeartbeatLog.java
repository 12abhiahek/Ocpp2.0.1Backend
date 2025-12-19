package com.ocpp201.websocket.websockets201.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "heartbeat_logs")
@Getter
@Setter
public class HeartbeatLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chargePointId;

    private LocalDateTime timestamp;

    private String remoteAddress;

    public HeartbeatLog() {}

    public HeartbeatLog(String chargePointId, LocalDateTime timestamp, String remoteAddress) {
        this.chargePointId = chargePointId;
        this.timestamp = timestamp;
        this.remoteAddress = remoteAddress;
    }
}
