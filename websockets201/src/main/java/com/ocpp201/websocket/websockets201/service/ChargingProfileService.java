//package com.ocpp201.websocket.websockets201.service;
//
//import com.ocpp201.websocket.websockets201.model.ChargingProfile;
//import com.ocpp201.websocket.websockets201.repository.ChargingProfileRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ChargingProfileService {
//
//    private final ChargingProfileRepository chargingProfileRepository;
//
////    public ChargingProfile getActiveTxProfile() {
////        return repo.findActiveProfiles("TxProfile")
////                .stream()
////                .findFirst()
////                .orElse(null);
////    }
////
////    public ChargingProfile getChargePointMaxProfile() {
////        return chargingProfileRepository.findActiveProfiles("ChargePointMaxProfile")
////                .stream()
////                .findFirst()
////                .orElse(null);
////    }
//
//
//
//
//    // OCPP priority logic
//    public ChargingProfile getProfileForTransaction() {
//
//        ChargingProfile txProfile =
//                chargingProfileRepository.findActiveProfiles("TxProfile")
//                        .stream().findFirst().orElse(null);
//
//        if (txProfile != null) return txProfile;
//
//        return chargingProfileRepository.findActiveProfiles("ChargePointMaxProfile")
//                .stream().findFirst().orElse(null);
//    }
//
//}
