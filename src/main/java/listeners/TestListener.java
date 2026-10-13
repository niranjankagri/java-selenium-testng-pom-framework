package listeners;

import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;

import pages.BaseClass;

/**
 * TestNG listener, registered in every suite file (testng.xml, groupsTestng.xml,
 * ReportDemoSuite.xml).
 * On a failure it saves a screenshot and adds it to the failed test's own entry
 * in the Extent report.
 */
public class TestListener implements ITestListener {

    /**
     * Runs right after a test failed, before BaseClass.afterMethod() records the error.
     *
     * @param result the failed test
     */
    @Override
    public void onTestFailure(ITestResult result) {
        try {
            String screenshotPath = BaseClass.takeScreenshot(result.getMethod().getMethodName());
            BaseClass.extentTest.fail("Screenshot at the failure",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (IOException e) {
            // A missing screenshot must not hide the real failure
            e.printStackTrace();
        }
    }

}
