//package com.ocpp201.websocket.websockets201.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table(name = "charging_schedule_period")
//@Getter
//@Setter
//public class ChargingSchedulePeriod {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Integer startPeriod;  // seconds
//    private Integer limit;        // watts or amps
//
//    @ManyToOne
//    @JoinColumn(name = "schedule_id")
//    private ChargingSchedule schedule;
//}
