package com.hack_SOAT_9.frame_extractor_service.domain;

public class VideoMessage {
    private String id;
    private String userId;
    private String videoName;
    private String videoPath;
    private String status;

    public VideoMessage() {}

    public VideoMessage(String id, String userId, String videoName, String videoPath, String status) {
        this.id = id;
        this.userId = userId;
        this.videoName = videoName;
        this.status = status;
        this.videoPath = videoPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}