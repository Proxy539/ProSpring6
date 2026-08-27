package com.apress.prospring6.thirteen;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class LetterSender {

    @Value("#{kafkaApplication.sendingTopic}")
    public String sendingToTopicName;

    @Value("#{kafkaApplication.receivingTopic}")
    private String sender;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    //make application configurable
    public void send(Letter letter) {
        log.info(">>>> -> [{}] Sending letter -> {}", sender, letter);
        kafkaTemplate.send(sendingToTopicName, letter);
    }

}
