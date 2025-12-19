package com.ocpp201.websocket.websockets201.repository;


import com.ocpp201.websocket.websockets201.model.ChargePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChargePointRepository extends JpaRepository<ChargePoint, Integer> {


    Optional<ChargePoint> findByChargeBoxId(String chargeBoxId);
}
