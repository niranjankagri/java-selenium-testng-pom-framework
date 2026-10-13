package pages;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.PropertyLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;


/**
 * Base class of every test class: browser, configuration and report.
 *
 * Run order (TestNG annotations):
 * <ol>
 * <li>{@link #beforeSuite()}: create the Extent Spark report (test-output/report.html).</li>
 * <li>{@link #Setup()}: load config.properties, start the browser from it and open the login page.</li>
 * <li>{@link #beforeMethod(Method)} / {@link #afterMethod(ITestResult)}: one report entry per test
 *     with its result.</li>
 * <li>{@link #teardown()}: close the browser after the class.</li>
 * <li>{@link #afterSuite()}: write the report to disk.</li>
 * </ol>
 * The tests of a class share one browser session and run in priority order
 * (login, admin, search, logout).
 */
public class BaseClass {

    // One browser for the running test class (static: no parallel runs)
    private static WebDriver driver ;
    public static ExtentReports extentReports;
    // Report entry of the running test; TestListener adds the failure screenshot to it
    public static ExtentTest extentTest;
    // Settings from config.properties, after -D overrides (see PropertyLoader)
    public static Properties properties = new Properties();

    public static PropertyLoader propertyLoader;

    /**
     * Creates the Extent Spark report once for the whole suite.
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        // Set up Extent report with ExtentSparkReport
        extentReports = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/report.html");
        extentReports.attachReporter(sparkReporter);
    }

    /**
     * Loads the configuration, starts the browser and opens the login page.
     */
    @BeforeClass(alwaysRun = true)
    public void Setup(){
        propertyLoader = new PropertyLoader();

        boolean headless = Boolean.parseBoolean(properties.getProperty("headless", "false"));
        driver = createDriver(properties.getProperty("browser", "chrome"), headless);
        driver.get(properties.getProperty("loginPageURL"));
        // A headless window already has its size; maximize() would shrink it to the default
        if (!headless) {
            driver.manage().window().maximize();
        }
        Assert.assertEquals(driver.getCurrentUrl() , properties.getProperty("loginPageURL"), "Invalid URL");

    }

    /**
     * Starts the browser named in config.properties. Selenium Manager downloads the
     * matching driver, so nothing has to be installed besides the browser.
     *
     * @param browser  chrome, firefox or edge
     * @param headless true to run without a window, at 1920x1080
     * @return WebDriver the started browser
     */
    private static WebDriver createDriver(String browser, boolean headless) {
        switch (browser.trim().toLowerCase()) {
            case "firefox": {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless", "--width=1920", "--height=1080");
                }
                return new FirefoxDriver(options);
            }
            case "edge": {
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    options.addArguments("--headless=new", "--window-size=1920,1080");
                }
                return new EdgeDriver(options);
            }
            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new", "--window-size=1920,1080");
                }
                return new ChromeDriver(options);
            }
            default:
                throw new IllegalArgumentException("Unknown browser '" + browser + "', expected chrome, firefox or edge");
        }
    }

    /**
     * @return WebDriver the browser of the running test class, for the page objects
     */
    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * Closes the browser after the class, also after a failure.
     */
    @AfterClass(alwaysRun = true)
    public void teardown(){
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Writes the Extent report to test-output/report.html.
     */
    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        //Writing everything to report
        extentReports.flush();
    }

    /**
     * Creates the report entry of the test that is about to run.
     */
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        extentTest = extentReports.createTest(method.getName());
    }

    /**
     * Records the test's result in its report entry (the failure screenshot is
     * added by TestListener just before this runs).
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.skip("Test skipped: " + result.getThrowable());
        } else {
            extentTest.pass("Test passed");
        }
    }


    /**
     * Saves the current browser screen as Screenshots/&lt;methodName&gt;.png.
     *
     * @param methodName name of the test, used as the file name
     * @return String absolute path of the image, for the report
     * @throws IOException when the file cannot be written
     */
    public static String takeScreenshot(String methodName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        // Selenium returns PNG data, so the file gets a .png name (it used to be called .jpg)
        String destination = System.getProperty("user.dir") + "/Screenshots/" + methodName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

}
