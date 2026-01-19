package com.ocpp201.websocket.websockets201.repository;


import com.ocpp201.websocket.websockets201.model.MeterValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeterValueRepository extends JpaRepository<MeterValue, Long> {

    //  All meter values for transaction
    List<MeterValue>
    findByTransactionIdOrderByTimestampAsc(Long transactionId);

    //  Latest value (live view)
    Optional<MeterValue>
    findTopByTransactionIdOrderByTimestampDesc(Long transactionId);

    //  Energy-only values
    @Query("""
        SELECT m FROM MeterValue m
        WHERE m.transactionId = :transactionId
          AND m.measurand = 'Energy.Active.Import.Register'
        ORDER BY m.timestamp ASC
    """)
    List<MeterValue> findEnergyValues(
            @Param("transactionId") Long transactionId
    );

    //  Start & End energy
    @Query("""
        SELECT m FROM MeterValue m
        WHERE m.transactionId = :transactionId
          AND m.measurand = 'Energy.Active.Import.Register'
          AND m.readingContext IN ('Transaction.Begin', 'Transaction.End')
        ORDER BY m.timestamp ASC
    """)
    List<MeterValue> findStartAndEndEnergy(
            @Param("transactionId") Long transactionId
    );

    //  DB-side energy calculation
    @Query("""
        SELECT
          MAX(CAST(m.meterValue AS double)) -
          MIN(CAST(m.meterValue AS double))
        FROM MeterValue m
        WHERE m.transactionId = :transactionId
          AND m.measurand = 'Energy.Active.Import.Register'
    """)
    Double calculateConsumedEnergy(
            @Param("transactionId") Long transactionId
    );

//    Optional<MeterValue>
//    findTopByTransactionIdOrderByValueTimestampDesc(Long transactionId);
}
