package com.ocpp201.websocket.websockets201.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "charging_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargingProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charging_profile_pk")
    private Long id;

    @Column(name = "charging_profile_id", nullable = false)
    private String profileId; // OCPP profile id

    @Column(name = "stack_level", nullable = false)
    private Integer stackLevel;

    @Column(name = "charging_profile_purpose", nullable = false)
    private String chargingProfilePurpose;   // TxProfile, ChargePointMaxProfile, DefaultProfile

    @Column(name = "charging_profile_kind", nullable = false)
    private String chargingProfileKind;      // Absolute, Relative, Recurring

    @Column(name = "recurrence_kind")
    private String recurrenceKind;

    @Column(name = "valid_from")
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @Column(name = "duration_in_seconds")
    private Integer durationInSeconds;

    @Column(name = "charging_rate_unit", nullable = false)
    private String chargingRateUnit;

    @Column(name = "min_charging_rate", nullable = false)
    private BigDecimal minChargingRate;

    @Column(name = "start_schedule")
    private Instant startSchedule;

//    @OneToMany(mappedBy = "profile",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true,
//            fetch = FetchType.EAGER)
//
//    private List<ChargingSchedule> schedules =new ArrayList<>();


    /** IMPORTANT FIX */
    @OneToMany(mappedBy = "profile",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<ChargingSchedulePeriod> schedulePeriods = new ArrayList<>();
}
