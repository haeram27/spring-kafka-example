package com.example.kafka.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.event.EventListener;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class KafkaConsumerApplication {

    @Value("${start.message}")
    private String hello;

    @EventListener
    public void init(ApplicationStartingEvent event) {
        log.info("******** received by event ********" + event.toString());
        log.info(String.valueOf(System.nanoTime()));
        System.out.println("******** received by event ********" + event.toString());
    }

    @EventListener
    public void init(ApplicationContextInitializedEvent event) {
        log.info("******** received by event ********" + event.toString());
        log.info(String.valueOf(System.nanoTime()));
    }

    @EventListener
    public void init(ApplicationEnvironmentPreparedEvent event) {
        log.info("******** received by event ********" + event.toString());
        log.info(String.valueOf(System.nanoTime()));
    }

    @EventListener
    public void init(ApplicationPreparedEvent event) {
        log.info("******** received by event ********" + event.toString());
        log.info(String.valueOf(System.nanoTime()));
    }

    @EventListener
    public void init(ApplicationStartedEvent event) {
        log.info("******** received by event ********" + event.toString());
        log.info(String.valueOf(System.nanoTime()));
    }

    @EventListener
    public void init(ApplicationReadyEvent event) {
        log.info("******** received by event ********" + event.toString());
        log.info(String.valueOf(System.nanoTime()));
        log.info("${start.message}");
    }

    @EventListener
    public void init(ApplicationFailedEvent event) {
        log.info("******** received by event ********" + event.toString());
        log.info(String.valueOf(System.nanoTime()));
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);

        new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        log.info("heart beat");
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    log.error("Exception: ", e);
                }
            }
        }.start();
    }
}
