package com.ocpp201.websocket.websockets201.repository;


import com.ocpp201.websocket.websockets201.model.MeterValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
//public interface MeterValueRepository extends JpaRepository<MeterValue, String> {
//
//    List<MeterValue> findByTransactionId(Long txId);
//
//    List<MeterValue> findByTransactionIdOrderByTimestampAsc(Long transactionId);
//}

@Repository
public interface MeterValueRepository extends JpaRepository<MeterValue, Long> {
}
