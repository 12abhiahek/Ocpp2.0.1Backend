package com.ocpp201.websocket.websockets201.handler;
//
//
import com.ocpp201.websocket.websockets201.dto.MeterValueHeader;
import com.ocpp201.websocket.websockets201.model.OcppMessage;
import com.ocpp201.websocket.websockets201.model.Transaction;
import com.ocpp201.websocket.websockets201.repository.TransactionRepository;
//import com.ocpp201.websocket.websockets201.service.ChargingProfileService;
import com.ocpp201.websocket.websockets201.service.MeterValueService;
import com.ocpp201.websocket.websockets201.service.TransactionService;
import com.ocpp201.websocket.websockets201.util.JsonHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.time.Instant;
//
//
//@Slf4j
//@Service("TransactionEvent")
//@RequiredArgsConstructor
//public class TransactionEventHandler implements HandlerStrategy {
//
////    private final TransactionRepository txRepo;
////    private final MeterValueService meterValueService;
////    private final ObjectMapper mapper = new ObjectMapper();
////
////    @Override
////    public String action() {
////        return "TransactionEvent";
////    }
////
////    @Override
////    public void handle(WebSocketSession session, OcppMessage message) throws Exception {
////
////        JsonNode payload = message.getPayload();
////
////        String eventType = payload.path("eventType").asText();
////        String txId = payload.path("transactionInfo").path("transactionId").asText();
////        String cpId = payload.path("evse").path("id").asText();
////        String idToken = payload.path("idToken").path("idToken").asText(null);
////
////        // ================================
////        //  SAFE OCPP 2.0.1 METER VALUE PARSING
////        // ================================
////        Double meterValue = extractMeterValue(payload);
////
////        log.info(" TransactionEvent → eventType={} txId={} cpId={} idToken={} meterValue={}",
////                eventType, txId, cpId, idToken, meterValue);
////
////        switch (eventType) {
////
////            case "Started" -> handleStarted(txId, cpId, idToken, meterValue);
////
////            case "Updated", "Charging" -> handleUpdated(txId, cpId, meterValue);
////
////            case "Ended" -> handleEnded(txId, cpId, meterValue);
////
////            default ->
////                    log.warn(" Unknown TransactionEvent type received: {}", eventType);
////        }
////
////        // Prepare ACK response
////        ObjectNode respPayload = mapper.createObjectNode();
////        respPayload.put("status", "Accepted");
////
////        String json = JsonHelper.result(message.getUniqueId(), respPayload);
////        session.sendMessage(new TextMessage(json));
////    }
////
////    // =======================================================
////    //            SAFE PARSING OF OCPP 2.0.1 METER VALUES
////    // =======================================================
////    private Double extractMeterValue(JsonNode payload) {
////
////        try {
////            JsonNode mvArray = payload.get("meterValue");
////
////            if (mvArray == null || !mvArray.isArray() || mvArray.size() == 0)
////                return null;
////
////            JsonNode mvEntry = mvArray.get(0);
////            JsonNode sampledValues = mvEntry.get("sampledValue");
////
////            if (sampledValues != null && sampledValues.isArray() && sampledValues.size() > 0) {
////
////                JsonNode sample = sampledValues.get(0);
////
////                if (sample.has("value")) {
////                    return sample.get("value").asDouble();
////                }
////            }
////        } catch (Exception e) {
////            log.error(" Failed to parse meterValue: {}", e.getMessage());
////        }
////
////        return null;
////    }
////
////    // =======================================================
////    //              EVENT TYPE HANDLERS
////    // =======================================================
////
////    private void handleStarted(String txId, String cpId, String idToken, Double meterStart) {
////
////        Transaction tx = new Transaction();
////        tx.setTransactionId(txId);
////        tx.setCpId(cpId);
////        tx.setIdToken(idToken);
////        tx.setStartTime(Instant.now());
////        tx.setStartMeterValue(meterStart);
////        tx.setLastKnownMeterValue(meterStart);
////        tx.setStatus("ACTIVE");
////
////        txRepo.save(tx);
////
////        // Save the meter sample properly
////        if (meterStart != null) {
////            meterValueService.saveMeterValue(txId, cpId, meterStart);
////        }
////
////        log.info(" Transaction STARTED → txId={} cpId={} meterStart={}", txId, cpId, meterStart);
////    }
////
////    private void handleUpdated(String txId, String cpId, Double meterValue) {
////
////        if (meterValue != null) {
////            meterValueService.saveMeterValue(txId, cpId, meterValue);
////        }
////
////        txRepo.findById(txId).ifPresent(tx -> {
////            tx.setLastKnownMeterValue(meterValue);
////            txRepo.save(tx);
////        });
////
////        log.info(" Transaction UPDATED → txId={} meter={}", txId, meterValue);
////    }
////
////    private void handleEnded(String txId, String cpId, Double meterStop) {
////
////        txRepo.findById(txId).ifPresent(tx -> {
////
////            tx.setStopTime(Instant.now());
////            tx.setStopMeterValue(meterStop);
////
////            if (tx.getStartMeterValue() != null && meterStop != null) {
////                tx.setEnergyKwh((meterStop - tx.getStartMeterValue()) / 1000.0);
////            }
////
////            tx.setStatus("FINISHED");
////            txRepo.save(tx);
////
////            if (meterStop != null) {
////                meterValueService.saveMeterValue(txId, cpId, meterStop);
////            }
////
////            log.info(" Transaction ENDED → txId={} energy={} kWh",
////                    txId, tx.getEnergyKwh());
////        });
////    }
//
//
//
//
//
//    private final TransactionRepository txRepo;
//    private final MeterValueService meterValueService;
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Override
//    public String action() {
//        return "TransactionEvent";
//    }
//
//
//
//    @Override
//    public void handle(WebSocketSession session, OcppMessage message) throws Exception {
//
//        String chargeBoxId =
//                session.getUri().getPath().replaceAll(".*/", "");
//
//        JsonNode p = message.getPayload();
//        String eventType = p.path("eventType").asText();
//
//        JsonNode txInfo = p.path("transactionInfo");
//        Long txId = txInfo.hasNonNull("transactionId")
//                ? txInfo.get("transactionId").asLong()
//                : null;
//
//        String evseId = p.path("evse").path("id").asText();
//        String idToken = p.path("idToken").path("idToken").asText(null);
//        Double meterValue = extractMeterValue(p);
//
////        log.info("TransactionEvent → type={} txId={} cpId={} meterValue={}",
////                eventType, txId, cpId, meterValue);
//
//        log.info("TransactionEvent → type={} evseId={} meterValue={}",
//                eventType, evseId, meterValue);
//
//        //  STARTED → generate txId and return it
//        if ("Started".equals(eventType)) {
//
//            Transaction tx = new Transaction();
//            tx.setEvseId(evseId);
//            tx.setIdToken(idToken);
//            tx.setStartTime(Instant.now());
//            tx.setStartMeterValue(meterValue);
//            tx.setLastKnownMeterValue(meterValue);
//            tx.setStatus("ACTIVE");
//
//            tx = txRepo.save(tx); // 🔥 DB generates ID
//
//            ObjectNode resp = mapper.createObjectNode();
//            resp.put("status", "Accepted");
//            resp.put("transactionId", tx.getTransactionId()); // 🔥 KEY FIX
//
//            String json = JsonHelper.result(message.getUniqueId(), resp);
//            session.sendMessage(new TextMessage(json));
//
//            log.info("STARTED → assigned txId={} evseId={} meterStart={} chargeBoxId={}", tx.getTransactionId(), evseId, meterValue,chargeBoxId);
//            return;
//        }
//
//        if (txId == null) {
//            log.error(" {} event received WITHOUT transactionId – cannot persist", eventType);
//            return;
//        }
//
//
//        switch (eventType) {
//            case "Updated", "Charging" -> handleUpdated(txId, evseId, meterValue, p);
//            case "Ended" -> handleEnded(txId, evseId, meterValue, p);
//            default -> log.warn("Unknown TransactionEvent: {}", eventType);
//        }
//
//        ObjectNode resp = mapper.createObjectNode();
//        resp.put("status", "Accepted");
//        session.sendMessage(new TextMessage(
//                JsonHelper.result(message.getUniqueId(), resp)
//        ));
//    }
//
//
//    // ---------------------------------------------------------
//    //       SAFE METER VALUE EXTRACTION FOR OCPP 2.0.1
//    // ---------------------------------------------------------
//    private Double extractMeterValue(JsonNode p) {
//
//        try {
//            JsonNode mvArray = p.get("meterValue");
//
//            if (mvArray == null || !mvArray.isArray() || mvArray.size() == 0)
//                return null;
//
//            JsonNode mvEntry = mvArray.get(0);
//
//            JsonNode sampled = mvEntry.get("sampledValue");
//            if (sampled != null && sampled.isArray() && sampled.size() > 0) {
//                JsonNode sample = sampled.get(0);
//
//                return sample.get("value").asDouble();
//            }
//
//        } catch (Exception ex) {
//            log.error("Meter parsing error: {}", ex.getMessage());
//        }
//
//        return null;
//    }
//
//    // ---------------------------------------------------------
//    //                       HANDLERS
//    // ---------------------------------------------------------
//
//    private Long handleStarted(Long txId, String evseId, String idToken, Double meterStart, JsonNode p) {
//
//        Transaction tx = new Transaction();
//        //tx.setTransactionId(txId);
//        tx.setEvseId(evseId);
//        tx.setIdToken(idToken);
//        tx.setStartTime(Instant.now());
//        tx.setStartMeterValue(meterStart);
//        tx.setLastKnownMeterValue(meterStart);
//        tx.setStatus("ACTIVE");
//
//        txRepo.save(tx);
//
//        saveMeterHeaders(txId, evseId, p);
//
//        log.info("STARTED → txId={} evseId={} meterStart={}", txId, evseId, meterStart);
//        return tx.getTransactionId();
//    }
//
//    private void handleUpdated(Long txId, String evseId, Double meterValue, JsonNode p) {
//
//        saveMeterHeaders(txId, evseId, p);
//
//        txRepo.findById(txId).ifPresent(tx -> {
//            tx.setLastKnownMeterValue(meterValue);
//            txRepo.save(tx);
//        });
//
//        log.info("UPDATED → txId={} meter={} evseId={}", txId, meterValue,evseId);
//    }
//
//    private void handleEnded(Long txId, String evseId, Double meterStop, JsonNode p) {
//
//        saveMeterHeaders(txId, evseId, p);
//
//        txRepo.findById(txId).ifPresent(tx -> {
//
//            tx.setStopTime(Instant.now());
//            tx.setStopMeterValue(meterStop);
//
//            if (tx.getStartMeterValue() != null && meterStop != null)
//                tx.setEnergyKwh((meterStop - tx.getStartMeterValue()) / 1000.0);
//
//            tx.setStatus("FINISHED");
//            txRepo.save(tx);
//
//            log.info("ENDED → txId={} evseId={} energy={} kWh", txId, evseId,tx.getEnergyKwh());
//        });
//    }
//
//    // ---------------------------------------------------------
//    //      SAVE ALL METER HEADERS (full 2.0.1 structure)
//    // ---------------------------------------------------------
////    private void saveMeterHeaders(Long txId, String cpId, JsonNode p) {
////        JsonNode mvArray = p.get("meterValue");
////
////        if (mvArray != null && mvArray.isArray()) {
////            for (JsonNode mvNode : mvArray) {
////
////                try {
////                    MeterValueHeader header = mapper.treeToValue(mvNode, MeterValueHeader.class);
////                    meterValueService.saveMeterHeader(txId, cpId, header);
////
////                } catch (Exception ex) {
////                    log.error("Failed to convert MeterValue header: {}", ex.getMessage());
////                }
////            }
////        }
////    }
//
//    private void saveMeterHeaders(Long txId, String cpId, JsonNode p) {
//
//        JsonNode mvArray = p.get("meterValue");
//        if (mvArray == null || !mvArray.isArray()) return;
//
//        // 🔥 evseId comes from TransactionEvent payload
//        Integer evseId = p.path("evse").path("id").asInt(1);
//
//        for (JsonNode mvNode : mvArray) {
//            try {
//                MeterValueHeader header =
//                        mapper.treeToValue(mvNode, MeterValueHeader.class);
//
//                meterValueService.saveMeterHeader(
//                        txId,
//                        cpId,
//                        evseId,
//                        header
//                );
//
//            } catch (Exception ex) {
//                log.error("Failed to convert MeterValue header", ex);
//            }
//        }
//    }
//
//
//}



