//package com.ocpp201.websocket.websockets201.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.Instant;
//import java.util.List;
//
//@Entity
//@Table(name = "charging_profile")
//@Getter
//@Setter
//public class ChargingProfile {
//
//    @Id
//    @Column(name = "charging_profile_pk")
//    private Long id;
//
//    private Integer stackLevel;
//    private String chargingProfilePurpose;   // TxProfile, ChargePointMaxProfile
//    private String chargingProfileKind;      // Absolute, Relative
//    private String recurrenceKind;
//
//    private Instant validFrom;
//    private Instant validTo;
//    private Integer durationInSeconds;
//
//    @OneToMany(mappedBy = "profile", fetch = FetchType.EAGER)
//    private List<ChargingSchedule> schedules;
//}
