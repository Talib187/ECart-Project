package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseClass;

public class Reporting implements ITestListener {

    public ExtentSparkReporter sparkReporter; // UI of the Report
    public ExtentReports extent;  // populate common info in the report.
    public ExtentTest test; // Creating test cases entries in the report and update the status in the test method.
    String repName;

    @Override
    public void onStart(ITestContext context) {
        // Create Reports directory if it doesn't exist
        File reportsDir = new File(System.getProperty("user.dir") + File.separator + "Reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }

        // UI of the report
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";
        String reportPath = reportsDir + File.separator + repName;

        System.out.println("Report Path: " + reportPath);
        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

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
        extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups()); // To display the group in the report.
        test.log(Status.PASS, "Passed test case is: " + result.getName());
        test.log(Status.PASS, result.getName() + " got successfully executed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
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

    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getClass().getName());
        test.log(Status.SKIP, "Skipped test case is: " + result.getName());
        test.log(Status.INFO, "Test skipped reason is: " + result.getThrowable().getMessage());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();

        String pathOfExtentReport = System.getProperty("user.dir") + File.separator + "Reports" + File.separator + repName;
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // To send the report via email:
        try {
            URL url = extentReport.toURI().toURL();

            // Create email message:
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("your-email@gmail.com", "your-password")); // Replace with your actual password
            email.setSSLOnConnect(true);
            email.setFrom("your-email@gmail.com");
            email.setSubject("Test Result");
            email.setMsg("Please find the attached report");
            email.addTo("receiver-email@gmail.com"); // Receiver
            email.attach(extentReport);
            email.send();
        } catch (IOException | EmailException e) {
            e.printStackTrace();
        }
    }
}
