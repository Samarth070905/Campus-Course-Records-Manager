package edu.ccrm.io;

import edu.ccrm.config.AppConfig;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class BackupService {
    private final Path dataDir = AppConfig.getInstance().getDataDir();

    public Path createTimestampedBackup(Path sourceFolder) throws IOException {
        String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path backupRoot = dataDir.resolve("backups").resolve(stamp);
        Files.createDirectories(backupRoot);
        try (Stream<Path> files = Files.walk(sourceFolder)) {
            files.forEach(src -> {
                try {
                    Path rel = sourceFolder.relativize(src);
                    Path dest = backupRoot.resolve(rel);
                    if (Files.isDirectory(src)) {
                        Files.createDirectories(dest);
                    } else {
                        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return backupRoot;
    }

    public long computeSizeRecursive(Path folder) throws IOException {
        try (Stream<Path> stream = Files.walk(folder)) {
            return stream.filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try { return Files.size(p); } catch (IOException e) { return 0L; }
                    }).sum();
        }
    }
}

