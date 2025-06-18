package com.jk.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static final Properties props = new Properties();

    static {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                props.load(input);
                logger.info("✅ config.properties loaded successfully.");
            } else {
                throw new RuntimeException("❌ config.properties file not found in resources!");
            }
        } catch (IOException e) {
            logger.error("❌ Failed to load config.properties", e);
            throw new RuntimeException("❌ Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            logger.warn("⚠️ Property key not found: {}", key);
        } else {
            logger.debug("🔑 Loaded config key: {} = {}", key, value);
        }
        return value;
    }
}
