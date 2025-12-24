package com.ocpp201.websocket.websockets201.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

    @Entity
    @Table(name = "charging_schedule_period")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class ChargingSchedulePeriod {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "schedule_period_pk")
        private Long id;

        @Column(name = "start_period_in_seconds", nullable = false)
        private Integer startPeriodInSeconds;

        @Column(name = "start_period", nullable = false)
        private Integer startPeriod; // seconds

        @Column(name = "power_limit", nullable = false)
        private BigDecimal powerLimit; // W or A

        @Column(name = "number_phases")
        private Integer numberPhases;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "charging_profile_pk", nullable = false)
        private ChargingProfile profile;
    }
