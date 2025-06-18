package com.jk.pages;

import com.jk.config.ConfigReader;
import com.jk.ocr.OCRUtil;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;

public class LoginPage {
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By loginIdField = By.cssSelector("input[placeholder='Enter Login ID']");
    private final By passwordField = By.cssSelector("input[placeholder='Enter Password']");
    private final By captchaField = By.cssSelector("input[placeholder='Enter Captcha']");
    private final By captchaImage = By.cssSelector("img[alt='Captcha verification']");
    private final By loginButton = By.xpath("//button[.//span[text()='Login']]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Step("Performing Login to application")
    public void performLogin() {
        String url = ConfigReader.get("app.url");
        logger.info("Navigating to application URL: {}", url);
        driver.get(url);

        enterLoginId(ConfigReader.get("app.loginId"));
        enterPassword(ConfigReader.get("app.password"));
        enterCaptcha();
        clickLogin();
    }

    @Step("Entering Login ID: {0}")
    private void enterLoginId(String loginId) {
        logger.info("Entering Login ID: {}", loginId);
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginIdField)).sendKeys(loginId);
    }

    @Step("Entering Password")
    private void enterPassword(String password) {
        logger.info("Entering password.");
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Reading and entering CAPTCHA")
    private void enterCaptcha() {
        String captchaText = "";
        try {
            WebElement captchaElement = driver.findElement(captchaImage);
            File screenshot = captchaElement.getScreenshotAs(OutputType.FILE);
            BufferedImage bufferedImage = ImageIO.read(screenshot);
            captchaText = OCRUtil.getCaptchaTextFromBufferedImage(bufferedImage);
            logger.info("CAPTCHA recognized as: {}", captchaText);
            attachCaptchaText(captchaText);
        } catch (Exception e) {
            logger.error("Failed to read CAPTCHA: {}", e.getMessage());
        }
        driver.findElement(captchaField).sendKeys(captchaText);
    }

    @Step("Clicking on Login button and waiting for Dashboard")
    private void clickLogin() {
        logger.info("Clicking Login button.");
        driver.findElement(loginButton).click();
        logger.info("Waiting for dashboard to load...");
        wait.until(ExpectedConditions.urlContains("dashboard"));
        logger.info("Dashboard page loaded successfully.");
    }

    @Attachment(value = "Extracted CAPTCHA Text", type = "text/plain")
    private String attachCaptchaText(String text) {
        return text;
    }
}
