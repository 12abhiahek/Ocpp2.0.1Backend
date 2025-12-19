package com.ocpp201.websocket.websockets201.handler;

import com.ocpp201.websocket.websockets201.routing.MessageRouter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
@Component
public class OcppWebSocketHandler extends TextWebSocketHandler {

    private final MessageRouter router;

    public OcppWebSocketHandler(MessageRouter router) {
        this.router = router;
    }

    private String extractCpId(WebSocketSession session) {
        String path = session.getUri().getPath(); // /ocpp/v201/CH01
        String[] parts = path.split("/");
        return parts[parts.length - 1];
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String cpId = extractCpId(session);
        log.info("WS: Connection established from CP id={}", cpId);
        router.registerSession(cpId, session);
    }

//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
//        router.route(session, message.getPayload());
//    }
//
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) {
//        String cpId = extractCpId(session);
//        log.error("WS: Transport error for CP {}", cpId, exception);
//    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage msg) {
        String payload = msg.getPayload();

        // Basic OCPP guarding
        if (!payload.trim().startsWith("[")) {
            log.warn("WS: Invalid OCPP text from CP={} msg={}", extractCpId(session), payload);
            return;
        }

        router.route(session, payload);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.warn("WS: Transport warning CP={} reason={}",
                extractCpId(session),
                exception != null ? exception.getMessage() : "unknown");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String cpId = extractCpId(session);
        log.info("WS: Connection closed from CP id={} status={}", cpId, status);
        router.removeSession(cpId);
    }
}
