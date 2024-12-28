package testCases;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class test  {
	
	@Test
	public void getTest() {
		
		WebDriver driver = new ChromeDriver();
		driver.get("https://erail.in/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		WebElement fromSt =  driver.findElement(By.xpath("//input[@id='txtStationFrom']"));
		fromSt.clear();
		fromSt.sendKeys("DEL");
		
		List<WebElement> list = driver.findElements(By.xpath("/html/body/div[6]/div/div/div"));
		
		for(WebElement ele: list) {
			
			System.out.println(ele.getAttribute("title"));
		}
		
	}
	
}
