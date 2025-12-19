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
public class StartTransactionHandler implements HandlerStrategy{

    private final TransactionService txService;

    public StartTransactionHandler(TransactionService txService) {
        this.txService = txService;
    }

    @Override
    public String action() {
        return "StartTransaction";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage message) throws Exception {
        String cpId = session.getUri().getPath().replaceAll(".*/", "");
        JsonNode payload = message.getPayload();
        JsonNode idTokenNode = payload.get("idToken");

        String idToken = idTokenNode != null && idTokenNode.has("idToken")
                ? idTokenNode.get("idToken").asText()
                : "UNKNOWN";

        Transaction tx = txService.start(cpId, idToken);
        log.info("StartTransaction from {} txId={} idToken={}", cpId, tx.getTransactionId(), idToken);

        var respPayload = JsonHelper.obj()
                .put("transactionId", tx.getTransactionId())
                .putObject("idTokenInfo")
                .put("status", "Accepted");

        String resp = JsonHelper.result(message.getUniqueId(), respPayload);
        session.sendMessage(new TextMessage(resp));
    }
}
