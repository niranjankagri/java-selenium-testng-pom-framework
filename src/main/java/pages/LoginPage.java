package pages;

import browser_actions.BrowserAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * OrangeHRM login page: locators of the login form and actions on them.
 * Every action waits for its element (see BrowserAction).
 */
public class LoginPage extends BrowserAction {
   private WebDriver driver;
    /** Page object for the browser of the running test class. */
    public LoginPage(WebDriver driver) {
        super(driver);

        this.driver = driver;
    }

    // Login form fields, found by their name attributes
    private By usernameField = By.xpath("//input[@name='username']");
    private By passwordField = By.xpath("//input[@name='password']");
    private By loginButton = By.xpath("//button[@type='submit']");

    /** Types the user name. */
    public void enterUsername(String username) {

        sendKeys(usernameField, username);
    }
    /** Types the password. */
    public void enterPassword(String password) {

        sendKeys(passwordField, password);
    }
    /** Submits the login form; the dashboard opens on success. */
    public void clickLogin() {

        click(loginButton);
    }

    /** @return true when the user name field is visible. */
    public boolean isUsernameFieldDisplayed() {

        return isElementDisplayed(usernameField);

    }

    /** @return true when the user name field can be typed into. */
    public boolean isUsernameFieldEnabled() {

        return isElementEnabled(usernameField);

    }

    /** @return true when the password field is visible. */
    public boolean isPasswordFieldDisplayed() {

        return isElementDisplayed(passwordField);

    }

    /** @return true when the password field can be typed into. */
    public boolean isPasswordFieldEnabled() {

        return isElementEnabled(passwordField);

    }

    /** @return true when the Login button can be clicked. */
    public boolean isLoginButtonEnabled() {

        return isElementEnabled(loginButton);

    }

    /** @return true when the Login button is visible. */
    public boolean isLoginButtonDisplayed() {

        return isElementDisplayed(loginButton);

    }

}
