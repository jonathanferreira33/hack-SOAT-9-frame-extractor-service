package com.hack_SOAT_9.frame_extractor_service.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ErrorEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ErrorEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishError(String email, String errorMessage) {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("errorMessage", errorMessage);

        rabbitTemplate.convertAndSend("video.errors", "", payload);
    }
}