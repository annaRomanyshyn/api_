package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import static utils.TestLogger.*;

public class TestListener implements ITestListener  {
    public void onTestStart(ITestResult result) {
        apiLog.info("Test: " + result.getName() + "is started");
    }

    public void onTestSuccess(ITestResult result) {
        apiLog.info("Test: " + result.getName() + "passed");
    }

    public void onTestFailure(ITestResult result) {
        apiLog.error("Test: " + result.getName() + "FAILED");
    }

    public void onTestSkipped(ITestResult result) {
        apiLog.error("Test: " + result.getName() + "skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {
        apiLog.error("Test: " + context.getName() + "is finished");;
    }
}
