package com.redhat.cajun.navy.datawarehouse.model.cmd.mission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redhat.cajun.navy.datawarehouse.model.MissionReport;
import io.vertx.core.json.Json;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MissionCommand {

    // TO-DO: Mission Failed ??
    public static enum MessageTypes {
        MissionStartedEvent, MissionPickedUpEvent, MissionCompletedEvent, UpdateResponderCommand, CreateMissionCommand
    };

    private String id;
    private String messageType;
    private String invokingService;
    private long timestamp;
    private Body body;

    public String getId() {
        return id;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getInvokingService() {
        return invokingService;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public MissionReport getMissionReport() {
        return body;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setInvokingService(String invokingService) {
        this.invokingService = invokingService;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return Json.encode(this);
    }

}
