package com.hack_SOAT_9.frame_extractor_service.utils;

import com.hack_SOAT_9.frame_extractor_service.domain.VideoMessage;
import com.hack_SOAT_9.frame_extractor_service.domain.entity.VideoProcessingEventEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class VideoMessageMapperTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @Test
    void testToEntity_withValidRecord() {
        String dateTimeStr = "2025-07-12T10:00:00";
        VideoMessage record = new VideoMessage(
                "user123",
                "video.mp4",
                "/output",
                "/path/video.mp4",
                dateTimeStr,
                VideoProcessingStatus.PROCESSING
        );

        VideoProcessingEventEntity entity = VideoMessageMapper.toEntity(record);

        assertNotNull(entity);
        assertEquals("user123", entity.getUserID());
        assertEquals("video.mp4", entity.getVideoName());
        assertEquals("/output", entity.getOutputDir());
        assertEquals("/path/video.mp4", entity.getVideoPath());
        assertEquals(LocalDateTime.parse(dateTimeStr, FORMATTER), entity.getQueuedAt());
        assertEquals(VideoProcessingStatus.PROCESSING, entity.getStatus());
        assertNull(entity.getCompletedAt());
    }

    @Test
    void testToEntity_withNullRecord() {
        assertNull(VideoMessageMapper.toEntity(null));
    }

    @Test
    void testToEntity_withNullQueuedAt() {
        VideoMessage record = new VideoMessage(
                "user123",
                "video.mp4",
                "/output",
                "/path/video.mp4",
                null,
                VideoProcessingStatus.SUCCESS
        );
        VideoProcessingEventEntity entity = VideoMessageMapper.toEntity(record);
        assertNotNull(entity);
        assertNull(entity.getQueuedAt());
        assertEquals(VideoProcessingStatus.SUCCESS, entity.getStatus());
    }

    @Test
    void testToRecord_withValidEntity() {
        VideoProcessingEventEntity entity = new VideoProcessingEventEntity();
        entity.setUserID("user123");
        entity.setVideoName("video.mp4");
        entity.setOutputDir("/output");
        entity.setVideoPath("/path/video.mp4");
        entity.setQueuedAt(LocalDateTime.of(2025, 7, 12, 10, 0));
        entity.setStatus(VideoProcessingStatus.ERROR);

        VideoMessage record = VideoMessageMapper.toRecord(entity);

        assertNotNull(record);
        assertEquals("user123", record.userId());
        assertEquals("video.mp4", record.videoName());
        assertEquals("/output", record.outputDir());
        assertEquals("/path/video.mp4", record.videoPath());
        assertEquals(entity.getQueuedAt().format(FORMATTER), record.queuedAt());
        assertEquals(VideoProcessingStatus.ERROR, record.status());
    }

    @Test
    void testToRecord_withNullEntity() {
        assertNull(VideoMessageMapper.toRecord(null));
    }

    @Test
    void testToRecord_withNullQueuedAt() {
        VideoProcessingEventEntity entity = new VideoProcessingEventEntity();
        entity.setQueuedAt(null);
        VideoMessage record = VideoMessageMapper.toRecord(entity);
        assertNull(record.queuedAt());
    }
}
