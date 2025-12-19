package com.ocpp201.websocket.websockets201.service;


import com.ocpp201.websocket.websockets201.model.Transaction;
import com.ocpp201.websocket.websockets201.routing.MessageRouter;
import com.ocpp201.websocket.websockets201.util.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class CommandService {


    private final MessageRouter router;
    private final TransactionService transactionService;

    public CommandService(MessageRouter router, TransactionService transactionService) {
        this.transactionService = transactionService;
        this.router = router;
    }

//    public void sendRemoteStart(String cpId, String idToken) throws IOException {
//        WebSocketSession session = router.getSession(cpId);
//        if (session == null || !session.isOpen()) {
//            throw new IllegalStateException("CP " + cpId + " not connected.");
//        }
//
//        String uniqueId = UUID.randomUUID().toString();
//
//        var payload = JsonHelper.obj()
//                .putObject("idToken")
//                .put("idToken", idToken);
//
//        // OCPP-style frame: [2, uid, "RemoteStartTransaction", {payload}]
//        var arrStr = "[2,\"" + uniqueId + "\",\"RemoteStartTransaction\"," + payload.toString() + "]";
//        log.info("Sending RemoteStartTransaction to {}: {}", cpId, arrStr);
//        session.sendMessage(new TextMessage(arrStr));
//    }


    public void sendRemoteStart(String cpId, String idToken) throws IOException {

        WebSocketSession session = router.getSession(cpId);
        if (session == null || !session.isOpen()) {
            throw new IllegalStateException("CP " + cpId + " not connected.");
        }

        // 1️⃣ One uniqueId for BOTH DB and OCPP
        String uniqueId = UUID.randomUUID().toString();

//        // 2️⃣ Save in DB as Pending
//        transactionService.createPendingRemoteStart(cpId, idToken);


        // 🔹 Save Pending transaction (DB generates transactionId)
        Transaction tx = transactionService.createPendingRemoteStart(cpId, idToken);
        // (optional) log DB transaction id
        log.info("Created pending transaction id={}", tx.getTransactionId());

        // 3️⃣ Build OCPP payload and send
        var payload = JsonHelper.obj()
                .putObject("idToken")
                .put("idToken", idToken);

        String arrStr = "[2,\"" + uniqueId + "\",\"RemoteStartTransaction\"," + payload.toString() + "]";

        log.info("Sending RemoteStartTransaction to {}: {}", cpId, arrStr);
        session.sendMessage(new TextMessage(arrStr));
    }

    public void sendRemoteStop(String cpId, Long transactionId) throws IOException {

        WebSocketSession session = router.getSession(cpId);
        if (session == null || !session.isOpen()) {
            throw new IllegalStateException("CP " + cpId + " not connected.");
        }

        String uniqueId = UUID.randomUUID().toString();

        // optional: mark stopped in DB
        transactionService.stop(transactionId);

        var payload = JsonHelper.obj()
                .put("transactionId", transactionId);

        String arrStr = "[2,\"" + uniqueId + "\",\"RemoteStopTransaction\"," + payload.toString() + "]";

        log.info("Sending RemoteStopTransaction to {}: {}", cpId, arrStr);
        session.sendMessage(new TextMessage(arrStr));
    }


//
//    public void sendRemoteStop(String cpId, String transactionId) throws IOException {
//        WebSocketSession session = router.getSession(cpId);
//        if (session == null || !session.isOpen()) {
//            throw new IllegalStateException("CP " + cpId + " not connected.");
//        }
//
//        String uniqueId = UUID.randomUUID().toString();
//
//        var payload = JsonHelper.obj()
//                .put("transactionId", transactionId);
//
//        var arrStr = "[2,\"" + uniqueId + "\",\"RemoteStopTransaction\"," + payload.toString() + "]";
//        log.info("Sending RemoteStopTransaction to {}: {}", cpId, arrStr);
//        session.sendMessage(new TextMessage(arrStr));
//    }
}
