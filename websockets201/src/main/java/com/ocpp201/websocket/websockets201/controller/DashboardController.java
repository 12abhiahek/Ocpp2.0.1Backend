package com.ocpp201.websocket.websockets201.controller;

import com.ocpp201.websocket.websockets201.service.ChargePointService;
import com.ocpp201.websocket.websockets201.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {


    private final ChargePointService cpService;
    private final TransactionService txnService;

    public DashboardController(ChargePointService cpService, TransactionService txnService) {
        this.cpService = cpService;
        this.txnService = txnService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
