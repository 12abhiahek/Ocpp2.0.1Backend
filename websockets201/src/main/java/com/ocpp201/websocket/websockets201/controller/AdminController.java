package com.ocpp201.websocket.websockets201.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @PostMapping("/reset")
    public void resetCharger() {
        // Reset logic
    }

    @PostMapping("/firmware")
    public void updateFirmware() {
    }
}
