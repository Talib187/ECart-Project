package testCases;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseClass {

	public Logger logger;
	public  WebDriver driver;
	public Properties prop;
	public static ExtentSparkReporter sparkReporter;
	public ExtentReports extent; 
	public ExtentTest test;
	
	
	// test

	@BeforeClass(groups = { "sanity", "regression", "master", "dataDriven" })
	@Parameters({ "os", "browser" })
	
	public void setUp(String os, String br) throws IOException {

		// Loading config.properties fiel

		FileReader file = new FileReader(
				"C:\\Users\\mtali\\eclipse-workspace\\opencart\\src\\test\\resources\\config.properties");
		prop = new Properties();
		prop.load(file);

		logger = LogManager.getLogger(this.getClass());

		
		// For Remote driver Exection:
		
		if (prop.getProperty("execution_env").equalsIgnoreCase("remote")) {
		    try {
		        // Determine the operating system for remote execution
		        Platform platform;
		        if (os.equalsIgnoreCase("windows")) {
		            platform = Platform.WINDOWS;
		        } else if (os.equalsIgnoreCase("linux")) {
		            platform = Platform.LINUX;
		        } else {
		            throw new IllegalArgumentException("Unsupported OS: " + os);
		        }

		        // Determine the browser for remote execution
		        switch (br.toLowerCase()) {
		            case "chrome":
		                ChromeOptions chromeOptions = new ChromeOptions();
		                chromeOptions.setPlatformName(platform.name());
		                chromeOptions.addArguments("--headless");
		                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
		                break;

		            case "firefox":
		                FirefoxOptions firefoxOptions = new FirefoxOptions();
		                firefoxOptions.addArguments("--headless");
		                firefoxOptions.setPlatformName(platform.name());
		                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), firefoxOptions);
		                break;

		            case "edge":
		                EdgeOptions edgeOptions = new EdgeOptions();
		                edgeOptions.addArguments("--headless");
		                edgeOptions.setPlatformName(platform.name());
		                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), edgeOptions);
		                break;

		            default:
		                throw new IllegalArgumentException("Unsupported browser: " + br);
		        }

		        System.out.println("Driver initialized for remote execution on " + platform + " with " + br);
		    } catch (MalformedURLException e) {
		        System.err.println("Invalid Grid URL: " + e.getMessage());
		    } catch (Exception e) {
		        System.err.println("Error during driver initialization: " + e.getMessage());
		    }
		}		
		// For the local execution
		
		if (prop.getProperty("execution_env").equalsIgnoreCase("local")) {

			switch (br.toLowerCase()) {

			case "chrome":
				driver = new ChromeDriver();
				break;

			case "edge":
				driver = new EdgeDriver();
				break;

			case "firefox":
				driver = new FirefoxDriver();
				break;

			default:
				System.out.println("Invalid brower name");
				return;

			}

		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		// driver.get("http://localhost/opencart/upload/");

		// driver.get("https://tutorialsninja.com/demo/");

		driver.get(prop.getProperty("appURL")); // reading url from properties file.
		logger.info("URL entered in the browser.");

	}

	@AfterClass(groups = { "sanity", "regression", "master", "dataDriven" })
	public void tearDown() {
		driver.quit();
	}

	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		String trgPath = System.getProperty("user.dir") + "\\\\Screenshots\\" + tname + "-" + timeStamp + ".png";
		File trg = new File(trgPath);

		src.renameTo(trg);

		return trgPath;

	}

	public String randomName() {
		String generatedString = RandomStringUtils.randomAlphanumeric(6);
		return generatedString;
	}

	public String randomTelephone() {
		String telephone = RandomStringUtils.randomNumeric(10);
		return telephone;
	}

	public String randomPassword() {
		String generatedString = RandomStringUtils.randomAlphanumeric(4);
		String generatedNumber = RandomStringUtils.randomNumeric(4);
		String password = generatedString + generatedNumber;
		return password;

	}
}
