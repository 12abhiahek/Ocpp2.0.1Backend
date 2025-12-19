package com.ocpp201.websocket.websockets201.model;


import lombok.Data;
import tools.jackson.databind.JsonNode;

@Data
public class OcppMessage {

    private int messageTypeId;   // 2=CALL, 3=CALLRESULT, 4=CALLERROR
    private String uniqueId;
    private String action;       // only for CALL
    private JsonNode payload;
}
