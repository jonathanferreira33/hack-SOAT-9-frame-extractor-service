package com.hack_SOAT_9.frame_extractor_service.consumer;

import com.hack_SOAT_9.frame_extractor_service.domain.VideoMessage;
import com.hack_SOAT_9.frame_extractor_service.service.VideoProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VideoUploadListenerTest {

    @Mock
    private VideoProcessorService processorService;

    private VideoUploadListener listener;

    @BeforeEach
    void setUp() {
        listener = new VideoUploadListener(processorService);
    }

    @Test
    void shouldProcessVideoWhenMessageReceived() {
        VideoMessage mockMessage = VideoMessage.withVideoNameAndPath("video.mp4", "/dummy/path/video.mp4");

        listener.handleVideoUpload(mockMessage);

        verify(processorService, times(1)).processVideo(mockMessage);
    }
}

