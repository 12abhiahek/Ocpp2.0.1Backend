package com.ocpp201.websocket.websockets201.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
        name = "device_variable",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"charge_box_id", "component", "variable"}
        )
)
@Getter
@Setter
public class DeviceVariable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chargeBoxId;
    private String component;
    private String variable;
    private String value;
    private String attribute; // Actual / Target / Min / Max

    private Instant updatedAt = Instant.now();
}
