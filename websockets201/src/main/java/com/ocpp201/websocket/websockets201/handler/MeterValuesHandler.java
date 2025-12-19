package com.ocpp201.websocket.websockets201.handler;


import com.ocpp201.websocket.websockets201.dto.MeterValueHeader;
import com.ocpp201.websocket.websockets201.model.OcppMessage;
import com.ocpp201.websocket.websockets201.service.ActiveTransactionService;
import com.ocpp201.websocket.websockets201.service.MeterValueService;
import com.ocpp201.websocket.websockets201.util.JsonHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

//    @Slf4j
//    @Service
//    public class MeterValuesHandler implements HandlerStrategy{
////        private final MeterValueService meterValueService;
////
////        public MeterValuesHandler(MeterValueService meterValueService) {
////            this.meterValueService = meterValueService;
////        }
////
////        @Override
////        public String action() {
////            // MUST match OCPP action name sent by CP
////            return "MeterValues";
////        }
////
////
////       @Override
////        public void handle(WebSocketSession session,
////                           OcppMessage message) throws Exception {
////
////            String cpId = session.getUri().getPath().replaceAll(".*/", "");
////            JsonNode payload = message.getPayload();
////
////            String transactionId =
////                    payload.has("transactionId") ? payload.get("transactionId").asText() : null;
////
////            // Normal OCPP 2.0 payload has array of meterValue objects
////            JsonNode values = payload.get("meterValue");
////
////            meterValueService.saveMeterValues(cpId, transactionId, values);
////
////            log.info(" Received MeterValues from CP={} tx={}", cpId, transactionId);
////
////            // Respond ACK
////            String response = JsonHelper.result(message.getUniqueId(), JsonHelper.obj());
////            session.sendMessage(new TextMessage(response));
////        }
//
//
//        @Override
//        public String action() {
//            return "MeterValues"; // for old chargers
//        }
//
//        @Override
//        public void handle(WebSocketSession session, OcppMessage message) throws Exception {
//
//            log.warn("Received MeterValues (OCPP 1.6) — ignoring because backend is OCPP 2.0.1");
//
//            // Always send empty Accepted response
//            String resp = JsonHelper.result(message.getUniqueId(), JsonHelper.obj());
//            session.sendMessage(new TextMessage(resp));
//        }
//
//    }


//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class MeterValuesHandler implements HandlerStrategy {
//
//    private final MeterValueService meterValueService;
//    private final ActiveTransactionService activeTransactionService;
//    private final ObjectMapper objectMapper;
//
//    @Override
//    public String action() {
//        return "MeterValues";
//    }
//
//    @Override
//    public void handle(WebSocketSession session, OcppMessage message)
//            throws Exception {
//
//        JsonNode payload = message.getPayload();
//        String cpId = session.getUri().getPath().replaceAll(".*/", "");
//
//        // DO NOT trust payload transactionId
//        Long transactionId = activeTransactionService.get(cpId);
//
//        if (transactionId == null) {
//            log.warn("MeterValues ignored – no active tx for CP={}", cpId);
//            sendOk(session, message);
//            return;
//        }
//
//        String evseId = payload.path("evseId").asText("1");
//
//        JsonNode meterValues = payload.get("meterValue");
//        if (meterValues == null || !meterValues.isArray()) {
//            log.warn("Invalid MeterValues payload from CP={}", cpId);
//            sendOk(session, message);
//            return;
//        }
//
//        for (JsonNode mvNode : meterValues) {
//            MeterValueHeader header =
//                    objectMapper.treeToValue(mvNode, MeterValueHeader.class);
//
//            meterValueService.saveMeterHeader(
//                    transactionId,
//                    evseId,
//                    header
//            );
//        }
//
//        log.info("MeterValues stored tx={} cp={}", transactionId, cpId);
//        sendOk(session, message);
//    }
//
//    private void sendOk(WebSocketSession session, OcppMessage message)
//            throws Exception {
//
//        session.sendMessage(new TextMessage(
//                JsonHelper.result(message.getUniqueId(), JsonHelper.obj())
//        ));
//    }
//}
@Slf4j
@Service
@RequiredArgsConstructor
public class MeterValuesHandler implements HandlerStrategy {

    private final MeterValueService meterValueService;
    private final ActiveTransactionService activeTransactionService;
    private final ObjectMapper objectMapper;

    @Override
    public String action() {
        return "MeterValues";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage message)
            throws Exception {

        // 🔥 ALWAYS use chargeBoxId from WebSocket URL
        String chargeBoxId =
                session.getUri().getPath().replaceAll(".*/", "");

        JsonNode payload = message.getPayload();

        // 🔥 NEVER trust transactionId in MeterValues (OCPP 2.0.1)
        Long transactionId = activeTransactionService.get(chargeBoxId);

        if (transactionId == null) {
            log.warn(
                    "MeterValues ignored: no active transaction for chargeBoxId={}",
                    chargeBoxId
            );
            sendOk(session, message);
            return;
        }

        // 🔥 evseId == connector_id in your DB
        Integer evseId = payload.path("evseId").asInt(1);

        JsonNode meterValues = payload.get("meterValue");
        if (meterValues == null || !meterValues.isArray()) {
            log.warn(
                    "Invalid MeterValues payload from chargeBoxId={}",
                    chargeBoxId
            );
            sendOk(session, message);
            return;
        }

        // ------------------------------------------------
        // Save meter values
        // ------------------------------------------------
        meterValueService.saveMeterHeaders(
                transactionId,
                chargeBoxId,
                evseId,
                meterValues,
                objectMapper
        );

        log.info(
                "MeterValues stored ✓ txId={} chargeBoxId={} evseId={}",
                transactionId, chargeBoxId, evseId
        );

        sendOk(session, message);
    }

    // ------------------------------------------------
    // ACK response
    // ------------------------------------------------
    private void sendOk(WebSocketSession session, OcppMessage message)
            throws Exception {

        session.sendMessage(
                new TextMessage(
                        JsonHelper.result(
                                message.getUniqueId(),
                                JsonHelper.obj()
                        )
                )
        );
    }
}
