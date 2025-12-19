package com.ocpp201.websocket.websockets201.handler;


import com.ocpp201.websocket.websockets201.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class RemoteStartResponseHandler {


    private final TransactionService txService;

    public void handle(Long requestId, String status) {
        if ("Accepted".equals(status)) {
            txService.updateStartAccepted(requestId);
        }
    }
}
