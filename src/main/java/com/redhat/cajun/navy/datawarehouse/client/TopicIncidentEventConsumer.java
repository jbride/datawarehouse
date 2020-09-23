package com.redhat.cajun.navy.datawarehouse.client;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.redhat.cajun.navy.datawarehouse.DatawarehouseService;
import com.redhat.cajun.navy.datawarehouse.model.Incident;
import com.redhat.cajun.navy.datawarehouse.model.MissionReport;
import com.redhat.cajun.navy.datawarehouse.model.cmd.incident.IncidentCommand;
import com.redhat.cajun.navy.datawarehouse.util.Constants;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.infinispan.client.hotrod.MetadataValue;
import org.infinispan.client.hotrod.exceptions.HotRodClientException;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.json.Json;

/*  Purpose:
 *    Consume the IncidentUpdatedEvent (produced by incident-service) with a status of PICKEDUP from topic-incident-event Kafka topic
 *    This message contains the actual number of people that were picked up
*/
@ApplicationScoped
public class TopicIncidentEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger("TopicIncidentEventConsumer");
    private static final String LOG_INCIDENT_EVENT_CONSUMER = "er.demo.LOG_INCIDENT_EVENT_COMSUMER";
    private boolean log = true;

    @Inject
    DatawarehouseService dService;

    @Inject
    @ConfigProperty(name = LOG_INCIDENT_EVENT_CONSUMER, defaultValue = "False")
    String logRawEvents;

    @PostConstruct
    public void start() {
        log = Boolean.parseBoolean(logRawEvents);
        logger.info("start() will log raw messaging events = " + log);
    }

    @Incoming("topic-incident-event")
    @Blocking // Ensure execution occurs on a worker thread rather than on the event loop thread (which would never be blocked)
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)  // Ack message prior to message processing
    public void process(String topicIncidentEvent) {
        if (StringUtils.isEmpty(topicIncidentEvent)) {
            logger.warn("process() empty message body");
            return;
        }
        if (this.log) {
            logger.info("process() topicIncidentEvent = " + topicIncidentEvent);
        }

        if (StringUtils.contains(topicIncidentEvent, IncidentCommand.MessageTypes.IncidentUpdatedEvent.name())) {
            IncidentCommand icObj = Json.decodeValue(topicIncidentEvent, IncidentCommand.class);
            Incident iObj = icObj.getBody();
            if (Incident.Statuses.PICKEDUP.name().equals(iObj.getStatus())) {

                try {
                    // If event = PICKEDUP, then update MissionReport with numberRescued
                    updateCache(icObj.getId(), iObj);
                } catch(HotRodClientException x) {
                        logger.error(icObj.getId()+" : "+Constants.HOTROD_CLIENT_EXCEPTION+ " : processing IncidentUpdatedEvent() .... will try again");
                        try {
                            Thread.sleep(250);
                            updateCache(icObj.getId(), iObj);
                        }catch(Exception y){
                            logger.error(icObj.getId()+" Error processing IncidentUpdatedEvent() after second attempt !!!");
                            y.printStackTrace();
                        }
                } catch(Exception x) {
                    logger.error(icObj.getId()+" Error processing IncidentUpdatedEvent()");
                    x.printStackTrace();
                }
            }
            
        }
        
    }
    
    private void updateCache(String id, Incident iObj) throws Exception {

        MetadataValue<MissionReport> mValue = dService.getMissionReportCache().getWithMetadata(iObj.getId());
        if(mValue != null) {
            MissionReport mReport = mValue.getValue();
            mReport.setNumberRescued(iObj.getNumberOfPeople());
            dService.getMissionReportCache().replaceWithVersion(iObj.getId(), mReport, mValue.getVersion());
            logger.info(mReport.getIncidentId()+" : updated with following # of rescued people: "+iObj.getNumberOfPeople());
        }else {
            logger.error(id+" "+Constants.NO_REPORT_FOUND_EXCEPTION);
        }
        
    }

}
