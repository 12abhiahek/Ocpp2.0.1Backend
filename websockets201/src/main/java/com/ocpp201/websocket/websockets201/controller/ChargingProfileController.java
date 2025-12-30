package com.ocpp201.websocket.websockets201.controller;

import com.ocpp201.websocket.websockets201.dto.ApiResponse;
import com.ocpp201.websocket.websockets201.dto.ChargingProfileRequest;
import com.ocpp201.websocket.websockets201.service.ChargingProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/charging-profiles")
@RequiredArgsConstructor
@Slf4j
public class ChargingProfileController {

//    private final ChargingProfileService service;
//
//    @PostMapping("/apply")
//    public ResponseEntity<String> apply(
//            @RequestBody ChargingProfileRequest request
//    ) {
//        service.applyProfile(request);
//        return ResponseEntity.ok("Charging profile applied");
//    }
//
//    @PostMapping("/clear")
//    public ResponseEntity<String> clear(
//            @RequestParam String chargeBoxId,
//            @RequestParam Integer evseId,
//            @RequestParam String profileId
//    ) {
//        service.clearChargingProfile(
//                chargeBoxId,
//                evseId,
//                profileId
//        );
//        return ResponseEntity.ok("Charging profile cleared");
//    }


    private final ChargingProfileService service;

    // ================= APPLY =================
    @PostMapping("/apply")
    public ResponseEntity<ApiResponse<Map<String, Object>>> apply(
            @RequestBody ChargingProfileRequest request
    ) {

        log.info(
                "[API][IN ] ApplyChargingProfile cp={} evse={} purpose={} limit={}{}",
                request.getChargeBoxId(),
                request.getEvseId(),
                request.getChargingProfilePurpose(),
                request.getLimit(),
                request.getChargingRateUnit()
        );

        Map<String, Object> result =
                service.applyProfileAndReturn(request);

        return ResponseEntity.ok(
                ApiResponse.<Map<String, Object>>builder()
                        .timestamp(Instant.now())
                        .status("SUCCESS")
                        .message("Charging profile applied successfully")
                        .data(result)
                        .build()
        );
    }

    // ================= CLEAR =================
    @PostMapping("/clear")
    public ResponseEntity<ApiResponse<Map<String, Object>>> clear(
            @RequestParam String chargeBoxId,
            @RequestParam Integer evseId,
            @RequestParam String profileId
    ) {

        log.info(
                "[API][IN ] ClearChargingProfile cp={} evse={} profileId={}",
                chargeBoxId,
                evseId,
                profileId
        );

        Map<String, Object> result =
                service.clearChargingProfileAndReturn(
                        chargeBoxId,
                        evseId,
                        profileId
                );

        return ResponseEntity.ok(
                ApiResponse.<Map<String, Object>>builder()
                        .timestamp(Instant.now())
                        .status("SUCCESS")
                        .message("Charging profile cleared successfully")
                        .data(result)
                        .build()
        );
    }
}
