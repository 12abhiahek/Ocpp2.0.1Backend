package com.ocpp201.websocket.websockets201.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetVariablesRequest {

    private List<VariableData> variableData;

}
