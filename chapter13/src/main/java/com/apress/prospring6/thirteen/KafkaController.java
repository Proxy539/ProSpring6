package com.apress.prospring6.thirteen;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/kafka")
public class KafkaController {

    private final LetterSender sender;

    @PostMapping(value = "/send")
    public void sendMessageToKafkaTopic(@RequestBody Letter letter) {
        this.sender.send(letter);
    }
}
