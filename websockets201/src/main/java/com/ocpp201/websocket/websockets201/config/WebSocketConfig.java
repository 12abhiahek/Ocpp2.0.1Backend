package com.ocpp201.websocket.websockets201.config;

import com.ocpp201.websocket.websockets201.handler.OcppWebSocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@Slf4j
public class WebSocketConfig implements org.springframework.web.socket.config.annotation.WebSocketConfigurer {

    private final OcppWebSocketHandler handler;

    public WebSocketConfig(OcppWebSocketHandler handler) {
        this.handler = handler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Postman / CP URL: ws://localhost:8081/ocpp/v201/{cpId}
        registry.addHandler(handler, "/ocpp/v201/{cpId}")
                .setHandshakeHandler(new OcppHandshakeHandler())
//                .setAllowedOrigins("*");
                .setAllowedOriginPatterns("*");
        // No subprotocol enforced -> Postman can connect.
        log.info("WebSocketConfig: Registered OCPP WebSocket handler ws://localhost:8081/ocpp2/v201/{chargeboxId}");
    }
}
