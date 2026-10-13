import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import pages.BaseClass;
import pages.LoginPage;

/**
 * Two tests that fail and skip ON PURPOSE, to show what the Extent report looks like
 * for a failure and a skip. They are not part of testng.xml.
 *
 * How to run them:
 * <pre>
 * mvn test -DsuiteXmlFile=ReportDemoSuite.xml
 * </pre>
 * Expected: the 4 OrangeHRM tests pass, failOnPurpose fails, skipOnPurpose is skipped,
 * so Maven ends with "Tests run: 6, Failures: 1, Errors: 0, Skipped: 1" and BUILD FAILURE.
 * Then open test-output/report.html (the failure has its screenshot attached) and
 * Screenshots/failOnPurpose.png.
 */
public class ReportDemoTest extends BaseClass {

    /**
     * Fails on purpose: BaseClass has just opened the login page, but the test expects
     * the dashboard. The listener attaches a screenshot of the login page to the report.
     */
    @Test(priority = 1, description = "Fails on purpose: expects the dashboard without logging in")
    public void failOnPurpose() {
        extentTest.info("Demo failure: checks for the dashboard URL while still on the login page");
        LoginPage loginPage = new LoginPage(getDriver());
        // Passes: the login form is there
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field is not displayed");
        // Wrong on purpose: nobody logged in, so the browser is still on the login page
        Assert.assertEquals(loginPage.getCurrentUrl(), properties.getProperty("actualURL"),
                "Demo failure: not on the dashboard (nobody logged in)");
    }

    /**
     * Skipped on purpose: shows how a skipped test and its reason appear in the report.
     * Real tests use SkipException when a precondition is missing.
     */
    @Test(priority = 2, description = "Skipped on purpose to show a skip in the report")
    public void skipOnPurpose() {
        throw new SkipException("Skipped on purpose to show a skipped test in the report");
    }
}
