package com.hack_SOAT_9.frame_extractor_service.service;

import com.hack_SOAT_9.frame_extractor_service.domain.VideoMessage;
import com.hack_SOAT_9.frame_extractor_service.domain.entity.VideoProcessingEventEntity;
import com.hack_SOAT_9.frame_extractor_service.messaging.ErrorEventPublisher;
import com.hack_SOAT_9.frame_extractor_service.utils.VideoMessageMapper;
import com.hack_SOAT_9.frame_extractor_service.utils.VideoProcessingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class VideoProcessorService {

    private final VideoEventService videoEventService;

    private static final String FRAME_OUTPUT_DIR = "frames";

    @Autowired
    private ErrorEventPublisher errorEventPublisher;

    @Autowired
    public VideoProcessorService(VideoEventService videoEventService) {
        this.videoEventService = videoEventService;
    }

    public void processVideo(VideoMessage event) {
        VideoProcessingEventEntity eventEntity = VideoMessageMapper.toEntity(event);
        try {
            System.out.println("Processando vídeo: " + event.videoName());

            Path frameDir = Paths.get(FRAME_OUTPUT_DIR);
            Files.createDirectories(frameDir);

            String framePattern = FRAME_OUTPUT_DIR + "/frame_%04d.jpg";
            ProcessBuilder pb = new ProcessBuilder(
                    "ffmpeg", "-i", event.videoName(), framePattern
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[ffmpeg] " + line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("ffmpeg falhou com código: " + exitCode);
            }

            String zipFileName = event.videoName().replaceAll("\\..+$", "") + "_frames.zip";
            try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFileName))) {
                Files.list(frameDir)
                        .filter(Files::isRegularFile)
                        .forEach(path -> {
                            try (InputStream fis = Files.newInputStream(path)) {
                                ZipEntry entry = new ZipEntry(path.getFileName().toString());
                                zipOut.putNextEntry(entry);

                                byte[] buffer = new byte[1024];
                                int len;
                                while ((len = fis.read(buffer)) > 0) {
                                    zipOut.write(buffer, 0, len);
                                }

                                zipOut.closeEntry();
                            } catch (IOException e) {
                                throw new UncheckedIOException(e);
                            }
                        });
            }
            eventEntity.setStatus(VideoProcessingStatus.SUCCESS);
            videoEventService.saveEvent(eventEntity);

            System.out.println("ZIP gerado: " + zipFileName);

        } catch (Exception e) {
            if (event.userEmail() != null) {
                errorEventPublisher.publishError(event.userEmail(), e.getMessage());
            }
            eventEntity.setStatus(VideoProcessingStatus.ERROR);
            videoEventService.saveEvent(eventEntity);
            e.printStackTrace();
        }
    }
}
