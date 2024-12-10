package testCases;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class test extends BaseClass {
	
	@Test
	@Parameters({ "os", "browser" })

	public void remoteDemo(String os, String br) throws MalformedURLException {
		
		// URL of the Selenium Grid Hub
        String gridURL = "http://localhost:4444/wd/hub"; // Replace with your Grid Hub URL

        // Desired Capabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(br); // Browser name: chrome, firefox, etc.
        capabilities.setVersion(""); // Optional: specify version
        capabilities.setCapability("enableVNC", true); // Enable VNC if supported
        capabilities.setCapability("enableVideo", false); // Enable/disable video recording

        // Create RemoteWebDriver instance
        WebDriver driver = new RemoteWebDriver(new URL(gridURL), capabilities);

        try {
            // Navigate to a website
            driver.get("https://www.google.com/");

            // Perform simple actions
            WebElement heading = driver.findElement(By.tagName("h1"));
            System.out.println("Page Heading: " + heading.getText());

            // Print current URL
            System.out.println("Current URL: " + driver.getCurrentUrl());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the driver
            driver.quit();
        }
    }
	
	
}
