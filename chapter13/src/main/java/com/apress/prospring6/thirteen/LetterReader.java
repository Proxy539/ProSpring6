package com.apress.prospring6.thirteen;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LetterReader {

    @Value("#{kafkaApplication.receivingTopic}")
    private String receivingTopicName;

    @KafkaListener(topics = "#{kafkaApplication.receivingTopic}",
    groupId = "${spring.kafka.consumer.group-id}",
    containerFactory = "kafkaListenerContainerFactory")
    public void consume(ConsumerRecord<String, String> cr) {
        log.info("<<<< Receiving message at -> {}", cr.timestamp());
        log.info("<<<< Receiving message on topic -> {}", cr.topic());
        log.info("<<<< Receiving message on partition -> {}", cr.partition());
        log.info("<<<< Receiving message with headers -> {}", cr.headers());
        log.info("<<<< Receiving message with key -> {}", cr.key());
        log.info("<<<< Receiving message with value -> {}", cr.value());
    }
}
