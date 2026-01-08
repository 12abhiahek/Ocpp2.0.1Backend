package com.ocpp201.websocket.websockets201.controller;

import com.ocpp201.websocket.websockets201.dto.*;
import com.ocpp201.websocket.websockets201.model.DeviceVariable;
import com.ocpp201.websocket.websockets201.model.EventLog;
import com.ocpp201.websocket.websockets201.model.OcppMessage;
import com.ocpp201.websocket.websockets201.service.DeviceManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
@Slf4j
public class DeviceManagementController {
    private final DeviceManagementService service;
    private final com.ocpp201.websocket.websockets201.repository.DeviceVariableRepository deviceVariableRepository;
    private final com.ocpp201.websocket.websockets201.repository.EventLogRepository eventLogRepository;
    private final com.ocpp201.websocket.websockets201.service.DiagnosticService diagnosticService;


    // -------- ChangeAvailability --------

    @PostMapping("/change-availability")
    public ResponseEntity<ApiResponse<ChangeAvailabilityResponse>>
    changeAvailability(
            @RequestParam String chargeBoxId,
            @RequestBody ChangeAvailabilityRequest request
    ) {
        String commandId =
                service.sendChangeAvailability(chargeBoxId, request);

        ChangeAvailabilityResponse response =
                ChangeAvailabilityResponse.builder()
                        .commandId(commandId)
                        .chargeBoxId(chargeBoxId)
                        .evseId(request.getEvseId())
                        .requestedStatus(request.getOperationalStatus())
                        .commandState("SENT")
                        .timestamp(Instant.now())
                        .build();

        return ResponseEntity.ok(
                ApiResponse.<ChangeAvailabilityResponse>builder()
                        .status("ACCEPTED")
                        .message("ChangeAvailability command sent")
                        .data(response)
                        .timestamp(Instant.now())
                        .build()
        );
    }


    @GetMapping("/variables/{chargeBoxId}")
    public List<DeviceVariable> getDeviceVariables(
            @PathVariable String chargeBoxId
    ) {
        return deviceVariableRepository.findByChargeBoxId(chargeBoxId);
    }

    @GetMapping("/events")
    public List<EventLog> getEvents(
            @RequestParam(required = false) Integer severity
    ) {
        if (severity != null) {
            return eventLogRepository.findBySeverityGreaterThanEqual(severity);
        }
        return eventLogRepository.findAll();
    }

    @PostMapping("/diagnostics/logs")
    public ResponseEntity<Void> getLogs(
            @RequestParam String chargeBoxId,
            @RequestBody GetLogRequest req
    ) {
        diagnosticService.requestLogs(chargeBoxId, req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{cpId}/getvariables")
    public OcppMessage getVariables(@PathVariable String cpId) throws Exception {
        return service.getVariables(cpId);
    }

    @PostMapping("/{cpId}/setvariables")
    public OcppMessage setVariables(
            @PathVariable String cpId,
            @RequestParam String value
    ) throws Exception {
        return service.setVariables(cpId, value);
    }

    @PostMapping("/get-base-report")
    public ResponseEntity<ApiResponse<GetBaseReportRequest>> getBaseReport(
            @RequestParam String chargeBoxId,
            @RequestBody GetBaseReportRequest request
    ) {
        GetBaseReportRequest command =
                service.sendGetBaseReport(chargeBoxId, request);

        return ResponseEntity.ok(
                ApiResponse.<GetBaseReportRequest>builder()
                        .status("ACCEPTED")
                        .message("GetBaseReport command sent")
                        .data(command)
                        .timestamp(Instant.now())
                        .build()
        );
    }




}
