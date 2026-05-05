package com.inventory.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
	private static ExtentReports extent;

	public static ExtentReports getReportInstance() {
		if (extent == null) {
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String reportPath = "reports/InventoryProReport_" + timestamp + ".html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
			spark.config().setDocumentTitle("Inventory Pro Automation Report");
			spark.config().setReportName("Inventory Pro Regression Suite");
			extent = new ExtentReports();
			extent.attachReporter(spark);
			extent.setSystemInfo("Project", "Inventory Pro");
			extent.setSystemInfo("Framework", "Maven + Selenium + TestNG + POM");
		}
		return extent;
	}
}