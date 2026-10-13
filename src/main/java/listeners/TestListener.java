package listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.testng.ITestListener;
import org.testng.ITestResult;
import pages.BaseClass;

import java.awt.*;
import java.io.File;
import java.io.IOException;



public class TestListener  implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        try {
        String screenshotPath = BaseClass.takeScreenshot(result.getMethod().getMethodName());

        BaseClass.extentReports.createTest(result.getMethod().getMethodName())
                .fail(result.getThrowable().getMessage(),
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());


            String reportFilePath = "E:/Java_Selenuim_Framework/Java_Selenium_Testng_POM_Framework/test-output/report.html";

            openReport(reportFilePath);
    } catch (IOException e){
            e.printStackTrace();
        }
}

    private static void openReport(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            try {

                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found: " + filePath);
        }
    }

}