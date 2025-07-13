package com.hack_SOAT_9.frame_extractor_service.service;

import com.hack_SOAT_9.frame_extractor_service.domain.entity.VideoProcessingEventEntity;
import com.hack_SOAT_9.frame_extractor_service.repository.VideoProcessingEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class VideoEventServiceTest {

    private VideoProcessingEventRepository mockRepository;
    private VideoEventService service;

    @BeforeEach
    void setUp() {
        mockRepository = mock(VideoProcessingEventRepository.class);
        service = new VideoEventService();
        var field = VideoEventService.class.getDeclaredFields()[0];
        field.setAccessible(true);
        try {
            field.set(service, mockRepository);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldSaveEventUsingRepository() {
        VideoProcessingEventEntity dummyEvent = new VideoProcessingEventEntity();
        service.saveEvent(dummyEvent);
        verify(mockRepository, times(1)).save(dummyEvent);
    }
}
