package com.ocpp201.websocket.websockets201.model;


import com.ocpp201.websocket.websockets201.repository.ReservationRepository;
import com.ocpp201.websocket.websockets201.service.DeviceManagementService;
import com.ocpp201.websocket.websockets201.util.ReservationStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class ReservationExpiryJob {

    private final ReservationRepository repo;
    private final DeviceManagementService deviceService;

    @Scheduled(fixedRate = 60000) // every 1 minute
    @Transactional
    public void expireReservations() {

        Instant now = Instant.now();

        List<Reservation> expired =
                repo.findExpired(now);

        for (Reservation r : expired) {
            r.setStatus(ReservationStatus.EXPIRED);
            log.info(
                    "[RESERVE][EXPIRED] reservationPk={} connector={}",
                    r.getReservationPk(),
                    r.getConnectorPk()
            );

            //  SEND OCPP NotifyEvent
            deviceService.sendNotifyEvent(
                    r.getChargeBoxId(),  // see note below
                    "ReservationExpired",
                    "Medium",
                    "Reservation",
                    "Reservation expired for connector "
                            + r.getConnectorPk()
                            + ", reservationId="
                            + r.getReservationPk()
            );
        }

        repo.saveAll(expired);
    }

    /**
     * Resolve chargeBoxId for reservation
     * (depends on your schema)
     */
    private String resolveChargePointId(Reservation r) {
        // if connectorPk maps 1-to-1 with chargeBoxId:
        return r.getChargeBoxId();
    }
}
