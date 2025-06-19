package com.jk.pages.beneficiary;

import com.jk.data.BeneficiaryData;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class OtherMCBBeneficiaryPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(OtherMCBBeneficiaryPage.class);

    // Locators
    private final By beneficiaryLink = By.cssSelector("a[href='/beneficiary']");
    private final By otherMcbLink = By.cssSelector("a[href*='otherMcb']");
    private final By addBeneficiaryBtn = By.xpath("//button[contains(.,'Add Beneficiary')]");
    private final By nicknameField = By.cssSelector("input[placeholder='Enter Nickname']");
    private final By accountNumberField = By.cssSelector("input[placeholder='Enter Account Number']");
    private final By dropdownTrigger = By.cssSelector("div.css-lspp8m-control");
    private final By submitBtn = By.xpath("//button[contains(.,'Submit')]");

    public OtherMCBBeneficiaryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Step("Navigating to Other MCB Beneficiary page")
    public void navigateToAddBeneficiary() {
        logger.info("Clicking on Beneficiary -> Other MCB -> Add Beneficiary");
        wait.until(ExpectedConditions.elementToBeClickable(beneficiaryLink)).click();
        wait.until(ExpectedConditions.elementToBeClickable(otherMcbLink)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addBeneficiaryBtn)).click();
    }

    @Step("Adding Other MCB Beneficiary: {0}")
    public void addOtherMcbBeneficiary(BeneficiaryData data) {
        logger.info("Filling nickname: {}", data.nickname);
        wait.until(ExpectedConditions.visibilityOfElementLocated(nicknameField)).sendKeys(data.nickname);

        logger.info("Selecting 'Account as Beneficiary' dropdown");
        wait.until(ExpectedConditions.elementToBeClickable(dropdownTrigger)).click();
        driver.findElement(By.xpath("//div[contains(@class,'option') and contains(.,'Account as Beneficiary')]")).click();

        logger.info("Filling account number: {}", data.accountNumber);
        driver.findElement(accountNumberField).sendKeys(data.accountNumber);

        logger.info("Submitting beneficiary form");
        driver.findElement(submitBtn).click();
    }
}
