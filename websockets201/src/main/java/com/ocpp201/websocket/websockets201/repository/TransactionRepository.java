package com.ocpp201.websocket.websockets201.repository;


import com.ocpp201.websocket.websockets201.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionId(Long transactionId);
}
