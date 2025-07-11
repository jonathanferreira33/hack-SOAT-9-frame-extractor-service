package com.hack_SOAT_9.frame_extractor_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String VIDEO_UPLOAD_QUEUE = "video.upload";

    @Bean
    public Queue videoUploadQueue() {
        return new Queue(VIDEO_UPLOAD_QUEUE, true);
    }
}