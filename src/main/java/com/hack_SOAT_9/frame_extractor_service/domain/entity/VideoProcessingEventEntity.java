package com.hack_SOAT_9.frame_extractor_service.domain.entity;

import com.hack_SOAT_9.frame_extractor_service.utils.VideoProcessingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "video_processing_events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoProcessingEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String videoPath;
    private String videoName;
    private String outputDir;
    private String userID;
    private LocalDateTime queuedAt;
    private LocalDateTime completedAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private VideoProcessingStatus status;

}
