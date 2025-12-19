package com.ocpp201.websocket.websockets201.handler;


import com.ocpp201.websocket.websockets201.model.OcppMessage;
//import com.ocpp201.websocket.websockets201.service.AuthorizationService;
import com.ocpp201.websocket.websockets201.service.AuthorizationService;
import com.ocpp201.websocket.websockets201.util.JsonHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class AuthorizeHandler implements HandlerStrategy{
//
////    private final AuthorizationService authorizationService;
////    private final ObjectMapper mapper = new ObjectMapper();
////
////    @Override
////    public String action() {
////        return "Authorize";
////    }
////
////    @Override
////    public void handle(WebSocketSession session, OcppMessage message) throws Exception {
////
////        JsonNode payload = message.getPayload();
////
////        // OCPP 2.0.1 Authorize Request Format:
////        String idToken = "UNKNOWN";
////
////        if (payload != null && payload.has("idToken")) {
////            JsonNode idTokenNode = payload.get("idToken");
////            if (idTokenNode.has("idToken")) {
////                idToken = idTokenNode.get("idToken").asText();
////            }
////        }
////
////        log.info(" Authorize Request received → idToken={}", idToken);
////
////        // DB validation
////        String status = authorizationService.validateToken(idToken);
////
////        // Auto-register new tokens (Optional)
////        if ("Invalid".equals(status)) {
////            authorizationService.registerNewToken(idToken);
////            log.info("Registered new token = {}", idToken);
////            status = "Accepted";
////        }
////
////        // Build OCPP 2.0.1 CallResult response
////        ObjectNode idTokenInfo = mapper.createObjectNode();
////        idTokenInfo.put("status", status);
////
////        ObjectNode responsePayload = mapper.createObjectNode();
////        responsePayload.set("idTokenInfo", idTokenInfo);
////
////        // OCPP 2.0.1 Response:
////        // {
////        //   "messageId": "...",
////        //   "action": "Authorize",
////        //   "payload": { ... }
////        // }
////        ObjectNode callResult = mapper.createObjectNode();
////        callResult.put("messageId", message.getUniqueId());
////        callResult.put("action", "Authorize");
////        callResult.set("payload", responsePayload);
////
////        String jsonResponse = mapper.writeValueAsString(callResult);
////
////        log.info(" Authorize Response → {}", jsonResponse);
////
////        session.sendMessage(new TextMessage(jsonResponse));
////    }
//
//
//
//    private final AuthorizationService authorizationService;
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Override
//    public String action() {
//        return "Authorize";
//    }
//
//    @Override
//    public void handle(WebSocketSession session, OcppMessage message) throws Exception {
//
//        JsonNode payload = message.getPayload();
//
//        String idToken = payload
//                .path("idToken")
//                .path("idToken")
//                .asText(null);
//
//        log.info("Authorize → idToken={}", idToken);
//
//        String status = authorizationService.validateToken(idToken);
//
//        // Optional auto-register
//        if ("Invalid".equals(status)) {
//            authorizationService.registerNewToken(idToken);
//            status = "Accepted";
//            log.info("Auto-registered new token {}", idToken);
//        }
//
//        // Build OCPP 2.0.1 response
//        ObjectNode idTokenInfo = mapper.createObjectNode();
//        idTokenInfo.put("status", status);
//
//        ObjectNode responsePayload = mapper.createObjectNode();
//        responsePayload.set("idTokenInfo", idTokenInfo);
//
//        String response = JsonHelper.result(
//                message.getUniqueId(),
//                responsePayload
//        );
//
//        log.info("Authorize response → {}", response);
//
//        session.sendMessage(new TextMessage(response));
//    }
//}


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizeHandler implements HandlerStrategy {

    private final AuthorizationService authorizationService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String action() {
        return "Authorize";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage message)
            throws Exception {

        JsonNode payload = message.getPayload();

        String idToken = payload
                .path("idToken")
                .path("idToken")
                .asText(null);

        log.info("Authorize → idToken={}", idToken);

        String status = authorizationService.validateToken(idToken);

        if ("Invalid".equals(status)) {
            authorizationService.registerNewToken(idToken);
            status = "Accepted";
        }

        ObjectNode idTokenInfo = mapper.createObjectNode();
        idTokenInfo.put("status", status);

        ObjectNode responsePayload = mapper.createObjectNode();
        responsePayload.set("idTokenInfo", idTokenInfo);

        session.sendMessage(new TextMessage(
                JsonHelper.result(message.getUniqueId(), responsePayload)
        ));
    }
}
