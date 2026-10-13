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

public class BrowserAction {

    private WebDriver driver;
    private WebDriverWait wait;

    public BrowserAction(WebDriver driver) {
        this.driver = driver;
        long seconds = Long.parseLong(BaseClass.properties.getProperty("waitSeconds", "10").trim());
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        this.wait.ignoring(StaleElementReferenceException.class);
    }

    public void click(By element) {
        wait.until(d -> {
            try {
                d.findElement(element).click();
                return true;
            } catch (ElementNotInteractableException
                    | StaleElementReferenceException | org.openqa.selenium.NoSuchElementException e) {
                return false;
            }
        });
    }

    public void sendKeys(By element, String data) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(element)).sendKeys(data);
    }

    public boolean isElementDisplayed(By element) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(element)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementEnabled(By element) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(element)).isEnabled();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String waitForUrl(String expectedUrl) {
        try {
            wait.until(ExpectedConditions.urlToBe(expectedUrl));
        } catch (TimeoutException e) {
            // Fall through: the assertion in the test reports the URL we ended up on
        }
        return driver.getCurrentUrl();
    }
}
