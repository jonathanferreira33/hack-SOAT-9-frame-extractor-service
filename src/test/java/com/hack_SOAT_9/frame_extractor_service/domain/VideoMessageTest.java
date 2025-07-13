package com.hack_SOAT_9.frame_extractor_service.domain;

import com.hack_SOAT_9.frame_extractor_service.utils.VideoProcessingStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VideoMessageTest {

    @Test
    void testWithVideoNameAndPath() {
        String videoName = "video.mp4";
        String videoPath = "/path/to/video.mp4";

        VideoMessage message = VideoMessage.withVideoNameAndPath(videoName, videoPath);

        assertNull(message.userId());
        assertEquals(videoName, message.videoName());
        assertNull(message.outputDir());
        assertEquals(videoPath, message.videoPath());
        assertNull(message.queuedAt());
        assertEquals(VideoProcessingStatus.PROCESSING, message.status());
    }

    @Test
    void testFull() {
        String userId = "user123";
        String userEmail = "user@email.com";
        String videoName = "video.mp4";
        String outputDir = "/output";
        String videoPath = "/path/to/video.mp4";
        String queuedAt = "2025-07-12T10:00:00";
        VideoProcessingStatus status = VideoProcessingStatus.SUCCESS;

        VideoMessage message = VideoMessage.full(userId, userEmail, videoName, outputDir, videoPath, queuedAt, status);

        assertEquals(userId, message.userId());
        assertEquals(videoName, message.videoName());
        assertEquals(outputDir, message.outputDir());
        assertEquals(videoPath, message.videoPath());
        assertEquals(queuedAt, message.queuedAt());
        assertEquals(status, message.status());
    }
}