@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionEventHandler implements HandlerStrategy {

    private final TransactionRepository txRepo;
    private final MeterValueService meterValueService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String action() {
        return "TransactionEvent";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage message) throws Exception {

        String chargeBoxId =
                session.getUri().getPath().replaceAll(".*/", "");

        JsonNode p = message.getPayload();
        String eventType = p.path("eventType").asText(null);

        if (eventType == null) {
            log.error("[TX][ERR] Missing eventType payload={}", p);
            return;
        }

        JsonNode txInfo = p.path("transactionInfo");
        Long txId = txInfo.hasNonNull("transactionId")
                ? txInfo.get("transactionId").asLong()
                : null;

        Integer evseId = p.path("evse").path("id").asInt(1);
        String idToken = p.path("idToken").path("idToken").asText(null);
        Double meterValue = extractMeterValue(p);

        log.info(
                "[TX][IN] type={} cp={} evse={} txId={} meter={}",
                eventType, chargeBoxId, evseId, txId, meterValue
        );

        switch (eventType) {

            case "Started" -> handleStarted(
                    session, message, chargeBoxId, evseId, idToken, meterValue
            );

            case "Updated", "Charging" -> {
                if (!validateActiveTransaction(txId)) return;
                handleUpdated(txId, chargeBoxId, evseId, meterValue, p);
                sendAccepted(session, message);
            }

            case "Ended" -> {
                if (!validateActiveTransaction(txId)) return;
                handleEnded(txId, chargeBoxId, evseId, meterValue, p);
                sendAccepted(session, message);
            }

            default -> {
                log.warn("[TX][WARN] Unknown eventType={} cp={}", eventType, chargeBoxId);
                sendAccepted(session, message);
            }
        }
    }

    // =========================================================
    // STARTED
    // =========================================================
    private void handleStarted(
            WebSocketSession session,
            OcppMessage message,
            String chargeBoxId,
            Integer evseId,
            String idToken,
            Double meterStart
    ) throws Exception {

        log.debug("[TX][DB] Creating transaction cp={} evse={}", chargeBoxId, evseId);

        Transaction tx = new Transaction();
        tx.setEvseId(String.valueOf(evseId));
        tx.setIdToken(idToken);
        tx.setStartTime(Instant.now());
        tx.setStartMeterValue(meterStart);
        tx.setLastKnownMeterValue(meterStart);
        tx.setStatus("ACTIVE");

        tx = txRepo.save(tx);

        log.info("[TX][DB] Transaction saved txId={}", tx.getTransactionId());

        ObjectNode resp = mapper.createObjectNode();
        resp.put("status", "Accepted");
        resp.put("transactionId", tx.getTransactionId());

        session.sendMessage(new TextMessage(
                JsonHelper.result(message.getUniqueId(), resp)
        ));

        log.info(
                "[TX][OUT] Started Accepted cp={} evse={} txId={}",
                chargeBoxId, evseId, tx.getTransactionId()
        );
    }

    // =========================================================
    // UPDATED / CHARGING
    // =========================================================
    private void handleUpdated(
            Long txId,
            String chargeBoxId,
            Integer evseId,
            Double meterValue,
            JsonNode payload
    ) {

        log.debug("[TX][DB] Updating txId={} meter={}", txId, meterValue);

        saveMeterHeadersSafely(txId, chargeBoxId, evseId, payload);

        txRepo.findById(txId).ifPresent(tx -> {
            tx.setLastKnownMeterValue(meterValue);
            txRepo.save(tx);
            log.debug("[TX][DB] txId={} lastMeter updated", txId);
        });

        log.info("[TX][OK] Updated txId={}", txId);
    }

    // =========================================================
    // ENDED
    // =========================================================
    private void handleEnded(
            Long txId,
            String chargeBoxId,
            Integer evseId,
            Double meterStop,
            JsonNode payload
    ) {

        log.info("[TX][END] Processing end txId={}", txId);

        saveMeterHeadersSafely(txId, chargeBoxId, evseId, payload);

        txRepo.findById(txId).ifPresent(tx -> {

            if ("FINISHED".equals(tx.getStatus())) {
                log.warn("[TX][WARN] txId={} already finished", txId);
                return;
            }

            tx.setStopTime(Instant.now());
            tx.setStopMeterValue(meterStop);

            if (tx.getStartMeterValue() != null && meterStop != null) {
                tx.setEnergyKwh(
                        (meterStop - tx.getStartMeterValue()) / 1000.0
                );
            }

            tx.setStatus("FINISHED");
            txRepo.save(tx);

            log.info(
                    "[TX][DONE] txId={} energy={} kWh",
                    txId, tx.getEnergyKwh()
            );
        });
    }

    // =========================================================
    // VALIDATION
    // =========================================================
    private boolean validateActiveTransaction(Long txId) {

        if (txId == null) {
            log.error("[TX][ERR] Missing transactionId");
            return false;
        }

        return txRepo.findById(txId)
                .map(tx -> {
                    if (!"ACTIVE".equals(tx.getStatus())) {
                        log.warn(
                                "[TX][WARN] txId={} invalid status={}",
                                txId, tx.getStatus()
                        );
                        return false;
                    }
                    return true;
                })
                .orElseGet(() -> {
                    log.error("[TX][ERR] txId={} not found in DB", txId);
                    return false;
                });
    }

    // =========================================================
    // SAVE METER VALUES
    // =========================================================
    private void saveMeterHeadersSafely(
            Long txId,
            String chargeBoxId,
            Integer evseId,
            JsonNode payload
    ) {

        JsonNode mvArray = payload.get("meterValue");
        if (mvArray == null || !mvArray.isArray() || mvArray.isEmpty()) {
            log.debug("[MV][SKIP] No meter values txId={}", txId);
            return;
        }

        meterValueService.saveMeterHeaders(
                txId,
                chargeBoxId,
                evseId,
                mvArray,
                mapper
        );

        log.debug("[MV][OK] MeterValues delegated to service txId={}", txId);
    }


    private void sendAccepted(WebSocketSession session, OcppMessage message)
            throws Exception {

        ObjectNode resp = mapper.createObjectNode();
        resp.put("status", "Accepted");

        session.sendMessage(new TextMessage(
                JsonHelper.result(message.getUniqueId(), resp)
        ));
    }

    private Double extractMeterValue(JsonNode p) {
        try {
            JsonNode mv = p.path("meterValue");
            if (mv.isArray() && !mv.isEmpty()) {
                JsonNode sv = mv.get(0).path("sampledValue");
                if (sv.isArray() && !sv.isEmpty()) {
                    return sv.get(0).path("value").asDouble();
                }
            }
        } catch (Exception ignored) {}
        return null;
    }
}
