//package com.ocpp201.websocket.websockets201.repository;
//
//import com.ocpp201.websocket.websockets201.model.ChargingProfile;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface ChargingProfileRepository extends JpaRepository<ChargingProfile, Long> {
//
//    @Query("""
//        SELECT cp FROM ChargingProfileEntity cp
//        WHERE cp.chargingProfilePurpose = :purpose
//        AND (cp.validFrom IS NULL OR cp.validFrom <= CURRENT_TIMESTAMP)
//        AND (cp.validTo IS NULL OR cp.validTo >= CURRENT_TIMESTAMP)
//        ORDER BY cp.stackLevel DESC
//        """)
//    List<ChargingProfile> findActiveProfiles(String purpose);
//}
