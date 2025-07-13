package com.hack_SOAT_9.frame_extractor_service.repository;

import com.hack_SOAT_9.frame_extractor_service.domain.entity.VideoProcessingEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoProcessingEventRepository extends JpaRepository<VideoProcessingEventEntity, Long> {
    List<VideoProcessingEventEntity> findAllByUserID(String userId);
}
