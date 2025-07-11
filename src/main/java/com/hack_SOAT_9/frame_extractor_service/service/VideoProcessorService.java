package com.hack_SOAT_9.frame_extractor_service.service;

import com.hack_SOAT_9.frame_extractor_service.domain.VideoMessage;

import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class VideoProcessorService {

    private static final String FRAME_OUTPUT_DIR = "frames";

    public void processVideo(VideoMessage event) {
        try {
            System.out.println("Processando vídeo: " + event.getVideoName());

            // 1. Criar diretório de saída
            Path frameDir = Paths.get(FRAME_OUTPUT_DIR);
            Files.createDirectories(frameDir);

            // 2. Executar o ffmpeg
            String framePattern = FRAME_OUTPUT_DIR + "/frame_%04d.jpg";
            ProcessBuilder pb = new ProcessBuilder(
                    "ffmpeg", "-i", event.getVideoName(), framePattern
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // 3. Log do ffmpeg
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

            // 4. Compactar os frames em um .zip
            String zipFileName = event.getVideoName().replaceAll("\\..+$", "") + "_frames.zip";
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

            System.out.println("ZIP gerado: " + zipFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
