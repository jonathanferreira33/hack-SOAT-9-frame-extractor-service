package com.hack_SOAT_9.frame_extractor_service.repository;

import com.hack_SOAT_9.frame_extractor_service.domain.entity.VideoProcessingEventEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class VideoProcessingEventRepositoryTest {

    @Autowired
    private VideoProcessingEventRepository repository;

    @Test
    void testFindAllByUserID() {
        VideoProcessingEventEntity event1 = new VideoProcessingEventEntity();
        event1.setUserID("user123");
        repository.save(event1);

        VideoProcessingEventEntity event2 = new VideoProcessingEventEntity();
        event2.setUserID("user123");
        repository.save(event2);

        VideoProcessingEventEntity event3 = new VideoProcessingEventEntity();
        event3.setUserID("otherUser");
        repository.save(event3);

        List<VideoProcessingEventEntity> result = repository.findAllByUserID("user123");

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(e -> "user123".equals(e.getUserID()));
    }
}