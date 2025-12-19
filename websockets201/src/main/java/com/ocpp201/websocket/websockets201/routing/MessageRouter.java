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

    public void route(WebSocketSession session, String raw) {
        try {
            OcppMessage msg = JsonHelper.parse(raw);
            if (msg.getMessageTypeId() != 2) {
                log.warn("Ignoring non-CALL message: {}", raw);
                return;
            }
            HandlerStrategy handler = handlers.get(msg.getAction());
            if (handler == null) {
                log.warn("No handler for action: {}", msg.getAction());
                String err = JsonHelper.error(msg.getUniqueId(),
                        "NotSupported", "Action not implemented");
                session.sendMessage(new TextMessage(err));
                return;
            }
            handler.handle(session, msg);
        } catch (Exception e) {
            log.error("Error routing message: {}", raw, e);
        }
    }
}
