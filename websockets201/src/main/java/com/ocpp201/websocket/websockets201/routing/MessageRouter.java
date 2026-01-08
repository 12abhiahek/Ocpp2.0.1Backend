package com.ocpp201.websocket.websockets201.routing;


import com.ocpp201.websocket.websockets201.handler.HandlerStrategy;
import com.ocpp201.websocket.websockets201.model.OcppMessage;
import com.ocpp201.websocket.websockets201.util.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MessageRouter {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Map<String, HandlerStrategy> handlers;

    public MessageRouter(List<HandlerStrategy> handlerList) {
        this.handlers = handlerList.stream()
                .collect(Collectors.toMap(HandlerStrategy::action, h -> h));
    }

    public void registerSession(String cpId, WebSocketSession session) {
        sessions.put(cpId, session);
    }

    public void removeSession(String cpId) {
        sessions.remove(cpId);
    }

    public WebSocketSession getSession(String cpId) {
        return sessions.get(cpId);
    }

//    public void route(WebSocketSession session, String raw) {
//        try {
//            OcppMessage msg = JsonHelper.parse(raw);
//            if (msg.getMessageTypeId() != 2) {
//                log.warn("Ignoring non-CALL message: {}", raw);
//                return;
//            }
//            HandlerStrategy handler = handlers.get(msg.getAction());
//            if (handler == null) {
//                log.warn("No handler for action: {}", msg.getAction());
//                String err = JsonHelper.error(msg.getUniqueId(),
//                        "NotSupported", "Action not implemented");
//                session.sendMessage(new TextMessage(err));
//                return;
//            }
//            handler.handle(session, msg);
//        } catch (Exception e) {
//            log.error("Error routing message: {}", raw, e);
//        }
//    }



    // NEW: pending CMS → Charger requests
    private final Map<String, CompletableFuture<OcppMessage>> pending =
            new ConcurrentHashMap<>();
//
//    public MessageRouter(List<HandlerStrategy> handlerList) {
//        this.handlers = handlerList.stream()
//                .collect(Collectors.toMap(HandlerStrategy::action, h -> h));
//    }


    // ------------------------------------------------
    // CMS → Charger (SEND COMMAND)
    // ------------------------------------------------

    public CompletableFuture<OcppMessage> sendCall(
            String cpId,
            String action,
            Object payload
    ) throws Exception {

        WebSocketSession session = sessions.get(cpId);
        if (session == null) {
            throw new IllegalStateException("ChargePoint not connected: " + cpId);
        }

        String messageId = UUID.randomUUID().toString();

        String raw = JsonHelper.call(messageId, action, payload);

        CompletableFuture<OcppMessage> future = new CompletableFuture<>();
        pending.put(messageId, future);

        session.sendMessage(new TextMessage(raw));
        log.info("CMS → CP [{}] {}", action, raw);

        return future;
    }

    // ------------------------------------------------
    // Charger → CMS (ROUTE)
    // ------------------------------------------------

    public void route(WebSocketSession session, String raw) {
        try {
            OcppMessage msg = JsonHelper.parse(raw);

            switch (msg.getMessageTypeId()) {

                case 2 -> handleCall(session, msg);        // CALL
                case 3 -> handleCallResult(msg);           // CALLRESULT
                case 4 -> handleCallError(msg);            // CALLERROR

                default -> log.warn("Unknown message type: {}", raw);
            }

        } catch (Exception e) {
            log.error("Error routing message: {}", raw, e);
        }
    }

    // ------------------------------------------------
    // CALL (Charger → CMS)
    // ------------------------------------------------

    private void handleCall(WebSocketSession session, OcppMessage msg) throws Exception {

        HandlerStrategy handler = handlers.get(msg.getAction());

        if (handler == null) {
            log.warn("No handler for action: {}", msg.getAction());
            String err = JsonHelper.error(
                    msg.getUniqueId(),
                    "NotSupported",
                    "Action not implemented"
            );
            session.sendMessage(new TextMessage(err));
            return;
        }

        handler.handle(session, msg);
    }

    // ------------------------------------------------
    // CALLRESULT (Charger → CMS)
    // ------------------------------------------------

    private void handleCallResult(OcppMessage msg) {

        CompletableFuture<OcppMessage> future =
                pending.remove(msg.getUniqueId());

        if (future != null) {
            future.complete(msg);
            log.info("CP → CMS CALLRESULT for {}", msg.getUniqueId());
        } else {
            log.warn("No pending request for CALLRESULT {}", msg.getUniqueId());
        }
    }

    // ------------------------------------------------
    // CALLERROR
    // ------------------------------------------------

    private void handleCallError(OcppMessage msg) {
        CompletableFuture<OcppMessage> future =
                pending.remove(msg.getUniqueId());

        if (future != null) {
            future.completeExceptionally(
                    new RuntimeException("CALLERROR: " + msg.getPayload())
            );
        }
    }
}
