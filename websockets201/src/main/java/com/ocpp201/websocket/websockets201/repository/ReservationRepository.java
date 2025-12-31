package com.ocpp201.websocket.websockets201.repository;

import com.ocpp201.websocket.websockets201.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation>
    findFirstByConnectorPkAndStatus(
            Integer connectorPk,
            String status
    );

    List<Reservation>
    findByStatus(String status);


    @Query("""
        SELECT r FROM Reservation r
        WHERE r.status = 'ACCEPTED'
        AND r.expiryDatetime < :now
    """)
    List<Reservation> findExpired(Instant now);
}
