package com.ocpp201.websocket.websockets201.handler;


import com.ocpp201.websocket.websockets201.model.OcppMessage;
import com.ocpp201.websocket.websockets201.service.ChargePointService;
import com.ocpp201.websocket.websockets201.util.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.JsonNode;

import java.time.Instant;

@Slf4j
@Service
public class BootNotificationHandler implements HandlerStrategy {


    private final ChargePointService cpService;

    public BootNotificationHandler(ChargePointService cpService) {
        this.cpService = cpService;
    }

    @Override
    public String action() {
        return "BootNotification";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage message) throws Exception {
        String ChargeBoxId=session.getUri().getPath().replaceAll(".*/", "");
        JsonNode cs = message.getPayload().get("chargingStation");

        String vendor = cs != null && cs.has("vendorName") ? cs.get("vendorName").asText() : "Unknown";
        String model  = cs != null && cs.has("model")      ? cs.get("model").asText()      : "Unknown";


        cpService.registerOrUpdate(ChargeBoxId, vendor, model);


        log.info("BootNotification from {} vendor={} model={}", ChargeBoxId, vendor, model);

        var payload = JsonHelper.obj()
                .put("currentTime", Instant.now().toString())
                .put("interval", 300)
                .put("status", "Accepted");

        String resp = JsonHelper.result(message.getUniqueId(), payload);
        session.sendMessage(new TextMessage(resp));
    }
}
