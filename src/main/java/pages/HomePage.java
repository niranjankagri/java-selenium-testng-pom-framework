package pages;

import browser_actions.BrowserAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BrowserAction {
    private WebDriver driver;
    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private By adminOption = By.xpath("//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name' and text()='Admin']");

    public void admin() {
      click(adminOption);
    }

    public boolean isAdminOptionEnabled() {

        return isElementEnabled(adminOption);

    }

    public boolean isAdminOptionDisplayed() {

        return isElementDisplayed(adminOption);

    }
}
