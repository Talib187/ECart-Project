package pageObjects;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

	WebDriver driver;

	public BasePage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
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
