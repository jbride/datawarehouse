package com.redhat.cajun.navy.datawarehouse.client;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TopicResponderCommandConsumer {

    private static final Logger logger = LoggerFactory.getLogger("TopicResponderCommandConsumer");
    private static final String LOG_TOPIC_RESPONDER = "er.demo.LOG_TOPIC_RESPONDER";
    private boolean log = false;

    @Inject
    @ConfigProperty(name = LOG_TOPIC_RESPONDER, defaultValue = "False")
    String logRawEvents;

    @PostConstruct
    public void start() {
        log = Boolean.parseBoolean(logRawEvents);
        logger.info("start() will log raw messaging events = " + log);
    }

    /*
     * The Mission service sends updates to Kafka on the topic-responder-command
     * when missions are completed to indicate that the Responder is available for a
     * new mission. The Process service sends updates to Kafka on the
     * topic-responder-command when a business process is started
     */
    @Incoming("topic-responder-command")
    public void process(String topicResponderCommand) {
        if (this.log) {
            logger.info("process() responderCommand = " + topicResponderCommand);
        }
    }

}
