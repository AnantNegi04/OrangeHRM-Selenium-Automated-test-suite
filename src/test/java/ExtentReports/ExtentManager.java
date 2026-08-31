package ExtentReports;

import baseTest.BaseTests;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager extends BaseTests {

    private static ExtentReports extent;

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReporter.html");
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("Test Results");
            spark.config().setTheme(Theme.DARK);
            extent.attachReporter(spark);
            extent.setSystemInfo("Environment", "QA");
        }

        return extent;
    }
}
