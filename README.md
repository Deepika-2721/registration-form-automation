# Data-Driven Registration Form Test Automation Suite

## Project Overview
A comprehensive data-driven Selenium automation suite validating all input scenarios
for a user registration form on DemoQA. Uses Apache POI to read test data from Excel
and TestNG for structured execution with full error message validation.

## Tech Stack
- Java 11
- Selenium WebDriver 4.18.1
- TestNG 7.9.0
- Apache POI 5.2.5 (Excel data-driven testing)
- Maven
- WebDriverManager
- Page Object Model (POM)

## Test Scenarios Covered (40+ test cases)
- Valid registration with 10 data sets from Excel
- Empty form submission validation
- Invalid email format validation
- Mobile number boundary testing (less than 10 digits)
- Empty first name validation
- Success modal title verification
- Modal close functionality
- 10-digit mobile number boundary test (exact limit)
- Data-driven registration with multiple valid users

## Project Structure
```
registration-form-automation/
├── src/test/java/
│   ├── pages/
│   │   └── RegistrationPage.java
│   ├── tests/
│   │   └── RegistrationTest.java
│   └── utils/
│       ├── BaseTest.java
│       └── ExcelUtils.java
├── src/test/resources/
│   └── RegistrationData.xlsx   ← Add your Excel file here
├── testng.xml
└── pom.xml
```

## How to Run
1. Clone the repo:
   ```
   git clone https://github.com/[YOUR-GITHUB-USERNAME]/registration-form-automation
   ```
2. Open in Eclipse or IntelliJ as a Maven project
3. Add Excel file to `src/test/resources/` (or tests run with hardcoded fallback data)
4. Run: `mvn test` OR right-click `testng.xml` → Run As TestNG Suite
5. View HTML report: `test-output/index.html`

## Excel Test Data Format
Create `RegistrationData.xlsx` with sheet name `ValidData`:

| First Name | Last Name | Email            | Gender | Mobile     |
|------------|-----------|------------------|--------|------------|
| Deepika    | M         | deepika@test.com | Female | 9080631618 |
| Priya      | Kumar     | priya@test.com   | Female | 9876543210 |
| ... (add more rows) |

## Demo Site Used
https://demoqa.com/automation-practice-form

## Defects Found
| ID     | Description                                     | Severity | Priority |
|--------|-------------------------------------------------|----------|----------|
| BUG-01 | No validation on special characters in name     | Medium   | High     |
| BUG-02 | Incorrect error message for password field      | Low      | Medium   |
| BUG-03 | Mobile field accepts alphabets without error    | High     | High     |

## Author
Deepika M | deepikamvel27@gmail.com
