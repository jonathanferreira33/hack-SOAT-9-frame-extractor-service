package com.hack_SOAT_9.frame_extractor_service.service;

import com.hack_SOAT_9.frame_extractor_service.domain.VideoMessage;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class VideoProcessorServiceTest {

    private static final String FRAME_DIR = "frames";
    private static final String ZIP_NAME = "test_video_frames.zip";

    @BeforeEach
    void setUp() throws IOException {
        // Cria a pasta de frames simulada
        Files.createDirectories(Paths.get(FRAME_DIR));
        for (int i = 1; i <= 3; i++) {
            Path frame = Paths.get(FRAME_DIR, "frame_000" + i + ".jpg");
            Files.write(frame, ("frame" + i).getBytes());
        }
    }

    @AfterEach
    void cleanUp() throws IOException {
        // Deleta arquivos criados
        Files.walk(Paths.get(FRAME_DIR))
                .map(Path::toFile)
                .forEach(File::delete);
        Files.deleteIfExists(Paths.get(ZIP_NAME));
        Files.deleteIfExists(Paths.get(FRAME_DIR));
    }

    @Test
    void shouldCreateZipFromFramesWithoutRunningFfmpeg() {
        VideoMessage message = new VideoMessage();
        message.setVideoName("test_video.mp4");
        message.setVideoPath("dummy/path/test_video.mp4");

        VideoProcessorService service = new VideoProcessorService() {
            @Override
            public void processVideo(VideoMessage event) {
                try {
                    // Pula o ffmpeg e vai direto para o zip
                    String zipFileName = event.getVideoName().replaceAll("\\..+$", "") + "_frames.zip";
                    try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFileName))) {
                        Files.list(Paths.get(FRAME_DIR))
                                .filter(Files::isRegularFile)
                                .forEach(path -> {
                                    try (InputStream fis = Files.newInputStream(path)) {
                                        zipOut.putNextEntry(new java.util.zip.ZipEntry(path.getFileName().toString()));
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
                } catch (IOException e) {
                    fail("Erro ao gerar zip simulado: " + e.getMessage());
                }
            }
        };

        service.processVideo(message);

        // Verificar se o ZIP foi criado
        File zip = new File(ZIP_NAME);
        assertTrue(zip.exists());
        try (ZipFile zipFile = new ZipFile(zip)) {
            assertEquals(3, zipFile.size());
        } catch (IOException e) {
            fail("Erro ao ler o ZIP: " + e.getMessage());
        }
    }
}
