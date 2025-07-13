package com.hack_SOAT_9.frame_extractor_service.utils;

import com.hack_SOAT_9.frame_extractor_service.domain.VideoMessage;
import com.hack_SOAT_9.frame_extractor_service.domain.entity.VideoProcessingEventEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VideoMessageMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public static VideoProcessingEventEntity toEntity(VideoMessage message) {
        if (message == null) return null;

        VideoProcessingEventEntity entity = new VideoProcessingEventEntity();

        entity.setUserID(message.userId());
        entity.setVideoName(message.videoName());
        entity.setOutputDir(message.outputDir());
        entity.setVideoPath(message.videoPath());

        if (message.queuedAt() != null) {
            entity.setQueuedAt(LocalDateTime.parse(message.queuedAt(), FORMATTER));
        }

        entity.setStatus(message.status());
        entity.setCompletedAt(null);

        return entity;
    }

    public static VideoMessage toRecord(VideoProcessingEventEntity entity) {
        if (entity == null) return null;

        String queuedAtStr = null;
        if (entity.getQueuedAt() != null) {
            queuedAtStr = entity.getQueuedAt().format(FORMATTER);
        }

        return new VideoMessage(
                entity.getUserID(),
                entity.getVideoName(),
                entity.getOutputDir(),
                entity.getVideoPath(),
                queuedAtStr,
                entity.getStatus()
        );
    }
}
