package com.ocpp201.websocket.websockets201.handler;

import com.ocpp201.websocket.websockets201.model.OcppMessage;
import com.ocpp201.websocket.websockets201.util.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.node.ObjectNode;

@Component
@Slf4j
public class ReserveNowHandler implements HandlerStrategy {
    @Override
    public String action() {
        return "ReserveNow";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage msg)
            throws Exception {

        ObjectNode resp = JsonHelper.obj();
        resp.put("status", "Accepted");

        session.sendMessage(
                new TextMessage(
                        JsonHelper.result(msg.getUniqueId(), resp)
                )
        );
    }
}
