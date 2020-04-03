package com.redhat.cajun.navy.datawarehouse.model.cmd.createMissionCommand;

public class Body {

    private String incidentId;
    private String responderId;
    private String responderStartLat;
    private String responderStartLong;
    private String incidentLat;
    private String incidentLong;
    private String destinationLat;
    private String destinationLong;
    private String processId;

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getResponderId() {
        return responderId;
    }

    public void setResponderId(String responderId) {
        this.responderId = responderId;
    }

    public String getResponderStartLat() {
        return responderStartLat;
    }

    public void setResponderStartLat(String responderStartLat) {
        this.responderStartLat = responderStartLat;
    }

    public String getResponderStartLong() {
        return responderStartLong;
    }

    public void setResponderStartLong(String responderStartLong) {
        this.responderStartLong = responderStartLong;
    }

    public String getIncidentLat() {
        return incidentLat;
    }

    public void setIncidentLat(String incidentLat) {
        this.incidentLat = incidentLat;
    }

    public String getIncidentLong() {
        return incidentLong;
    }

    public void setIncidentLong(String incidentLong) {
        this.incidentLong = incidentLong;
    }

    public String getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(String destinationLat) {
        this.destinationLat = destinationLat;
    }

    public String getDestinationLong() {
        return destinationLong;
    }

    public void setDestinationLong(String destinationLong) {
        this.destinationLong = destinationLong;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

}