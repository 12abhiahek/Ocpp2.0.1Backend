package com.ocpp201.websocket.websockets201.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "connector")
@Getter
@Setter
@NoArgsConstructor
public class Connector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "connector_pk")
    private Integer connectorPk;

    @Column(name = "charge_box_id", nullable = false)
    private String chargeBoxId;   // CP id (e.g. ch015)

    @Column(name = "connector_id", nullable = false)
    private Integer connectorId;  // 1,2,3...

    @Column(name = "kw")
    private BigDecimal kw;

    @Column(name = "volt")
    private BigDecimal volt;

    @Column(name = "connectorType")
    private String connectorType;

    @Column(name = "connectorName")
    private String connectorName;
}
