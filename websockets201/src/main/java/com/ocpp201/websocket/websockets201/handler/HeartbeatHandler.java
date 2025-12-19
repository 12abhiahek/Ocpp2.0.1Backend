package com.ocpp201.websocket.websockets201.handler;


import com.ocpp201.websocket.websockets201.model.OcppMessage;
import com.ocpp201.websocket.websockets201.service.ChargePointService;
import com.ocpp201.websocket.websockets201.util.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.Instant;

@Slf4j
@Service
public class HeartbeatHandler implements HandlerStrategy{

    private final ChargePointService cpService;

    public HeartbeatHandler(ChargePointService cpService) {
        this.cpService = cpService;
    }

    @Override
    public String action() {
        return "Heartbeat";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage message) throws Exception {
        String cpId = session.getUri().getPath().replaceAll(".*/", "");
        cpService.heartbeat(cpId);
        log.info("Heartbeat from {}", cpId);

        var payload = JsonHelper.obj()
                .put("currentTime", Instant.now().toString());

        String resp = JsonHelper.result(message.getUniqueId(), payload);
        session.sendMessage(new TextMessage(resp));
    }
}
