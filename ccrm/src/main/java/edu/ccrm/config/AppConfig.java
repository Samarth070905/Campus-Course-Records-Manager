package edu.ccrm.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Properties;

public final class AppConfig {
    private static final String DEFAULT_CONFIG = "/app.properties";
    private static AppConfig instance;
    private final Properties props = new Properties();
    private final Path dataDir;
    private final int maxCreditsPerSemester;

    private AppConfig() {
        try (InputStream in = getClass().getResourceAsStream(DEFAULT_CONFIG)) {
            if (in != null) props.load(in);
        } catch (IOException ignored) {}

        String dataDirProp = props.getProperty("data.dir", "ccrm_data");
        dataDir = Paths.get(dataDirProp).toAbsolutePath();
        maxCreditsPerSemester = Integer.parseInt(props.getProperty("maxCreditsPerSemester", "24"));

        try {
            Files.createDirectories(dataDir);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create data directory: " + dataDir, e);
        }
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) instance = new AppConfig();
        return instance;
    }

    public Path getDataDir() { return dataDir; }
    public int getMaxCreditsPerSemester() { return maxCreditsPerSemester; }
    public String get(String key, String def) { return props.getProperty(key, def); }
}

