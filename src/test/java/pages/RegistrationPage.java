package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class RegistrationPage {

    WebDriver driver;
    WebDriverWait wait;

    // ── Form Fields ──────────────────────────────
    @FindBy(id = "firstName")
    WebElement firstNameField;

    @FindBy(id = "lastName")
    WebElement lastNameField;

    @FindBy(id = "userEmail")
    WebElement emailField;

    @FindBy(id = "userNumber")
    WebElement mobileField;

    @FindBy(id = "currentAddress")
    WebElement currentAddressField;

    // Gender radio buttons
    @FindBy(xpath = "//label[@for='gender-radio-1']")
    WebElement genderMale;

    @FindBy(xpath = "//label[@for='gender-radio-2']")
    WebElement genderFemale;

    // Submit button
    @FindBy(id = "submit")
    WebElement submitButton;

    // Success modal
    @FindBy(id = "example-modal-sizes-title-lg")
    WebElement successModalTitle;

    // Close modal button
    @FindBy(id = "closeLargeModal")
    WebElement closeModalButton;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ── Actions ──────────────────────────────────
    public void enterFirstName(String name) {
        firstNameField.clear();
        firstNameField.sendKeys(name);
    }

    public void enterLastName(String name) {
        lastNameField.clear();
        lastNameField.sendKeys(name);
    }

    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void selectGenderMale() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", genderMale);
    }

    public void selectGenderFemale() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", genderFemale);
    }

    public void enterMobileNumber(String mobile) {
        mobileField.clear();
        mobileField.sendKeys(mobile);
    }

    public void enterCurrentAddress(String address) {
        currentAddressField.clear();
        currentAddressField.sendKeys(address);
    }

    public void clickSubmit() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", submitButton);
    }

    public void fillAndSubmitForm(String firstName, String lastName,
                                   String email, String gender, String mobile) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        if (gender.equalsIgnoreCase("Male")) {
            selectGenderMale();
        } else {
            selectGenderFemale();
        }
        enterMobileNumber(mobile);
        enterCurrentAddress("123, Test Street, Chennai");
        clickSubmit();
    }

    // ── Validations ──────────────────────────────
    public boolean isSuccessModalDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successModalTitle));
            return successModalTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessModalTitle() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successModalTitle));
            return successModalTitle.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isFieldHighlightedAsError(WebElement field) {
        String borderColor = field.getCssValue("border-color");
        return borderColor.contains("220, 53, 69") || // Bootstrap danger red rgb
               field.getAttribute("class").contains("is-invalid");
    }

    public boolean isFirstNameInvalid() {
        return isFieldHighlightedAsError(firstNameField);
    }

    public boolean isEmailInvalid() {
        return isFieldHighlightedAsError(emailField);
    }

    public boolean isMobileInvalid() {
        return isFieldHighlightedAsError(mobileField);
    }

    public void closeSuccessModal() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(closeModalButton));
            closeModalButton.click();
        } catch (Exception e) {
            System.out.println("Modal close failed: " + e.getMessage());
        }
    }

    public void scrollToSubmit() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", submitButton);
    }
}
