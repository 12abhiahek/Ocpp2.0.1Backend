package com.ocpp201.websocket.websockets201.dto;

import lombok.Data;

import java.util.List;

@Data
public class MeterValuesRequest {

    private Integer evseId;
    private String transactionId;
    private List<MeterValueDto> meterValue;
}
