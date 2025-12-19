package com.ocpp201.websocket.websockets201.util;


import com.ocpp201.websocket.websockets201.model.OcppMessage;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

public class JsonHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static OcppMessage parse(String raw) {
        try {
            ArrayNode arr = (ArrayNode) mapper.readTree(raw);

            OcppMessage msg = new OcppMessage();
            msg.setMessageTypeId(arr.get(0).asInt());
            msg.setUniqueId(arr.get(1).asText());
            if (msg.getMessageTypeId() == 2 && arr.size() >= 4) { // CALL
                msg.setAction(arr.get(2).asText());
                msg.setPayload(arr.get(3));
            } else if (msg.getMessageTypeId() == 3 && arr.size() >= 3) { // CALLRESULT
                msg.setPayload(arr.get(2));
            }
            return msg;
        } catch (Exception e) {
            throw new RuntimeException("Invalid OCPP message: " + raw, e);
        }
    }

    public static ObjectNode obj() {
        return mapper.createObjectNode();
    }

    public static String result(String uniqueId, JsonNode payload) {
        ArrayNode arr = mapper.createArrayNode();
        arr.add(3); // CALLRESULT
        arr.add(uniqueId);
        arr.add(payload);
        return arr.toString();
    }

    public static String error(String uniqueId, String errorCode, String desc) {
        ArrayNode arr = mapper.createArrayNode();
        arr.add(4);
        arr.add(uniqueId);
        arr.add(errorCode);
        arr.add(desc);
        arr.add(obj());
        return arr.toString();
    }
}
