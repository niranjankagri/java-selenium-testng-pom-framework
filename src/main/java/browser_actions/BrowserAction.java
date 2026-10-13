package browser_actions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.BaseClass;

/**
 * Element actions shared by every page object: click, type, visible/enabled checks, URL.
 *
 * Every action waits for its element (explicit wait, up to "waitSeconds" from
 * config.properties), so pages and tests need no Thread.sleep. Page classes extend
 * this class and pass their locators (By) to these methods.
 */
public class BrowserAction {

    private WebDriver driver;
    // One wait per page object; stale elements are looked up again instead of failing
    private WebDriverWait wait;

    /**
     * @param driver the browser of the running test class
     */
    public BrowserAction(WebDriver driver) {
        this.driver = driver;
        long seconds = Long.parseLong(BaseClass.properties.getProperty("waitSeconds", "10").trim());
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        this.wait.ignoring(StaleElementReferenceException.class);
    }

    /**
     * Clicks the element as soon as a click goes through. OrangeHRM shows a loading
     * overlay after many actions; a click it intercepts is retried until the wait ends.
     *
     * @param element locator of the element
     */
    public void click(By element) {
        wait.until(d -> {
            try {
                d.findElement(element).click();
                return true;
            } catch (ElementNotInteractableException
                    | StaleElementReferenceException | org.openqa.selenium.NoSuchElementException e) {
                // ElementNotInteractableException also covers ElementClickInterceptedException
                return false;
            }
        });
    }

    /**
     * Types into the element once it is visible.
     *
     * @param element locator of the field
     * @param data    text to type
     */
    public void sendKeys(By element, String data) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element)).sendKeys(data);
    }

    /**
     * @param element locator of the element
     * @return boolean true when it becomes visible within the wait, false otherwise
     */
    public boolean isElementDisplayed(By element) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * @param element locator of the element
     * @return boolean true when it is visible and enabled, false when it never appears
     */
    public boolean isElementEnabled(By element) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(element)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * @return String the URL right now (no waiting)
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Waits until the browser shows the expected URL, e.g. after a click that navigates.
     *
     * @param expectedUrl the URL to wait for
     * @return String the URL at the end of the wait, so the test's assertion reports
     *         where the browser really is if it never got there
     */
    public String waitForUrl(String expectedUrl) {
        try {
            wait.until(ExpectedConditions.urlToBe(expectedUrl));
        } catch (TimeoutException e) {
            // Fall through: the assertion in the test reports the URL we ended up on
        }
        return driver.getCurrentUrl();
    }
}
