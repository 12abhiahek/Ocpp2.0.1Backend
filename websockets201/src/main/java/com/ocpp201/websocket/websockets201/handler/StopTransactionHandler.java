package com.ocpp201.websocket.websockets201.handler;

import com.ocpp201.websocket.websockets201.model.OcppMessage;
import com.ocpp201.websocket.websockets201.model.Transaction;
import com.ocpp201.websocket.websockets201.service.TransactionService;
import com.ocpp201.websocket.websockets201.util.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.JsonNode;

@Slf4j
@Service
public class StopTransactionHandler implements HandlerStrategy{

    private final TransactionService txService;

    public StopTransactionHandler(TransactionService txService) {
        this.txService = txService;
    }

    @Override
    public String action() {
        return "StopTransaction";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage message) throws Exception {
        JsonNode payload = message.getPayload();
        Long txId = payload.has("transactionId")
                ? Long.valueOf(payload.get("transactionId").asText())
                : null;

        Transaction tx = txService.stop(txId);
        log.info("StopTransaction for txId={} -> {}", txId, tx != null ? tx.getStatus() : "NOT FOUND");

        String resp = JsonHelper.result(message.getUniqueId(), JsonHelper.obj());
        session.sendMessage(new TextMessage(resp));
    }
}
