package com.ocpp201.websocket.websockets201.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class MeterValueHeader {

    private Instant timestamp;
    private List<SampledValue> sampledValue;

    @Data
    public static class SampledValue {
        private String value;
        private String measurand;
        private String context;
        private UnitOfMeasure unitOfMeasure;
    }

    @Data
    public static class UnitOfMeasure {
        private String unit;
        private Integer multiplier;
    }
}
