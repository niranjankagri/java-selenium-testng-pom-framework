package pages;

import browser_actions.BrowserAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * OrangeHRM Admin > System Users page: the user search form and the user menu (logout).
 */
public class AdminPage extends BrowserAction {

    private WebDriver driver;
    /** Page object for the browser of the running test class. */
    public AdminPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    // User menu (top right) and its Logout entry
    private By userDropDown = By.xpath("//span[@class='oxd-userdropdown-tab']");
    private By logoutOption = By.xpath("//a[@role='menuitem' and @href='/web/index.php/auth/logout']");

    // System Users search form: user name, Search, Reset, Status dropdown and its "Enabled" option
    private By usernameField = By.xpath("//div[2]/input[@class='oxd-input oxd-input--active']");

    private By searchButton = By.xpath("//button[@type='submit' and text() = ' Search ']");

    private By resetButton = By.xpath("//button[@type='button' and text() =' Reset ']");

    private By dropDownArrow = By.xpath("(//div/i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow'])[2]");

    private By enabledOption = By.xpath("(//div[@role='listbox']//child::div)[2]");

    /** Opens the user menu at the top right. */
    public void dropDown() {

      click(userDropDown);
    }
    /** Clicks Logout in the open user menu; the login page opens. */
    public void logout() {

        click(logoutOption);
    }

    /** Types a user name into the search form. */
    public void username(String username) {

        sendKeys(usernameField, username);
    }

    /** Runs the search with the current filters. */
    public void search() {

        click(searchButton);
    }

    /** Clears the search filters. */
    public void reset() {

        click(resetButton);
    }

    /** Opens the Status dropdown of the search form. */
    public void arrow() {

        click(dropDownArrow);
    }

    /** Picks "Enabled" in the open Status dropdown. */
    public void enabled() {

       click(enabledOption);
    }

    /** @return true when the search user name field is visible. */
    public boolean isUsernameFieldDisplayed() {

        return isElementDisplayed(usernameField);

    }

    /** @return true when the search user name field can be typed into. */
    public boolean isUsernameFieldEnabled() {

        return isElementEnabled(usernameField);

    }

    /** @return true when the Status dropdown can be opened. */
    public boolean isArrowButtonEnabled() {

        return isElementEnabled(dropDownArrow);

    }

    /** @return true when the Status dropdown arrow is visible. */
    public boolean isArrowButtonDisplayed() {

        return isElementDisplayed(dropDownArrow);

    }

    /** @return true when the "Enabled" option can be picked. */
    public boolean isEnabledOptionEnabled() {

        return isElementEnabled(enabledOption);

    }

    /** @return true when the "Enabled" option is visible. */
    public boolean isEnabledOptionDisplayed() {

        return isElementDisplayed(enabledOption);

    }
    /** @return true when the Search button can be clicked. */
    public boolean isSearchButtonEnabled() {

        return isElementEnabled(searchButton);

    }

    /** @return true when the Search button is visible. */
    public boolean isSearchButtonDisplayed() {

        return isElementDisplayed(searchButton);

    }
    /** @return true when the Reset button can be clicked. */
    public boolean isResetButtonEnabled() {

        return isElementEnabled(resetButton);

    }

    /** @return true when the Reset button is visible. */
    public boolean isResetButtonDisplayed() {

        return isElementDisplayed(resetButton);

    }

    /** @return true when the user menu can be opened. */
    public boolean isUserDropDownEnabled() {

        return isElementEnabled(userDropDown);

    }

    /** @return true when the user menu is visible. */
    public boolean isUserDropDownDisplayed() {

        return isElementDisplayed(userDropDown);

    }
    /** @return true when Logout can be clicked. */
    public boolean isLogoutOptionEnabled() {

        return isElementEnabled(logoutOption);

    }

    /** @return true when Logout is visible. */
    public boolean isLogoutOptionDisplayed() {

        return isElementDisplayed(logoutOption);

    }

}
