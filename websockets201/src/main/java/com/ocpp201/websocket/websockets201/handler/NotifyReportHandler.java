package com.ocpp201.websocket.websockets201.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotifyReportHandler implements HandlerStrategy{
    @Override
    public String action() {
        return "NotifyReport";
    }

    @Override
    public void handle(org.springframework.web.socket.WebSocketSession session, com.ocpp201.websocket.websockets201.model.OcppMessage msg) {
        log.info("[CP][IN ] NotifyReport response {}", msg.getPayload());
    }

}
