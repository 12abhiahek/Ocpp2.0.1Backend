package com.ocpp201.websocket.websockets201.handler;

import com.ocpp201.websocket.websockets201.model.OcppMessage;
import org.springframework.web.socket.WebSocketSession;

public interface HandlerStrategy {

    String action(); // "BootNotification", "Heartbeat", etc.
    void handle(WebSocketSession session, OcppMessage message) throws Exception;
}
