package com.redhat.cajun.navy.datawarehouse.model.cmd.incident;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IncidentCommand {

    public static enum MessageTypes {
        IncidentReportedEvent, IncidentAssignmentEvent, IncidentUpdatedEvent
    };

    private String id;
    private String messageType;
    private String invokingService;
    private long timestamp;
    private Body body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getInvokingService() {
        return invokingService;
    }

    public void setInvokingService(String invokingService) {
        this.invokingService = invokingService;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

}