package listeners;

import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;

import pages.BaseClass;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            String screenshotPath = BaseClass.takeScreenshot(result.getMethod().getMethodName());
            BaseClass.extentTest.fail("Screenshot at the failure",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
