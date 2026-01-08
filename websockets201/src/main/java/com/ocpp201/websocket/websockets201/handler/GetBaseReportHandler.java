package com.ocpp201.websocket.websockets201.handler;

import com.ocpp201.websocket.websockets201.model.OcppMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@Slf4j
public class GetBaseReportHandler implements HandlerStrategy{
    @Override
    public String action() {
        return "GetBaseReport";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage msg) {
        log.info("[CP][IN ] GetBaseReport response {}", msg.getPayload());
    }

//    @Override
//    public void handle(WebSocketSession session, OcppMessage msg) {
//        log.info("[CP][IN ] GetBaseReport accepted");
//    }
}
