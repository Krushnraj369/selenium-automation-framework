package com.jk.base;

import com.jk.drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {
        logger.info("🚀 [Test Start] Initializing WebDriver...");
        driver = DriverFactory.initDriver();
    }

    @AfterMethod
    public void tearDown() {
        logger.info("🧹 [Test End] Quitting WebDriver...");
        DriverFactory.quitDriver();
    }
}
