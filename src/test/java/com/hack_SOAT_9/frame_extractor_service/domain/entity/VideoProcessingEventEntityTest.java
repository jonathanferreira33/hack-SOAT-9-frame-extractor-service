package com.hack_SOAT_9.frame_extractor_service.domain.entity;

import com.hack_SOAT_9.frame_extractor_service.utils.VideoProcessingStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class VideoProcessingEventEntityTest {

    @Test
    void testGettersAndSetters() {
        VideoProcessingEventEntity entity = new VideoProcessingEventEntity();

        entity.setId(123L);
        entity.setVideoName("video.mp4");
        entity.setVideoPath("/path/to/video.mp4");
        entity.setOutputDir("/output");
        entity.setUserID("user123");

        LocalDateTime now = LocalDateTime.now();
        entity.setQueuedAt(now);
        entity.setCompletedAt(now.plusHours(1));

        entity.setStatus(VideoProcessingStatus.SUCCESS);

        assertEquals(123L, entity.getId());
        assertEquals("video.mp4", entity.getVideoName());
        assertEquals("/path/to/video.mp4", entity.getVideoPath());
        assertEquals("/output", entity.getOutputDir());
        assertEquals("user123", entity.getUserID());
        assertEquals(now, entity.getQueuedAt());
        assertEquals(now.plusHours(1), entity.getCompletedAt());
        assertEquals(VideoProcessingStatus.SUCCESS, entity.getStatus());
    }
}
