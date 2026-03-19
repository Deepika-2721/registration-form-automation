package tests;

import pages.RegistrationPage;
import utils.BaseTest;
import utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseTest {

    // ─────────────────────────────────────────────
    // TC_001: Valid registration - all fields filled
    // ─────────────────────────────────────────────
    @Test(priority = 1, description = "Valid registration with all required fields")
    public void validRegistration() {
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.scrollToSubmit();
        regPage.fillAndSubmitForm("Deepika", "M", "deepika@gmail.com", "Female", "9080631618");
        boolean success = regPage.isSuccessModalDisplayed();
        System.out.println("Success Modal Displayed: " + success);
        Assert.assertTrue(success, "Success modal not displayed after valid registration");
    }

    // ─────────────────────────────────────────────
    // TC_002: Verify success modal title text
    // ─────────────────────────────────────────────
    @Test(priority = 2, description = "Verify success modal shows correct title")
    public void verifySuccessModalTitle() {
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.scrollToSubmit();
        regPage.fillAndSubmitForm("Priya", "Kumar", "priya@test.com", "Female", "9876543210");
        String modalTitle = regPage.getSuccessModalTitle();
        System.out.println("Modal Title: " + modalTitle);
        Assert.assertEquals(modalTitle, "Thanks for submitting the form",
                "Success modal title mismatch");
    }

    // ─────────────────────────────────────────────
    // TC_003: Submit with empty first name
    // ─────────────────────────────────────────────
    @Test(priority = 3, description = "Submit form with empty first name - expect validation error")
    public void submitWithEmptyFirstName() {
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.scrollToSubmit();
        regPage.enterLastName("M");
        regPage.enterEmail("test@gmail.com");
        regPage.selectGenderMale();
        regPage.enterMobileNumber("9080631618");
        regPage.clickSubmit();
        boolean isInvalid = regPage.isFirstNameInvalid();
        System.out.println("First name marked invalid: " + isInvalid);
        Assert.assertTrue(isInvalid, "First name field not highlighted as invalid");
    }

    // ─────────────────────────────────────────────
    // TC_004: Submit with invalid email format
    // ─────────────────────────────────────────────
    @Test(priority = 4, description = "Submit with invalid email format - expect validation error")
    public void submitWithInvalidEmail() {
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.scrollToSubmit();
        regPage.fillAndSubmitForm("Deepika", "M", "invalidemail", "Female", "9080631618");
        boolean isEmailInvalid = regPage.isEmailInvalid();
        System.out.println("Email marked invalid: " + isEmailInvalid);
        Assert.assertTrue(isEmailInvalid, "Email field not highlighted as invalid for wrong format");
    }

    // ─────────────────────────────────────────────
    // TC_005: Submit with mobile less than 10 digits
    // ─────────────────────────────────────────────
    @Test(priority = 5, description = "Submit with mobile number less than 10 digits")
    public void submitWithShortMobileNumber() {
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.scrollToSubmit();
        regPage.fillAndSubmitForm("Deepika", "M", "deepika@gmail.com", "Female", "12345");
        boolean isMobileInvalid = regPage.isMobileInvalid();
        System.out.println("Mobile marked invalid: " + isMobileInvalid);
        Assert.assertTrue(isMobileInvalid, "Mobile field not highlighted as invalid for short number");
    }

    // ─────────────────────────────────────────────
    // TC_006: Submit completely empty form
    // ─────────────────────────────────────────────
    @Test(priority = 6, description = "Submit form with all fields empty - expect validation errors")
    public void submitEmptyForm() {
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.scrollToSubmit();
        regPage.clickSubmit();
        boolean firstNameInvalid = regPage.isFirstNameInvalid();
        System.out.println("Empty form - first name invalid: " + firstNameInvalid);
        Assert.assertTrue(firstNameInvalid,
                "Form accepted empty submission - validation failed");
    }

    // ─────────────────────────────────────────────
    // TC_007: Verify modal closes after clicking close
    // ─────────────────────────────────────────────
    @Test(priority = 7, description = "Verify success modal closes properly")
    public void verifyModalCloses() {
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.scrollToSubmit();
        regPage.fillAndSubmitForm("Raj", "Kumar", "raj@test.com", "Male", "9876543210");
        regPage.isSuccessModalDisplayed();
        regPage.closeSuccessModal();
        boolean isModalGone = !regPage.isSuccessModalDisplayed();
        System.out.println("Modal closed successfully: " + isModalGone);
        Assert.assertTrue(isModalGone, "Success modal did not close after clicking close button");
    }

    // ─────────────────────────────────────────────
    // TC_008: Mobile number with 10 digits boundary
    // ─────────────────────────────────────────────
    @Test(priority = 8, description = "Mobile with exactly 10 digits - boundary test")
    public void mobileNumberBoundaryTest() {
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.scrollToSubmit();
        regPage.fillAndSubmitForm("Ananya", "S", "ananya@test.com", "Female", "9123456789");
        boolean success = regPage.isSuccessModalDisplayed();
        System.out.println("10-digit mobile accepted: " + success);
        Assert.assertTrue(success, "Form rejected valid 10-digit mobile number");
    }

    // ─────────────────────────────────────────────
    // TC_009 - TC_018: Data-Driven Registration (Excel)
    // ─────────────────────────────────────────────
    @Test(priority = 9, dataProvider = "registrationData",
          description = "Data-driven registration with multiple valid data sets from Excel")
    public void dataDrivenRegistrationTest(String firstName, String lastName,
                                            String email, String gender, String mobile) {
        System.out.println("Testing registration for: " + firstName + " " + lastName);
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.scrollToSubmit();
        regPage.fillAndSubmitForm(firstName, lastName, email, gender, mobile);
        boolean success = regPage.isSuccessModalDisplayed();
        Assert.assertTrue(success,
                "Registration failed for data: " + firstName + ", " + email);
        regPage.closeSuccessModal();
    }

    @DataProvider(name = "registrationData")
    public Object[][] getRegistrationData() {
        try {
            ExcelUtils.loadExcel(
                "src/test/resources/RegistrationData.xlsx", "ValidData");
            int rowCount = ExcelUtils.getRowCount();
            Object[][] data = new Object[rowCount][5];
            for (int i = 1; i <= rowCount; i++) {
                data[i - 1][0] = ExcelUtils.getCellData(i, 0); // First Name
                data[i - 1][1] = ExcelUtils.getCellData(i, 1); // Last Name
                data[i - 1][2] = ExcelUtils.getCellData(i, 2); // Email
                data[i - 1][3] = ExcelUtils.getCellData(i, 3); // Gender
                data[i - 1][4] = ExcelUtils.getCellData(i, 4); // Mobile
            }
            ExcelUtils.closeWorkbook();
            return data;
        } catch (Exception e) {
            System.out.println("Excel not found, using hardcoded data: " + e.getMessage());
            return new Object[][]{
                {"Deepika",  "M",       "deepika@test.com",  "Female", "9080631618"},
                {"Priya",    "Kumar",   "priya@test.com",    "Female", "9876543210"},
                {"Rahul",    "Sharma",  "rahul@test.com",    "Male",   "9123456780"},
                {"Anjali",   "Verma",   "anjali@test.com",   "Female", "9234567891"},
                {"Karthik",  "R",       "karthik@test.com",  "Male",   "9345678912"},
                {"Meena",    "S",       "meena@test.com",    "Female", "9456789123"},
                {"Vikram",   "N",       "vikram@test.com",   "Male",   "9567891234"},
                {"Sneha",    "T",       "sneha@test.com",    "Female", "9678912345"},
                {"Arun",     "P",       "arun@test.com",     "Male",   "9789123456"},
                {"Kavitha",  "L",       "kavitha@test.com",  "Female", "9891234567"},
            };
        }
    }
}
