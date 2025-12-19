package com.ocpp201.websocket.websockets201.config;
import org.jspecify.annotations.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.List;

public class OcppHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected @Nullable String selectProtocol(List<String> requestedProtocols, WebSocketHandler webSocketHandler) {
//        return super.selectProtocol(requestedProtocols, webSocketHandler);

        // Case 1 — client supports OCPP 2.0.1 subprotocol
        if (requestedProtocols.contains("ocpp2.0.1")) {
            return "ocpp2.0.1";
        }

        // Case 2 — Postman or browser (NO subprotocol)
        if (requestedProtocols.isEmpty()) {
            return null; // allow websocket without protocol
        }

        // Case 3 — Unknown protocol
        return null;
    }


}
