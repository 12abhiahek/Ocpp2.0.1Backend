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
//@Table(name = "charging_profile_schedule")
//@Getter
//@Setter
//public class ChargingSchedule {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String chargingRateUnit; // W, A
//    private Double minChargingRate;
//    private Instant startSchedule;
//
//    @ManyToOne
//    @JoinColumn(name = "charging_profile_id")
//    private ChargingProfile profile;
//
//    @OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER)
//    private List<ChargingSchedulePeriod> periods;
//}
