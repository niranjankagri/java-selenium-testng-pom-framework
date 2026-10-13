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
import java.time.Duration;


public class BaseClass {

    private static WebDriver driver ;
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;
    public static Properties properties = new Properties();

    public static PropertyLoader propertyLoader;

    @BeforeClass(alwaysRun = true)
    public void Setup(){
        // Set up Extent report with ExtentSparkReport
        extentReports = new ExtentReports();

        propertyLoader = new PropertyLoader();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/report.html");
        extentReports.attachReporter(sparkReporter);

        driver = createDriver(properties.getProperty("browser", "chrome"),
                Boolean.parseBoolean(properties.getProperty("headless", "false")));
        driver.get(properties.getProperty("loginPageURL"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Assert.assertEquals(driver.getCurrentUrl() , properties.getProperty("loginPageURL"), "Invalid URL");

    }

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

    public static WebDriver getDriver() {
        return driver;
    }

    @AfterClass(alwaysRun = true)
    public void teardown(){
     driver.quit();

     //Writing everything to report
        extentReports.flush();
    }

    // Before each test method, create a test in report
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        extentTest = extentReports.createTest(method.getName());
    }

   // After each test method, log the test result
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


    public static String takeScreenshot(String methodName) throws IOException {
        // Assuming driver is your WebDriver instance and it's already initialized
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "/Screenshots/" + methodName + ".jpg";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }






}
