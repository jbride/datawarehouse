package com.redhat.cajun.navy.datawarehouse.client;

import com.redhat.cajun.navy.datawarehouse.util.Constants;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.redhat.cajun.navy.datawarehouse.MessageProcessingService;
import com.redhat.cajun.navy.datawarehouse.model.MissionReport;
import com.redhat.cajun.navy.datawarehouse.model.cmd.createMissionCommand.Body;
import com.redhat.cajun.navy.datawarehouse.model.cmd.createMissionCommand.CreateMissionCommand;
import com.redhat.cajun.navy.datawarehouse.model.cmd.mission.MissionCommand;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.Json;

/*   Purpose:
 *       Capture initial CreateMissionCommand message (which contains the processInstanceId) from process-service
 * 
 *   Consumes the following message type on the topic-mission-command kafka topic: 
 *
 *   {"id":"eece9ab4-9cb6-469b-b7f1-2f490dae599a","messageType":"CreateMissionCommand","invokingService":"IncidentProcessService","timestamp":1579820261957,"body":{"incidentId":"97b48ce8-2bf2-47c2-879c-b8ba8468c01c","responderId":"189","responderStartLat":"34.24630","responderStartLong":"-77.95140","incidentLat":"34.16423","incidentLong":"-77.85931","destinationLat":"34.17060","destinationLong":"-77.94900","processId":"220"}}
 * 
 */
@ApplicationScoped
public class TopicMissionCommandConsumer {

    private static final Logger logger = LoggerFactory.getLogger("TopicMissionCommandConsumer");
    private static final String LOG_MISSION_COMMAND_COMSUMER = "er.demo.LOG_MISSION_COMMAND_COMSUMER";
    private boolean log = true;

    @Inject
    @ConfigProperty(name = LOG_MISSION_COMMAND_COMSUMER, defaultValue = "False")
    String logRawEvents;

    @Inject
    MessageProcessingService mService;

    @PostConstruct
    public void start() {
        log = Boolean.parseBoolean(logRawEvents);
        logger.info("start() will log raw messaging events = " + log);
    }

    @Incoming("topic-mission-command")
    @Blocking // Ensure execution occurs on a worker thread rather than on the event loop thread (which whould never be blocked)
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)  // Ack message prior to message processing
    public void process(String topicCommand) {
        if (StringUtils.isEmpty(topicCommand)) {
            logger.warn("process() empty message body");
            return;
        }
        if (this.log) {
            logger.info("process() topic-mission-command = " + topicCommand);
        }

        if (StringUtils.contains(topicCommand, MissionCommand.MessageTypes.CreateMissionCommand.name())) {
            CreateMissionCommand icObj = Json.decodeValue(topicCommand, CreateMissionCommand.class);
            Body iObj = icObj.getBody();
            String pInstanceId = iObj.getProcessId();
            if (StringUtils.isNotEmpty(pInstanceId)) {

                MissionReport mReport = new MissionReport();
                mReport.setId(icObj.getId());
                mReport.setIncidentId(iObj.getIncidentId());
                mReport.setProcessInstanceId(pInstanceId);
                mReport.setResponderId(iObj.getResponderId());
                mService.processMissionStart(mReport);

            } else {
                logger.error(Constants.NO_PROCESS_INSTANCE_ID_EXCEPTION
                        + "  :  No pInstanceId found for CreateMissionCommand with incidentId = "
                        + iObj.getIncidentId());
            }

        }
    }

}
