package com.hack_SOAT_9.frame_extractor_service.repository;

import com.hack_SOAT_9.frame_extractor_service.domain.entity.VideoProcessingEventEntity;
import com.hack_SOAT_9.frame_extractor_service.utils.VideoProcessingStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class VideoProcessingEventRepositoryTest {

    @Autowired
    private VideoProcessingEventRepository repository;

    @Test
    void testFindAllByUserID() {
        // Arrange
        VideoProcessingEventEntity entity = new VideoProcessingEventEntity();
        entity.setUserID("user123");
        entity.setVideoName("example.mp4");
        entity.setVideoPath("/videos/example.mp4");
        entity.setOutputDir("/output");
        entity.setQueuedAt(LocalDateTime.now());
        entity.setCompletedAt(LocalDateTime.now().plusMinutes(2));
        entity.setStatus(VideoProcessingStatus.SUCCESS);

        repository.save(entity);

        // Act
        List<VideoProcessingEventEntity> results = repository.findAllByUserID("user123");

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getVideoName()).isEqualTo("example.mp4");
    }
}