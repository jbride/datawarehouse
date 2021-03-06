package com.redhat.cajun.navy.datawarehouse.client;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TopicIncidentCommandConsumer {

    private static final Logger logger = LoggerFactory.getLogger("TopicIncidentCommandConsumer");
    private static final String LOG_INCIDENT_COMMAND_CONSUMER = "er.demo.LOG_INCIDENT_COMMAND_COMSUMER";
    private boolean log = true;

    @Inject
    @ConfigProperty(name = LOG_INCIDENT_COMMAND_CONSUMER, defaultValue = "False")
    String logRawEvents;

    @PostConstruct
    public void start() {
        log = Boolean.parseBoolean(logRawEvents);
        logger.info("start() will log raw messaging events = " + log);
    }

    @Incoming("topic-incident-command")
    @Blocking // Ensure execution occurs on a worker thread rather than on the event loop thread (which whould never be blocked)
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)  // Ack message prior to message processing
    public void process(String topicIncidentCommand) {
        if (this.log) {
            logger.info("process() topicIncidentCommand = " + topicIncidentCommand);
        }
    }

}
