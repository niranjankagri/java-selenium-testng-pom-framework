import org.testng.asserts.SoftAssert;
import pages.AdminPage;
import pages.BaseClass;
import pages.LoginPage;
import pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * OrangeHRM tests. They share one browser session (BaseClass) and run in priority order:
 * 1 login, 2 open Admin, 3 search a user, 4 log out. Groups: SmokeTest (1, 2) and
 * Regression (3, 4), used by groupsTestng.xml.
 * Hard asserts stop a test at the first broken element; soft asserts collect the
 * URL checks and report them together at assertAll().
 */
public class TestClass extends BaseClass {

    @Test(groups= {"SmokeTest"},priority = 1)
    /** Logs in with the demo account from config.properties and lands on the dashboard. */
    public void testSuccessfulLogin() {
        extentTest.info("Verify that user is able to login with valid credentials");
        SoftAssert softAssert = new SoftAssert();
        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field is not displayed");
        Assert.assertTrue(loginPage.isUsernameFieldEnabled(), "Username field is disabled");
        loginPage.enterUsername(properties.getProperty("userName"));
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field is not displayed");
        Assert.assertTrue(loginPage.isPasswordFieldEnabled(), "Password field is disabled");
        loginPage.enterPassword(properties.getProperty("password"));
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button is not displayed");
        Assert.assertTrue(loginPage.isLoginButtonEnabled(), "Login button is disabled");
        loginPage.clickLogin();
        softAssert.assertEquals(loginPage.waitForUrl(properties.getProperty("actualURL")), properties.getProperty("actualURL"), "Invalid Home Page URL");
        softAssert.assertAll();

    }

    @Test(groups= {"Regression"}, priority = 3)
    /** Searches System Users for "Admin" with status Enabled, then resets the filters. */
    public void testSearchUsername() {
        extentTest.info("Verify that Admin is able to Search User");
        SoftAssert softAssert = new SoftAssert();
        AdminPage adminPage = new AdminPage(getDriver());
        Assert.assertTrue(adminPage.isUsernameFieldDisplayed(), "Username field is not displayed");
        Assert.assertTrue(adminPage.isUsernameFieldEnabled(), "Username field is disabled");
        adminPage.username("Admin");
        Assert.assertTrue(adminPage.isArrowButtonEnabled(), "Arrow button is disabled");
        Assert.assertTrue(adminPage.isArrowButtonDisplayed(), "Arrow button is not displayed");
        adminPage.arrow();
        Assert.assertTrue(adminPage.isEnabledOptionEnabled(), " Dropdown enabled option is disabled");
        Assert.assertTrue(adminPage.isEnabledOptionDisplayed(), " Dropdown enabled option is not displayed");
        adminPage.enabled();
        Assert.assertTrue(adminPage.isSearchButtonEnabled(), "Search button is disabled");
        Assert.assertTrue(adminPage.isSearchButtonDisplayed(), "Search button is not displayed");
        adminPage.search();
        Assert.assertTrue(adminPage.isResetButtonEnabled(), "Reset button is disabled");
        Assert.assertTrue(adminPage.isResetButtonDisplayed(), "Reset button is not displayed");
        adminPage.reset();
        softAssert.assertEquals(adminPage.waitForUrl(properties.getProperty("adminPageURL")), properties.getProperty("adminPageURL"), "Invalid Admin Page URL");
        softAssert.assertAll();
    }

    @Test(groups= {"SmokeTest"}, priority = 2)
    /** Opens Admin from the dashboard side menu. */
    public void testAdmin() {
        extentTest.info("Verify that user is able to navigate to Admin");
        SoftAssert softAssert = new SoftAssert();
        HomePage homePage = new HomePage(getDriver());
        softAssert.assertTrue(homePage.isAdminOptionEnabled(), "Admin option is disabled");
        softAssert.assertTrue(homePage.isAdminOptionDisplayed(), "Admin option is not displayed");
        homePage.admin();
        softAssert.assertEquals(homePage.waitForUrl(properties.getProperty("adminPageURL")), properties.getProperty("adminPageURL"), "Invalid Admin Page URL");
        softAssert.assertAll();
    }

    @Test (groups= {"Regression"},priority = 4)
    /** Logs out through the user menu and is back on the login page. */
    public void testSuccessfulLogout() {
        extentTest.info("Verify that user is able to logout");
        SoftAssert softAssert = new SoftAssert();
        AdminPage adminPage = new AdminPage(getDriver());
        Assert.assertTrue(adminPage.isUserDropDownEnabled(), "User dropdown is disabled");
        Assert.assertTrue(adminPage.isUserDropDownDisplayed(), "User dropdown is not displayed");
        adminPage.dropDown();
        adminPage.logout();
        softAssert.assertEquals(adminPage.waitForUrl(properties.getProperty("loginPageURL")), properties.getProperty("loginPageURL"), "Not back on the login page after logout");
        softAssert.assertAll();
    }

}
