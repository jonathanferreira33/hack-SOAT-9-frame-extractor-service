package com.hack_SOAT_9.frame_extractor_service.service;

import com.hack_SOAT_9.frame_extractor_service.domain.entity.VideoProcessingEventEntity;
import com.hack_SOAT_9.frame_extractor_service.repository.VideoProcessingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoEventService {

    @Autowired
    private VideoProcessingEventRepository repository;

    public void saveEvent(VideoProcessingEventEntity event) {
        repository.save(event);
    }
}
