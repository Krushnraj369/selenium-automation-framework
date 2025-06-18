package com.jk.pages;

import com.jk.data.BeneficiaryData;
import com.jk.utils.DropdownUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BeneficiaryPage {
    private static final Logger logger = LoggerFactory.getLogger(BeneficiaryPage.class);

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By beneficiaryLink = By.cssSelector("a[href='/beneficiary']");
    private final By otherBankLink = By.xpath("//a[@href='/beneficiary/otherBank']");
    private final By addBeneficiaryBtn = By.xpath("//button[.//span[text()='Add Beneficiary']]");
    private final By nickname = By.xpath("//input[@placeholder='Enter Nickname']");
    private final By accNumber = By.xpath("//input[@placeholder='Enter Beneficiary Account Number']");
    private final By accName = By.xpath("//input[@placeholder='Enter Beneficiary Account Name']");
    private final By mobile = By.xpath("//input[@placeholder='Enter Beneficiary Mobile Number']");
    private final By submit = By.xpath("//button[contains(., 'Submit')]");
    private final By confirm = By.xpath("//button[contains(@class,'bg-primary') and contains(., 'Confirm')]");

    public BeneficiaryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Step("Navigating to Add Beneficiary page")
    public void navigateToAddBeneficiary() {
        logger.info("Clicking on Beneficiary -> Other Bank -> Add Beneficiary");
        wait.until(ExpectedConditions.elementToBeClickable(beneficiaryLink)).click();
        wait.until(ExpectedConditions.elementToBeClickable(otherBankLink)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addBeneficiaryBtn)).click();
    }

    @Step("Adding beneficiary: {0}")
    public void addBeneficiary(BeneficiaryData data) {
        logger.info("Filling beneficiary form with data: {}", data);
        wait.until(ExpectedConditions.visibilityOfElementLocated(nickname)).sendKeys(data.nickname);
        driver.findElement(accNumber).sendKeys(data.accountNumber);
        driver.findElement(accName).sendKeys(data.accountName);
        driver.findElement(mobile).sendKeys(data.mobileNumber);

        selectDropdown("Select Beneficiary Bank", data.bank);
        selectDropdown("Select Region", data.region);
        selectDropdown("Select Branch", data.branch);

        submitForm();
        confirmSubmission();
    }

    @Step("Selecting dropdown: {0} -> {1}")
    private void selectDropdown(String label, String value) {
        logger.info("Selecting '{}' from dropdown labeled '{}'", value, label);
        DropdownUtil.selectReactOption(driver, label, value);
    }

    @Step("Submitting beneficiary form")
    private void submitForm() {
        logger.info("Submitting beneficiary form");
        driver.findElement(submit).click();
    }

    @Step("Confirming beneficiary submission")
    private void confirmSubmission() {
        logger.info("Confirming beneficiary submission");
        wait.until(ExpectedConditions.elementToBeClickable(confirm)).click();
    }
}
