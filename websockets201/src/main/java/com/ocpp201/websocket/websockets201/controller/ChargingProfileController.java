package com.ocpp201.websocket.websockets201.controller;

import com.ocpp201.websocket.websockets201.dto.ChargingProfileRequest;
import com.ocpp201.websocket.websockets201.service.ChargingProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/charging-profiles")
@RequiredArgsConstructor
public class ChargingProfileController {

    private final ChargingProfileService service;

    @PostMapping("/apply")
    public ResponseEntity<String> apply(
            @RequestBody ChargingProfileRequest request
    ) {
        service.applyProfile(request);
        return ResponseEntity.ok("Charging profile applied");
    }

    @PostMapping("/clear")
    public ResponseEntity<String> clear(
            @RequestParam String chargeBoxId,
            @RequestParam Integer evseId,
            @RequestParam String profileId
    ) {
        service.clearChargingProfile(
                chargeBoxId,
                evseId,
                profileId
        );
        return ResponseEntity.ok("Charging profile cleared");
    }
}
