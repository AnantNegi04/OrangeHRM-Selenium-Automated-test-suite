package ExtentReports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestListener implements ITestListener {

    //Created hash map to store thread id, test key value pair to avoid conflicting scenarios in parallel run
    private static final Map<Long, ExtentTest> extentTestMap = new HashMap<>();

    @Override
    public void onTestStart(ITestResult result) {
        WebDriver driver = new ChromeDriver();
        result.setAttribute("driver", driver);
        ExtentTest test = ExtentManager.getInstance().createTest(result.getMethod().getMethodName());
        extentTestMap.put(Thread.currentThread().getId(), test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTestMap.remove(Thread.currentThread().getId()).log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTestMap.remove(Thread.currentThread().getId()).log(Status.FAIL, result.getThrowable());

        /*ITestListener object allows to get the current executed test and it's attribute as every
           test class implements BaseTest class which has driver hence every child inherits it, thus
           from the result object we were able to get the driver attribute as object and we typecasted it to
           WebDriver
         */
        WebDriver driver = (WebDriver)result.getTestContext().getAttribute("driver");

        if (driver != null) {
            try {
                //typecast driver to Takescreenshot
                TakesScreenshot screenshot = (TakesScreenshot)driver;

                //Defines the src as FILE type and the screenshot output of FILE type as well using method getScreenshotAs
                File src = screenshot.getScreenshotAs(OutputType.FILE);

                //path where we want to store screenshot
                String path = "./screenshots/" + result.getMethod().getMethodName() + ".png";

                //creating File object from the string path
                File destination = new File(path);

                FileHandler.copy(src, destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTestMap.remove(Thread.currentThread().getId()).log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
    }
}
