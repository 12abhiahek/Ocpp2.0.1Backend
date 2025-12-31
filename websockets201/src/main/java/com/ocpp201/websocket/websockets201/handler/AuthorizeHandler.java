package com.ocpp201.websocket.websockets201.handler;


import com.ocpp201.websocket.websockets201.model.OcppMessage;
//import com.ocpp201.websocket.websockets201.service.AuthorizationService;
import com.ocpp201.websocket.websockets201.service.AuthorizationService;
import com.ocpp201.websocket.websockets201.service.AvailabilityService;
import com.ocpp201.websocket.websockets201.service.ReservationEnforcementService;
import com.ocpp201.websocket.websockets201.util.JsonHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

@Slf4j
@Service
//@RequiredArgsConstructor
public class AuthorizeHandler implements HandlerStrategy {

//    private final AuthorizationService authorizationService;
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Override
//    public String action() {
//        return "Authorize";
//    }
//
//    @Override
//    public void handle(WebSocketSession session, OcppMessage message)
//            throws Exception {
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
//        if ("Invalid".equals(status)) {
//            authorizationService.registerNewToken(idToken);
//            status = "Accepted";
//        }
//
//        ObjectNode idTokenInfo = mapper.createObjectNode();
//        idTokenInfo.put("status", status);
//
//        ObjectNode responsePayload = mapper.createObjectNode();
//        responsePayload.set("idTokenInfo", idTokenInfo);
//
//        session.sendMessage(new TextMessage(
//                JsonHelper.result(message.getUniqueId(), responsePayload)
//        ));
//    }



    private final AvailabilityService availabilityService;
    private final ReservationEnforcementService reservationService;

    public AuthorizeHandler(AvailabilityService availabilityService, ReservationEnforcementService reservationService) {
        this.availabilityService = availabilityService;
        this.reservationService=reservationService;
    }

    @Override
    public String action() {
        return "Authorize";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage msg)
            throws Exception {

        String cpId = session.getUri().getPath().split("/")[3];
        Integer evseId = msg.getPayload().path("evseId").asInt(1);

        String idTag =
                msg.getPayload()
                        .path("idToken")
                        .path("idToken")
                        .asText();

        ObjectNode resp = JsonHelper.obj();

        try {
            if (!availabilityService.isOperative(cpId, evseId)) {

                resp.putObject("idTokenInfo")
                        .put("status", "Blocked");

                log.warn(
                        "[AUTHORIZE-BLOCKED] cp={} evse={}",
                        cpId, evseId
                );
            } else if (!reservationService.isAllowed(evseId, idTag)) {

                resp.putObject("idTokenInfo")
                        .put("status", "Blocked");

                log.warn(
                        "[AUTHORIZE-BLOCKED][RESERVATION] cp={} evse={} idTag={}",
                        cpId, evseId, idTag
                );
                
            } else {
                resp.putObject("idTokenInfo")
                        .put("status", "Accepted");

                log.info(
                        "[AUTHORIZE-ACCEPTED] cp={} evse={} token={}",
                        cpId, evseId, idTag
                );
            }

            session.sendMessage(
                    new TextMessage(
                            JsonHelper.result(msg.getUniqueId(), resp)
                    )
            );

        } catch (Exception ex) {

            log.error("[AUTHORIZE-ERROR]", ex);

            session.sendMessage(
                    new TextMessage(
                            JsonHelper.error(
                                    msg.getUniqueId(),
                                    "InternalError",
                                    ex.getMessage()
                            )
                    )
            );
        }
    }
}
