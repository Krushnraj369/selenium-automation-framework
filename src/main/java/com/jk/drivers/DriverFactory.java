package com.jk.drivers;

import com.jk.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverFactory {

    private static WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    public static WebDriver initDriver() {
        if (driver == null) {
            logger.info("Initializing WebDriver...");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            attachDriverInfo("Driver Initialized", driver.getClass().getSimpleName());
            logger.info("Driver initialized successfully: {}", driver);
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            logger.info("Quitting WebDriver...");
            driver.quit();
            driver = null;
            logger.info("WebDriver quit successfully.");
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    @Attachment(value = "{0}", type = "text/plain")
    private static String attachDriverInfo(String title, String message) {
        return message;
    }
}
