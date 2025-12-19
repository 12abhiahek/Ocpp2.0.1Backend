package com.ocpp201.websocket.websockets201.service;


import com.ocpp201.websocket.websockets201.dto.MeterValueHeader;
import com.ocpp201.websocket.websockets201.model.MeterValue;
import com.ocpp201.websocket.websockets201.repository.MeterValueRepository;
import com.ocpp201.websocket.websockets201.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class MeterValueService {
//
//
//
////
////    private final MeterValueRepository repo;
////
////    public void saveMeterValues(String cpId, String txId, JsonNode meterValueArray) {
////        if (txId == null) {
////            log.warn("MeterValues ignored: transactionId is null for cpId={}", cpId);
////            return;
////        }
////
////        if (meterValueArray == null || !meterValueArray.isArray()) {
////            log.error("Invalid MeterValue payload for cpId={} txId={}", cpId, txId);
////            return;
////        }
////
////        int count = 0;
////
////        for (JsonNode mv : meterValueArray) {
////            if (!mv.has("timestamp")) continue;
////
////            Instant timestamp = Instant.parse(mv.get("timestamp").asText());
////
////            JsonNode sampled = mv.get("sampledValue");
////            if (sampled == null || !sampled.isArray()) continue;
////
////            for (JsonNode sample : sampled) {
////                if (!sample.has("value")) continue;
////
////                MeterValue record = new MeterValue();
////                record.setCpId(cpId);
////                record.setTransactionId(txId);
////                record.setTimestamp(timestamp);
////
////                Double numericValue = Double.parseDouble(sample.get("value").asText());
////                String measurand = sample.has("measurand")
////                        ? sample.get("measurand").asText()
////                        : "Unknown";
////
////                switch (measurand) {
////                    case "Voltage" ->
////                            record.setVoltage(numericValue);
////                    case "Current.Import" ->
////                            record.setCurrent(numericValue);
////                    case "Power.Active.Import" ->
////                            record.setPower(numericValue);
////                    case "Energy.Active.Import.Register" ->
////                            record.setEnergy(numericValue);
////                    default -> {
////                        // unknown measurand – you can skip saving if you want
////                        log.debug("Ignoring unknown measurand {} for tx {}", measurand, txId);
////                    }
////                }
////
////                repo.save(record);
////                count++;
////            }
////        }
////
////        log.info("Saved {} meter samples for CP={} TX={}", count, cpId, txId);
////    }
////
//
//
//
//    private final MeterValueRepository repo;
//
//    public void saveMeterHeader(Long txId, String evseId, MeterValueHeader header) {
//
//        if (header == null || header.getSampledValue() == null) {
//            log.warn("Empty meterValue header received.");
//            return;
//        }
//
//        LocalDateTime timestamp = LocalDateTime.ofInstant(
//                header.getTimestamp(),
//                ZoneId.systemDefault()
//        );
//
//        header.getSampledValue().forEach(sample -> {
//
//            try {
//                Double numericValue = Double.valueOf(sample.getValue());
//
//                MeterValue mv = new MeterValue();
//                mv.setTransactionId(txId);
//                mv.setEvseId(evseId);
//                mv.setTimestamp(timestamp);
//                mv.setMeterValue(numericValue);
//
//                repo.save(mv);
//
//                log.debug("Saved OCPP2.0.1 meterValue: tx={} evse={} value={} Wh",
//                        txId, evseId, numericValue);
//
//            } catch (Exception ex) {
//                log.error("Invalid meterValue '{}': {}", sample.getValue(), ex.getMessage());
//            }
//        });
//    }
//
//
//
//}



//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class MeterValueService {
//
//    private final MeterValueRepository repo;
//
//    public void saveMeterHeader(
//            Long transactionId,
//            String evseId,
//            MeterValueHeader header
//    ) {
//
//        if (transactionId == null) {
//            log.warn("Skipping meter value: transactionId is null");
//            return;
//        }
//
//        if (header == null || header.getSampledValue() == null) {
//            log.warn("Empty MeterValue header received");
//            return;
//        }
//
//        LocalDateTime timestamp = LocalDateTime.ofInstant(
//                header.getTimestamp(),
//                ZoneId.systemDefault()
//        );
//
//        Integer connectorPk = Integer.valueOf(evseId); // direct mapping
//
//        for (MeterValueHeader.SampledValue sample : header.getSampledValue()) {
//            try {
//                MeterValue mv = new MeterValue();
//                mv.setId(System.nanoTime()); // manual ID
//                mv.setTransactionId(transactionId);
//                mv.setEvseId(connectorPk);
//                mv.setTimestamp(timestamp);
//                mv.setMeterValue(sample.getValue()); // store as TEXT
//
//                repo.save(mv);
//
//                log.debug(
//                        "Saved meter value → tx={} evse={} value={}",
//                        transactionId, evseId, sample.getValue()
//                );
//
//            } catch (Exception ex) {
//                log.error("Invalid meter value {}", sample.getValue(), ex);
//            }
//        }
//    }
//}



//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class MeterValueService {
//
//    private final MeterValueRepository repo;
//    private final ConnectorService connectorService;
//
//    public void saveMeterHeaders(
//            Long transactionId,
//            String chargeBoxId,
//            Integer evseId,
//            JsonNode meterValueArray,
//            ObjectMapper mapper
//    ) {
//
//        if (transactionId == null || meterValueArray == null || !meterValueArray.isArray()) {
//            return;
//        }
//
//        Integer connectorPk =
//                connectorService.resolveConnectorPk(chargeBoxId, evseId);
//
//        for (JsonNode mvNode : meterValueArray) {
//            try {
//                MeterValueHeader header =
//                        mapper.treeToValue(mvNode, MeterValueHeader.class);
//
//                LocalDateTime ts = LocalDateTime.ofInstant(
//                        header.getTimestamp(),
//                        ZoneId.systemDefault()
//                );
//
//                header.getSampledValue().forEach(sample -> {
//                    MeterValue mv = new MeterValue();
//                    mv.setId(System.nanoTime());
//                    mv.setTransactionId(transactionId);
//                    mv.setConnectorPk(connectorPk);
//                    mv.setTimestamp(ts);
//                    mv.setMeterValue(sample.getValue());
//
//                    repo.save(mv);
//                });
//
//            } catch (Exception ex) {
//                log.error("MeterValue parse error", ex);
//            }
//        }
//    }
//}



@Slf4j
@Service
@RequiredArgsConstructor
public class MeterValueService {

    private final MeterValueRepository repo;
    private final ConnectorService connectorService;

    public void saveMeterHeaders(
            Long transactionId,
            String chargeBoxId,
            Integer evseId,
            JsonNode meterValueArray,
            ObjectMapper mapper
    ) {

        // ---------------- VALIDATION ----------------
        if (transactionId == null) {
            log.warn("[MV][SKIP] transactionId is null, cp={}", chargeBoxId);
            return;
        }

        if (meterValueArray == null || !meterValueArray.isArray() || meterValueArray.isEmpty()) {
            log.debug("[MV][SKIP] No meterValue array txId={}", transactionId);
            return;
        }

        log.info(
                "[MV][IN] Processing meter values txId={} cp={} evse={}",
                transactionId, chargeBoxId, evseId
        );

        // ---------------- CONNECTOR RESOLUTION ----------------
        Integer connectorPk;
        try {
            connectorPk = connectorService.resolveConnectorPk(chargeBoxId, evseId);
            log.debug(
                    "[MV][OK] Resolved connectorPk={} for cp={} evse={}",
                    connectorPk, chargeBoxId, evseId
            );
        } catch (Exception ex) {
            log.error(
                    "[MV][ERR] Failed to resolve connector cp={} evse={}",
                    chargeBoxId, evseId, ex
            );
            return;
        }

        // ---------------- PROCESS HEADERS ----------------
        for (JsonNode mvNode : meterValueArray) {
            try {
                MeterValueHeader header =
                        mapper.treeToValue(mvNode, MeterValueHeader.class);

                if (header.getTimestamp() == null) {
                    log.warn(
                            "[MV][SKIP] Missing timestamp txId={}",
                            transactionId
                    );
                    continue;
                }

                if (header.getSampledValue() == null || header.getSampledValue().isEmpty()) {
                    log.debug(
                            "[MV][SKIP] Empty sampledValue txId={}",
                            transactionId
                    );
                    continue;
                }

                LocalDateTime timestamp = LocalDateTime.ofInstant(
                        header.getTimestamp(),
                        ZoneId.systemDefault()
                );

                // ---------------- PROCESS SAMPLES ----------------
                header.getSampledValue().forEach(sample -> {

                    if (sample.getValue() == null) {
                        log.debug(
                                "[MV][SKIP] Sample without value txId={}",
                                transactionId
                        );
                        return;
                    }

                    try {
                        MeterValue mv = new MeterValue();
                        mv.setId(System.nanoTime());
                        mv.setTransactionId(transactionId);
                        mv.setConnectorPk(connectorPk);
                        mv.setTimestamp(timestamp);
                        mv.setMeterValue(sample.getValue());

                        repo.save(mv);

                        log.debug(
                                "[MV][DB] Saved meterValue txId={} connectorPk={} value={}",
                                transactionId, connectorPk, sample.getValue()
                        );

                    } catch (Exception dbEx) {
                        log.error(
                                "[MV][DB_ERR] Failed to save meterValue txId={} value={}",
                                transactionId, sample.getValue(), dbEx
                        );
                    }
                });

            } catch (Exception parseEx) {
                log.error(
                        "[MV][PARSE_ERR] Invalid meterValue header txId={}",
                        transactionId, parseEx
                );
            }
        }

        log.info(
                "[MV][DONE] MeterValues processed txId={} cp={}",
                transactionId, chargeBoxId
        );
    }
}
