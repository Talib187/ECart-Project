package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseClass;


public class ExtentReportManager implements ITestListener {

	public static ExtentSparkReporter sparkReporter; // UI of the Report
	public static ExtentReports extent; // populate common info in the report.
	public static ExtentTest test; // Creating test cases entries in the report and update the status in the test
							// method.
	String repName;

	public void onStart(ITestContext context) {
		// UI of the report

		String timeStamp = new SimpleDateFormat("dd-MM-yy-HH.mm").format(new Date());
		repName = "Test-Report-" + timeStamp + ".html";

		String reportPath = "C:\\Users\\mtali\\eclipse-workspace\\opencart\\Reports\\" + repName;

		// String reportPath = System.getProperty("user.dir") + "/test-output/" +
		// repName;

		System.out.println(reportPath);
		sparkReporter = new ExtentSparkReporter(reportPath);
		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Functional Testing");
		sparkReporter.config().setTheme(Theme.STANDARD);
		

		// Attaching the test in the UI.
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Username", System.getProperty("user.name"));
		
		String os = context.getCurrentXmlTest().getParameter("os"); // this will read the os value from xml.
		extent.setSystemInfo("Operating System", os);

		String browser = context.getCurrentXmlTest().getParameter("browser"); // this will read browser from testng.xm.
		extent.setSystemInfo("Browse", browser);

		List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}

	}

	public void onTestSuccess(ITestResult result) {

		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // To display the group in report.
		test.log(Status.PASS, "Passed test case is: " + result.getName());
		test.log(Status.PASS, result.getName() + " got successfully executed");

	}

	public void onTestFailure(ITestResult result) {

		test = extent.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.FAIL, result.getName() + " got failed.");

		test.log(Status.INFO, "Test failed reason is: " + result.getThrowable().getMessage());

		try {

			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {

		test = extent.createTest(result.getClass().getName());
		test.log(Status.SKIP, "Failed test case is: " + result.getName());
		test.log(Status.INFO, "Test failed reason is: " + result.getThrowable().getMessage());

	}

	@AfterSuite
	public void onFinish(ITestContext context) {

		extent.flush();

		String pathOfExtentReport = System.getProperty("user.dir") + "//Reports//" + repName;
		File extentReport = new File(pathOfExtentReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// To send the report on the email:

		try {

		
					
				String extentReportPath = 	"file:///" + System.getProperty("user.dir") + "\\Reports\\" + repName;
			
			URL url = extentReport.toURI().toURL();

			/// Create email message:

	/*		ImageHtmlEmail email = new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("talibmohd0099@gmail.com", "ugwk qkbc bxnd ylvy"));
			email.setSSLOnConnect(true);
			email.setFrom("talibmohd0099@gmail.com");
			email.setSubject("Automation Test Result");
			email.setMsg("Please find attached report");
			email.addTo("mtalib187@gmail.com"); // Receiver
			email.attach(url, "extent report", "please check report");
			email.send();
			*/
			

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
