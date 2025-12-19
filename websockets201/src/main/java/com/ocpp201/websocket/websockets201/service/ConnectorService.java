package com.ocpp201.websocket.websockets201.service;

import com.ocpp201.websocket.websockets201.model.ChargePoint;
import com.ocpp201.websocket.websockets201.model.Connector;
import com.ocpp201.websocket.websockets201.repository.ChargePointRepository;
import com.ocpp201.websocket.websockets201.repository.ConnectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ConnectorService {

    private final ConnectorRepository connectorRepository;
    private final ChargePointRepository chargePointRepository;

    public Integer resolveConnectorPk(String chargeBoxId, Integer evseId) {

        // ensure charge box exists
        chargePointRepository.findByChargeBoxId(chargeBoxId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "ChargeBox not found: " + chargeBoxId
                        )
                );

        return connectorRepository
                .findByChargeBoxIdAndConnectorId(chargeBoxId, evseId)
                .orElseGet(() -> {
                    Connector c = new Connector();
                    c.setChargeBoxId(chargeBoxId);
                    c.setConnectorId(evseId); // evseId stored here
                    c.setConnectorName("EVSE-" + evseId);
                    return connectorRepository.save(c);
                })
                .getConnectorPk();
    }
}
