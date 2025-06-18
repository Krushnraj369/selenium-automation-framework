package com.jk.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DropdownUtil {
    public static void selectReactOption(WebDriver driver, String placeholder, String optionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        By dropdown = By.xpath("//div[contains(@class, 'css-') and .//div[text()='" + placeholder + "']]");
        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();

        WebElement input = driver.switchTo().activeElement();
        input.sendKeys(optionText);

        By optionLocator = By.xpath("//div[@role='option' and contains(text(),'" + optionText + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
    }
}
