package com.inventory.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.inventory.base.DriverFactory;
import com.inventory.utils.ExtentReportManager;
import com.inventory.utils.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final ExtentReports extent = ExtentReportManager.getReportInstance();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");

        try {
            String screenshotPath = ScreenshotUtil.captureScreenshot(
                    DriverFactory.getDriver(),
                    result.getMethod().getMethodName()
            );

            test.get().addScreenCaptureFromPath("../" + screenshotPath);

        } catch (Exception e) {
            test.get().log(Status.WARNING, "Screenshot capture failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, result.getThrowable());

        try {
            String screenshotPath = ScreenshotUtil.captureScreenshot(
                    DriverFactory.getDriver(),
                    result.getMethod().getMethodName()
            );

            test.get().addScreenCaptureFromPath("../" + screenshotPath);

        } catch (Exception e) {
            test.get().log(Status.WARNING, "Screenshot capture failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}