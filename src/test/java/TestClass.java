
import listeners.TestListener;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;
import pages.AdminPage;
import pages.BaseClass;
import pages.LoginPage;
import pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class TestClass extends BaseClass {

    @Test(groups= {"SmokeTest"},priority = 1)
    public void testSuccessfulLogin() throws InterruptedException {
        extentTest.info("Verify that user is able to login with valid credentials");
        SoftAssert softAssert = new SoftAssert();
        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field is not displayed");
        Assert.assertTrue(loginPage.isUsernameFieldEnabled(), "Username field is not disabled");
        loginPage.enterUsername(properties.getProperty("userName"));
        Thread.sleep(1000);
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field is not displayed");
        Assert.assertTrue(loginPage.isPasswordFieldEnabled(), "Password field is not disabled");
        loginPage.enterPassword(properties.getProperty("password"));
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button is not displayed");
        Assert.assertTrue(loginPage.isLoginButtonEnabled(), "Login button is disabled");
        loginPage.clickLogin();
        softAssert.assertEquals(loginPage.getCurrentUrl() , properties.getProperty("actualURL"), "Invalid Home Page URL");
        softAssert.assertAll();

    }

    @Test(groups= {"Regression"}, priority = 3)
    public void testSearchUsername() throws InterruptedException {
        extentTest.info("Verify that Admin is able to Search User");
        SoftAssert softAssert = new SoftAssert();
        Thread.sleep(1000);
        AdminPage adminPage = new AdminPage(getDriver());
        Thread.sleep(1000);
        Assert.assertTrue(adminPage.isUsernameFieldDisplayed(), "Username field is not displayed");
        Assert.assertTrue(adminPage.isUsernameFieldEnabled(), "Username field is disabled");
        adminPage.username("Admin");
        Thread.sleep(2000);
        Assert.assertTrue(adminPage.isArrowButtonEnabled(), "Arrow button is disabled");
        Assert.assertTrue(adminPage.isArrowButtonDisplayed(), "Arrow button is not displayed");
        adminPage.arrow();
        Thread.sleep(2000);
        Assert.assertTrue(adminPage.isEnabledOptionEnabled(), " Dropdown enabled option is disabled");
        Assert.assertTrue(adminPage.isEnabledOptionDisplayed(), " Dropdown enabled option is not displayed");
        adminPage.enabled();
        Assert.assertTrue(adminPage.isSearchButtonEnabled(), "Search button is disabled");
        Assert.assertTrue(adminPage.isSearchButtonDisplayed(), "Search button is not displayed");
        adminPage.search();
        Thread.sleep(2000);
        Assert.assertTrue(adminPage.isResetButtonEnabled(), "Reset button is disabled");
        Assert.assertTrue(adminPage.isResetButtonDisplayed(), "Reset button is not displayed");
        adminPage.reset();
        softAssert.assertEquals(adminPage.getCurrentUrl() , properties.getProperty("adminPageURL"), "Invalid Admin Page URL");
        softAssert.assertAll();
    }

    @Test(groups= {"SmokeTest"}, priority = 2)
    public void testAdmin() throws InterruptedException {
        extentTest.info("Verify that user is able to navigate to Admin");
        SoftAssert softAssert = new SoftAssert();
        Thread.sleep(1000);
        HomePage homePage = new HomePage(getDriver());
        softAssert.assertTrue(homePage.isAdminOptionEnabled(), "Admin option is disabled");
        softAssert.assertTrue(homePage.isAdminOptionDisplayed(), "Admin option is disabled");
        homePage.admin();
        softAssert.assertEquals(homePage.getCurrentUrl() , properties.getProperty("adminPageURL"), "Invalid Admin Page URL");
        softAssert.assertAll();
    }

    @Test (groups= {"Regression"},priority = 4)
    public void testSuccessfulLogout() throws InterruptedException {
        extentTest.info("Verify that user is able to logout");
        SoftAssert softAssert = new SoftAssert();
        Thread.sleep(2000);
        AdminPage adminPage = new AdminPage(getDriver());
        Assert.assertTrue(adminPage.isUserDropDownEnabled(), "User dropdown is disabled");
        Assert.assertTrue(adminPage.isUserDropDownDisplayed(), "User dropdown is not displayed");
        adminPage.dropDown();
        Thread.sleep(2000);
        adminPage.logout();
        softAssert.assertEquals(adminPage.getCurrentUrl() ,properties.getProperty("loginPageURL"), "Invalid Home Page URL");
        softAssert.assertAll();
    }



}
