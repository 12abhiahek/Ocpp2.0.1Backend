package com.ocpp201.websocket.websockets201.dto;

import lombok.Data;

@Data
public class SampledValueDto {
    private String value;
    private String measurand;
    private String readingContext;
    private String format;
    private String location;
    private String phase;
    private UnitOfMeasureDTO unitOfMeasure;
}
