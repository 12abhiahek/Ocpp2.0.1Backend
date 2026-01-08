package com.ocpp201.websocket.websockets201.dto;

import lombok.Data;

import java.util.List;

@Data
public class NotifyReportRequest {
    private Integer requestId;
    private Boolean tbc;
    private List<Object> reportData;
}
