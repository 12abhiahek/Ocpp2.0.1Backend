package com.ocpp201.websocket.websockets201.repository;

import com.ocpp201.websocket.websockets201.model.Connector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConnectorRepository extends JpaRepository<Connector, Integer> {

    Optional<Connector> findByChargeBoxIdAndConnectorId(
            String chargeBoxId,
            Integer connectorId // evseId
    );

}
