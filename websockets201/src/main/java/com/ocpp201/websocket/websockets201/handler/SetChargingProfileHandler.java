package com.ocpp201.websocket.websockets201.handler;
//
//import com.ocpp201.websocket.websockets201.model.OcppMessage;
//import com.ocpp201.websocket.websockets201.util.JsonHelper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import tools.jackson.databind.ObjectMapper;
//import tools.jackson.databind.node.ObjectNode;
//
//@Slf4j
//@Service("SetChargingProfile")
//@RequiredArgsConstructor
//public class SetChargingProfileHandler implements HandlerStrategy{
//
//
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Override
//    public String action() {
//        return "SetChargingProfile";
//    }
//
//    @Override
//    public void handle(WebSocketSession session, OcppMessage message) throws Exception {
//
//        log.info("SetChargingProfile ACK from ChargePoint");
//
//        ObjectNode resp = mapper.createObjectNode();
//        resp.put("status", "Accepted");
//
//        session.sendMessage(new TextMessage(
//                JsonHelper.result(message.getUniqueId(), resp)
//        ));
//    }
//}


import com.ocpp201.websocket.websockets201.model.OcppMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.JsonNode;

@Slf4j
@Service
@RequiredArgsConstructor
public class SetChargingProfileHandler implements HandlerStrategy {

//    @Override
//    public String action() {
//        // MUST match OCPP action name
//        return "SetChargingProfile";
//    }
//
//    @Override
//    public void handle(WebSocketSession session, OcppMessage message)
//            throws Exception {
//
//        JsonNode payload = message.getPayload();
//
//        String chargeBoxId =
//                session.getUri().getPath().replaceAll(".*/", "");
//
//        String status = payload.path("status").asText(null);
//
//        if (status == null) {
//            log.error(
//                    "[CP][ERR] SetChargingProfile response without status cp={} payload={}",
//                    chargeBoxId,
//                    payload
//            );
//            return;
//        }
//
//        if ("Accepted".equalsIgnoreCase(status)) {
//            log.info(
//                    "[CP][IN] SetChargingProfile Accepted cp={}",
//                    chargeBoxId
//            );
//        } else {
//            log.warn(
//                    "[CP][IN] SetChargingProfile Rejected cp={} reason={}",
//                    chargeBoxId,
//                    payload
//            );
//        }
//    }




    @Override
    public String action() {
        return "SetChargingProfile";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage message) {

        String cpId = session.getUri().getPath().replaceAll(".*/", "");
        String status = message.getPayload().path("status").asText();

        if ("Accepted".equalsIgnoreCase(status)) {
            log.info("[CP][IN] ChargingProfile Accepted cp={}", cpId);
        } else {
            log.warn("[CP][IN] ChargingProfile Rejected cp={}", cpId);
        }
    }
}
