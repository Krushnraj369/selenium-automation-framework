package com.jk.tests;

import com.jk.base.BaseTest;
import com.jk.data.BeneficiaryData;
import com.jk.pages.LoginPage;
import com.jk.pages.beneficiary.OtherMCBBeneficiaryPage;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IBMBRetailTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(IBMBRetailTest.class);

    @Test(description = "Verify Add Beneficiary Flow in IBMB Retail")
    public void testAddBeneficiaryFlow() {
        logger.info("🔐 Starting Add Beneficiary Test");

        LoginPage login = new LoginPage(driver);
     //*   BeneficiaryPage beneficiary = new BeneficiaryPage(driver);*/
        OtherMCBBeneficiaryPage beneficiary = new OtherMCBBeneficiaryPage(driver);

        performLoginStep(login);
        navigateToAddBeneficiaryStep(beneficiary);
        assertPageTitle("Myanmar Citizens Bank | Secure Internet Banking Services");

        BeneficiaryData data = new BeneficiaryData(
                "RestNick", "903456789012", "Rest Account", "9596543210",
                "Bangkok Bank", "Yangon", "Bangkok Bank Public Company Limited"
        );

        addBeneficiaryStep(beneficiary, data);

        logger.info("✅ Add Beneficiary Test Completed");
    }

    @Step("Login to the application")
    public void performLoginStep(LoginPage login) {
        login.performLogin();
    }

    @Step("Navigate to Add Beneficiary")
    public void navigateToAddBeneficiaryStep(OtherMCBBeneficiaryPage beneficiary) {
        beneficiary.navigateToAddBeneficiary();
    }

    @Step("Adding beneficiary with data: {0}")
    public void addBeneficiaryStep(OtherMCBBeneficiaryPage beneficiary, BeneficiaryData data) {
        beneficiary.addOtherMcbBeneficiary(data);
    }

    @Step("Verifying page title: Expected = '{0}'")
    public void assertPageTitle(String expected) {
        String actual = driver.getTitle();
        logger.info("Asserting page title: Expected = '{}', Actual = '{}'", expected, actual);
        attachText("Actual Page Title", actual);

        Assert.assertEquals(actual, expected, "❌ Page title doesn't match!");
    }

    @Attachment(value = "{0}", type = "text/plain")
    public String attachText(String name, String content) {
        return content;
    }
}
