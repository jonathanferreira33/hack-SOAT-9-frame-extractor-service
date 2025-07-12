package com.hack_SOAT_9.frame_extractor_service.controller;

import com.hack_SOAT_9.frame_extractor_service.domain.entity.VideoProcessingEventEntity;
import com.hack_SOAT_9.frame_extractor_service.repository.VideoProcessingEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/processing-events")
public class VideoProcessingEventController {


    private final VideoProcessingEventRepository repository;

    @Autowired
    public VideoProcessingEventController(VideoProcessingEventRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/user/{userId}")
    @Cacheable(value = "eventsByUser", key = "#userId")
    public ResponseEntity<List<VideoProcessingEventEntity>> getEventsByUser(@PathVariable String userId) {
        List<VideoProcessingEventEntity> events = repository.findAllByUserID(userId);
        return ResponseEntity.ok(events);
    }
}
