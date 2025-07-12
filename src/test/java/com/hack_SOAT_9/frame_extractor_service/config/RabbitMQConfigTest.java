package com.hack_SOAT_9.frame_extractor_service.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.amqp.core.Queue;
import org.springframework.context.ApplicationContext;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RabbitMQConfig.class)
class RabbitMQConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void queueBeanShouldBeCreated() {
        Queue queue = context.getBean(Queue.class);
        assertNotNull(queue);
        assertEquals("video.upload", queue.getName());
        assertTrue(queue.isDurable());
    }
}