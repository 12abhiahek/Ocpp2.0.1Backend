package com.ocpp201.websocket.websockets201.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ActiveTransactionService {

    private final Map<String, Long> activeTxMap = new ConcurrentHashMap<>();

    public void start(String cpId, Long txId) {
        activeTxMap.put(cpId, txId);
    }

    public void end(String cpId) {
        activeTxMap.remove(cpId);
    }

    public Long get(String cpId) {
        return activeTxMap.get(cpId);
    }
}
