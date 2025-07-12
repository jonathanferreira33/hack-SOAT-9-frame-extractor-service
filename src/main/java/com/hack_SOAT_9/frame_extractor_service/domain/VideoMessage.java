package com.hack_SOAT_9.frame_extractor_service.domain;

import com.hack_SOAT_9.frame_extractor_service.utils.VideoProcessingStatus;

import java.io.Serializable;

public record VideoMessage (
    String userId,
    String videoName,
    String outputDir,
    String videoPath,
    String queuedAt,
    VideoProcessingStatus status
) implements Serializable {
    public static VideoMessage withVideoNameAndPath(String videoName, String videoPath) {
        return new VideoMessage(
                null,
                videoName,
                null,
                videoPath,
                null,
                VideoProcessingStatus.PROCESSING
        );
    }

    public static VideoMessage full(String userId, String videoName, String outputDir,
                                    String videoPath, String queuedAt, VideoProcessingStatus status) {
        return new VideoMessage(
                userId,
                videoName,
                outputDir,
                videoPath,
                queuedAt,
                status
        );
    }
}

