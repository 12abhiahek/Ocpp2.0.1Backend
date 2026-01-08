package com.ocpp201.websocket.websockets201.handler;

import com.ocpp201.websocket.websockets201.dto.NotifyEventRequest;
import com.ocpp201.websocket.websockets201.model.EventLog;
import com.ocpp201.websocket.websockets201.model.OcppMessage;
import com.ocpp201.websocket.websockets201.repository.EventLogRepository;
import com.ocpp201.websocket.websockets201.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class NotifyEventHandler implements HandlerStrategy{

    private final EventService eventService;
    private final ObjectMapper mapper = new ObjectMapper();
    private final EventLogRepository repository;

    public NotifyEventHandler(EventService eventService, EventLogRepository repo, EventLogRepository repository) {
        this.eventService = eventService;
        this.repository = repository;
    }

    @Override
    public String action() {
        return "NotifyEvent";
    }

    @Override
    public void handle(WebSocketSession session, OcppMessage msg) {

        try {
            // Convert payload JsonNode → DTO
            NotifyEventRequest event =
                    mapper.treeToValue(
                            msg.getPayload(),
                            NotifyEventRequest.class
                    );

            log.warn(
                    "[EVENT] type={} severity={} component={} desc={}",
                    event.getEventType(),
                    event.getSeverity(),
                    event.getComponent(),
                    event.getDescription()
            );

            eventService.save(event);
//new code block
            EventLog logEntry = new EventLog();
            logEntry.setChargeBoxId(session.getUri().getPath().split("/")[3]);
            logEntry.setEventType(event.getEventType());
            logEntry.setSeverity(event.getSeverity());
            logEntry.setComponent(event.getComponent());
            logEntry.setDescription(event.getDescription());
            logEntry.setCreatedAt(event.getTimestamp());
            repository.save(logEntry);

        } catch (Exception e) {
            log.error("Failed to parse NotifyEvent payload", e);
        }
    }

}
