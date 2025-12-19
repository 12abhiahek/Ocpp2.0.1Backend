package com.ocpp201.websocket.websockets201.controller;

import com.ocpp201.websocket.websockets201.model.MeterValue;
import com.ocpp201.websocket.websockets201.repository.MeterValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/meter-values")
@RequiredArgsConstructor
public class MeterValueQueryController {

    private final MeterValueRepository repo;

    @GetMapping("/transaction/{txId}")
    public List<MeterValue> byTx(@PathVariable Long txId) {
        return repo.findAll()
                .stream()
                .filter(x -> txId.equals(x.getTransactionId()))
                .toList();
    }
}
