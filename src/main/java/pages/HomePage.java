package pages;

import browser_actions.BrowserAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * OrangeHRM dashboard (after login): the side menu entry that opens Admin.
 */
public class HomePage extends BrowserAction {
    private WebDriver driver;
    /** Page object for the browser of the running test class. */
    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    // Side menu entry, found by its visible text "Admin"
    private By adminOption = By.xpath("//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name' and text()='Admin']");

    /** Opens the Admin module from the side menu. */
    public void admin() {
      click(adminOption);
    }

    /** @return true when the Admin menu entry can be clicked. */
    public boolean isAdminOptionEnabled() {

        return isElementEnabled(adminOption);

    }

    /** @return true when the Admin menu entry is visible (the menu collapses in narrow windows). */
    public boolean isAdminOptionDisplayed() {

        return isElementDisplayed(adminOption);

    }
}
