package com.ocpp201.websocket.websockets201.service;


import com.ocpp201.websocket.websockets201.model.MeterValue;
import com.ocpp201.websocket.websockets201.model.Transaction;
import com.ocpp201.websocket.websockets201.repository.MeterValueRepository;
import com.ocpp201.websocket.websockets201.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

//    private final TransactionRepository repository;
//
//    public TransactionService(TransactionRepository repository) {
//        this.repository = repository;
//    }
//
//    public Transaction start(String cpId, String idToken) {
//        Transaction tx = new Transaction();
//        tx.setTransactionId(UUID.randomUUID().toString());
//        tx.setCpId(cpId);
//        tx.setIdToken(idToken);
//        tx.setStartTime(Instant.now());
//        tx.setStatus("Started");
//        return repository.save(tx);
//    }
//
//    public Transaction stop(String transactionId) {
//        return repository.findById(transactionId).map(tx -> {
//            tx.setStopTime(Instant.now());
//            tx.setStatus("Stopped");
//            return repository.save(tx);
//        }).orElse(null);
//    }
//
//    public Transaction get(String id) {
//        return repository.findById(id).orElse(null);
//    }
//
//
//    public Transaction createPendingRemoteStart(String cpId, String idToken, String requestId) {
//        Transaction tx = new Transaction();
//        tx.setTransactionId(requestId);          // Same as OCPP uniqueId
//        tx.setCpId(cpId);
//        tx.setIdToken(idToken);
//        tx.setStatus("Pending");                // Not started yet
//        tx.setStartTime(Instant.now());
//        return repository.save(tx);
//    }
//
//
//
//    public Transaction updateStartAccepted(String txId) {
//        return repository.findById(txId).map(tx -> {
//            tx.setStatus("Started");
//            tx.setStartTime(Instant.now());
//            return repository.save(tx);
//        }).orElse(null);
//    }



    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    // 🔹 Start transaction (local or Plug&Charge)
    public Transaction start(String evseId, String idToken) {
        Transaction tx = new Transaction();
        tx.setEvseId(evseId);
        tx.setIdToken(idToken);
        tx.setStartTime(Instant.now());
        tx.setStatus("Started");

        // 🔥 DO NOT set transactionId
        return repository.save(tx); // DB generates ID
    }

    // 🔹 Stop transaction
    public Transaction stop(Long transactionId) {
        return repository.findById(transactionId).map(tx -> {
            tx.setStopTime(Instant.now());
            tx.setStatus("Stopped");
            return repository.save(tx);
        }).orElse(null);
    }

    public Transaction get(Long transactionId) {
        return repository.findById(transactionId).orElse(null);
    }

    // 🔹 RemoteStart pending transaction
    public Transaction createPendingRemoteStart(String evseId, String idToken) {
        Transaction tx = new Transaction();
        tx.setEvseId(evseId);
        tx.setIdToken(idToken);
        tx.setStatus("Pending");
        tx.setStartTime(Instant.now());

        return repository.save(tx); // auto ID
    }

    // 🔹 When RemoteStart is accepted
    public Transaction updateStartAccepted(Long transactionId) {
        return repository.findById(transactionId).map(tx -> {
            tx.setStatus("Started");
            tx.setStartTime(Instant.now());
            return repository.save(tx);
        }).orElse(null);
    }




}
