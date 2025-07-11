package com.hack_SOAT_9.frame_extractor_service.consumer;

import com.hack_SOAT_9.frame_extractor_service.domain.VideoMessage;
import com.hack_SOAT_9.frame_extractor_service.service.VideoProcessorService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class VideoUploadListener {
    private final VideoProcessorService service;

    public VideoUploadListener(VideoProcessorService service) {
        this.service = service;
    }

    @RabbitListener(queues = "video.uploaded")
    public void handleVideoUpload(VideoMessage event) {
        service.processVideo(event);
    }
}
