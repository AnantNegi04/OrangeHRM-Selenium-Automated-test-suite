package ExtentReports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TestListener implements ITestListener {

    private static final Map<Long, ExtentTest> extentTestMap = new HashMap<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = ExtentManager.getInstance().createTest(result.getMethod().getMethodName());
        extentTestMap.put(Thread.currentThread().getId(), test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTestMap.remove(Thread.currentThread().getId()).log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTestMap.get(Thread.currentThread().getId()).log(Status.FAIL, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTestMap.get(Thread.currentThread().getId()).log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
    }
}
