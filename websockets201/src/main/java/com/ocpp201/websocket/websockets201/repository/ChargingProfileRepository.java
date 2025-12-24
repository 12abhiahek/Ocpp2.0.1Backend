package com.ocpp201.websocket.websockets201.repository;

import com.ocpp201.websocket.websockets201.model.ChargingProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChargingProfileRepository extends JpaRepository<ChargingProfile, Long> {



//    @Query("""
//        SELECT cp FROM ChargingProfile cp
//        WHERE cp.chargingProfilePurpose = :purpose
//        AND (cp.validFrom IS NULL OR cp.validFrom <= CURRENT_TIMESTAMP)
//        AND (cp.validTo IS NULL OR cp.validTo >= CURRENT_TIMESTAMP)
//        ORDER BY cp.stackLevel DESC
//        """)
//    Optional<ChargingProfile> findActiveProfiles(
//            @Param("profileId") String profileId
//    );




    // For ChargePointMaxProfile / DefaultProfile
    @Query("""
        SELECT cp FROM ChargingProfile cp
        WHERE cp.chargingProfilePurpose = :purpose
        AND (cp.validFrom IS NULL OR cp.validFrom <= CURRENT_TIMESTAMP)
        AND (cp.validTo IS NULL OR cp.validTo >= CURRENT_TIMESTAMP)
        ORDER BY cp.stackLevel DESC
        """)
    List<ChargingProfile> findActiveProfilesByPurpose(
            @Param("purpose") String purpose
    );

    // For TxProfile clear / lookup
    Optional<ChargingProfile> findByProfileId(String profileId);


    List<ChargingProfile> findByChargingProfilePurpose(
            String chargingProfilePurpose
    );

}
