package com.example.kafka.producer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class KafkaProducerApplication {

    private static AtomicInteger ai = new AtomicInteger(0);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

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
        new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        sendMessage("haeram27", String.valueOf(ai.incrementAndGet()));
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    log.error("Exception: ", e);
                }
            }
        }.start();
    }

    @EventListener
    public void init(ApplicationFailedEvent event) {
        log.info("******** received by event ********" + event.toString());
        log.info(String.valueOf(System.nanoTime()));
    }

    public void sendMessage(String topic, String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[" + message + "] with offset=["
                        + result.getRecordMetadata().offset() + "]");
            } else {
                log.info("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);

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
