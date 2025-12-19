package com.ocpp201.websocket.websockets201.handler;

import com.ocpp201.websocket.websockets201.model.OcppMessage;
import com.ocpp201.websocket.websockets201.service.ChargePointService;
import com.ocpp201.websocket.websockets201.util.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Service
public class StatusNotificationHandler implements HandlerStrategy{

    private final ChargePointService cpService;

    public StatusNotificationHandler(ChargePointService cpService) {
        this.cpService = cpService;
    }

    @Override
    public String action() {
        return "StatusNotification";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage message) throws Exception {
        String cpId = session.getUri().getPath().replaceAll(".*/", "");

        String status = message.getPayload().has("connectorStatus")
                ? message.getPayload().get("connectorStatus").asText()
                : "Unknown";

        cpService.updateStatus(cpId, status);
        log.info("StatusNotification from {} status={}", cpId, status);

        // empty payload result
        String resp = JsonHelper.result(message.getUniqueId(), JsonHelper.obj());
        session.sendMessage(new TextMessage(resp));
    }
}
