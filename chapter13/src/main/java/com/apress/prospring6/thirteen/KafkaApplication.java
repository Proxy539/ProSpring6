package com.apress.prospring6.thirteen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class KafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

    @Value("${app.sending.topic.name}")
    public String sendingTopic;

    @Value("${app.receiving.topic.name}")
    public String receivingTopic;

    @Bean
    public CommandLineRunner initCmd() {
        return args -> log.info(" >>> Sender {} ready to send letters to {}",
                receivingTopic, sendingTopic);
    }
}
