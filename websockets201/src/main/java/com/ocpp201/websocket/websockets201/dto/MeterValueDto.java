package com.ocpp201.websocket.websockets201.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class MeterValueDto {

    private Instant timestamp;
    private List<SampledValueDto> sampledValue;
}
