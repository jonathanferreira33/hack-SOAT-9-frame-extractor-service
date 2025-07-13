package com.hack_SOAT_9.frame_extractor_service.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;

import static org.junit.jupiter.api.Assertions.*;

class RabbitMQConfigTest {

    @Test
    void queueBeanShouldBeCreated() {
        RabbitMQConfig config = new RabbitMQConfig();
        Queue queue = config.videoUploadQueue();

        assertNotNull(queue);
        assertEquals("video.upload", queue.getName());
        assertTrue(queue.isDurable());
    }
}
