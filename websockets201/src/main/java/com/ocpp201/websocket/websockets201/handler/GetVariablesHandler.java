package com.ocpp201.websocket.websockets201.handler;

import com.ocpp201.websocket.websockets201.model.DeviceVariable;
import com.ocpp201.websocket.websockets201.model.OcppMessage;
import com.ocpp201.websocket.websockets201.repository.DeviceVariableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.JsonNode;

import java.time.Instant;

@Component
@Slf4j
public class GetVariablesHandler implements HandlerStrategy {


    private final DeviceVariableRepository repository;

    public GetVariablesHandler(DeviceVariableRepository repository) {
        this.repository = repository;
    }

    @Override
    public String action() {
        return "GetVariables";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage msg) {

        JsonNode results = msg.getPayload().get("getVariableResult");
        if (results == null) return;

        String cpId = session.getUri().getPath().split("/")[3];

        results.forEach(v -> {
            String component = v.get("component").get("name").asText();
            String variable  = v.get("variable").get("name").asText();
            String value     = v.get("attributeValue").asText();

            DeviceVariable deviceVariable =
                    repository.findByChargeBoxIdAndComponentAndVariable(cpId, component, variable)
                            .orElse(new DeviceVariable());

            deviceVariable.setChargeBoxId(cpId);
            deviceVariable.setComponent(component);
            deviceVariable.setVariable(variable);
            deviceVariable.setValue(value);
            deviceVariable.setUpdatedAt(Instant.now());

            repository.save(deviceVariable);
        });

        log.info("[CP][IN ] GetVariables stored for cp={}", cpId);
    }

}
