package com.ocpp201.websocket.websockets201.service;

import com.ocpp201.websocket.websockets201.repository.ReservationRepository;
import com.ocpp201.websocket.websockets201.util.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationEnforcementService {

   // private final ReservationRepository repo;

//    public boolean isAllowed(
//            Integer connectorPk,
//            String idTag
//    ) {
//        return repo
//                .findFirstByConnectorPkAndStatus(
//                        connectorPk, "ACCEPTED"
//                )
//                .map(r -> r.getIdTag().equals(idTag))
//                .orElse(true); // no reservation → allowed
//    }

    private final ReservationRepository repo;

    public boolean isAllowed(
            Integer connectorPk,
            String idTag
    ) {

        return repo
                .findFirstByConnectorPkAndStatus(
                        connectorPk,
                        String.valueOf(ReservationStatus.ACCEPTED)
                )
                .map(r -> {

                    if (r.isExpired()) {
                        r.setStatus(ReservationStatus.EXPIRED);
                        repo.save(r);
                        return true;
                    }

                    return r.getIdTag().equals(idTag);
                })
                .orElse(true);
    }
}
