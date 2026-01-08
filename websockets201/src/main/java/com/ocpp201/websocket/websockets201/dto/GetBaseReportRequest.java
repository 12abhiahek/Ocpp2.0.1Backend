package com.ocpp201.websocket.websockets201.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBaseReportRequest {
    private Integer requestId;
    private String reportBase; // FullInventory / ConfigurationInventory
    private String chargeBoxId;
    private String state;
    private Instant timestamp;
}
