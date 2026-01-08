package com.ocpp201.websocket.websockets201.service;

import com.ocpp201.websocket.websockets201.dto.ChangeAvailabilityRequest;
import com.ocpp201.websocket.websockets201.dto.GetBaseReportRequest;
import com.ocpp201.websocket.websockets201.model.OcppMessage;
import com.ocpp201.websocket.websockets201.routing.MessageRouter;
import com.ocpp201.websocket.websockets201.util.JsonHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceManagementService {

    private final MessageRouter router;

    public GetBaseReportRequest sendGetBaseReport(
            String cpId,
            GetBaseReportRequest request
    ) {

        int requestId = ThreadLocalRandom.current().nextInt(1, 1_000_000);

        Map<String, Object> payload = Map.of(
                "requestId", requestId,
                "reportBase", request.getReportBase()
        );

        send(cpId, "GetBaseReport", payload);

        // Return command metadata (NOT OCPP response)
        return GetBaseReportRequest.builder()
                .requestId(requestId)
                .chargeBoxId(cpId)
                .reportBase(request.getReportBase())
                .state("SENT")
                .timestamp(Instant.now())
                .build();
    }


    public String sendChangeAvailability(
            String cpId,
            ChangeAvailabilityRequest payload
    ) {
        return send(cpId, "ChangeAvailability", payload);
    }


    // CORE SEND METHOD

    public String send(String cpId, String action, Object payload) {

        WebSocketSession ws = router.getSession(cpId);
        if (ws == null || !ws.isOpen()) {
            throw new IllegalStateException(
                    "Charge Point offline: " + cpId
            );
        }

        // Generate unique command ID
        String commandId = JsonHelper.uid();

        try {
            ws.sendMessage(
                    new TextMessage(
                            JsonHelper.call(
                                    commandId,   // ← IMPORTANT
                                    action,
                                    payload
                            )
                    )
            );

            log.info(
                    "[CP][OUT] {} sent to {} cmdId={}",
                    action, cpId, commandId
            );

            return commandId;

        } catch (Exception e) {
            log.error(
                    "[CP][ERROR] Failed to send {} to {}",
                    action, cpId, e
            );
            throw new RuntimeException(
                    "Failed to send OCPP command " + action, e
            );
        }
    }

    public void sendNotifyEvent(
            String cpId,
            String eventType,
            String severity,
            String component,
            String description
    ) {

        Map<String, Object> payload = Map.of(
                "eventType", eventType,
                "severity", severity,
                "component", Map.of(
                        "name", component
                ),
                "description", description,
                "timestamp", Instant.now().toString()
        );

        send(cpId, "NotifyEvent", payload);
    }


    // ---------------- GetVariables ----------------

    public OcppMessage getVariables(String cpId) throws Exception {

        Map<String, Object> payload = Map.of(
                "getVariableData", List.of(
                        Map.of(
                                "component", Map.of("name", "EVSE"),
                                "variable", Map.of("name", "AvailabilityState")
                        )
                )
        );

        return router
                .sendCall(cpId, "GetVariables", payload)
                .get(15, TimeUnit.SECONDS);
    }

    // ---------------- SetVariables ----------------

    public OcppMessage setVariables(String cpId, String value) throws Exception {

        Map<String, Object> payload = Map.of(
                "setVariableData", List.of(
                        Map.of(
                                "component", Map.of("name", "EVSE"),
                                "variable", Map.of("name", "AvailabilityState"),
                                "attributeValue", value
                        )
                )
        );

        return router.sendCall(cpId, "SetVariables", payload).get();
    }

    // ---------------- ChangeAvailability ----------------

    public OcppMessage changeAvailability(String cpId, int evseId)
            throws Exception {

        Map<String, Object> payload = Map.of(
                "operationalStatus", "Inoperative",
                "evse", Map.of("id", evseId)
        );

        return router.sendCall(cpId, "ChangeAvailability", payload).get();
    }

}
