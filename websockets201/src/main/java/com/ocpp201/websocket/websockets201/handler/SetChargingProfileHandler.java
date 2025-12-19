//package com.ocpp201.websocket.websockets201.handler;
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
