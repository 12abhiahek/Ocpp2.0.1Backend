package com.ocpp201.websocket.websockets201.repository;

import com.ocpp201.websocket.websockets201.model.DeviceVariable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceVariableRepository extends JpaRepository<DeviceVariable, Long> {

    // Fetch all variables of a charge point
    List<DeviceVariable> findByChargeBoxId(String chargeBoxId);

    // Fetch a specific variable (used for upsert)
    Optional<DeviceVariable> findByChargeBoxIdAndComponentAndVariable(
            String chargeBoxId,
            String component,
            String variable
    );
}
