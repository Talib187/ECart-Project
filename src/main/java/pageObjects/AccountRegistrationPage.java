package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) {

		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement txtFirstName;

	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement txtLastName;

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtEmail;

	@FindBy(xpath = "//input[@id='input-telephone']")
	WebElement txtPhone;

	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txtPassword;

	@FindBy(xpath = "//input[@id='input-confirm']")
	WebElement txtConfirmPassword;

	@FindBy(xpath = "//input[@name='agree']")
	WebElement checkBoxPrivacy;

	@FindBy(xpath = "//input[@value='Continue']")
	WebElement btnContinue;
	
	@FindBy (xpath = "//*[@id=\"content\"]/h1")
	WebElement labelSuccess;
	
	@FindBy (xpath = "//*[@id=\"column-right\"]/div/a[13]")
	private WebElement btnLogout;
	
	@FindBy (xpath = "//*[@id=\"input-email\"]")
	WebElement txtLoginEmail;
	
	@FindBy (xpath = "//*[@id=\"input-password\"]")
	WebElement txtLoginpwd;
	
	@FindBy (xpath = "//*[@type=\"submit\"]")
	WebElement btnSubmit;
	
	

	public void setFirstName(String fName) {

		txtFirstName.sendKeys(fName);
	}

	public void setLastName(String lastName) {

		txtLastName.sendKeys(lastName);
	}

	public void setEmail(String email) {

		txtEmail.sendKeys(email);
	}

	public void setTelephone(String telephone) {

		txtPhone.sendKeys(telephone);
	}

	public void setPassword(String password) {

		txtPassword.sendKeys(password);
		txtConfirmPassword.sendKeys(password);
	}

	public void setPrivacyPolicy() {

		checkBoxPrivacy.click();
	}

	public void clickContinue() {
		btnContinue.click();
	}
	
	public String getSuccessLabel() {
		
	return	labelSuccess.getText();
	}
	
	public void loggOut() {
		btnLogout.click();
	}
	public void enterNewEmail(String newEmail) {
		
		txtLoginEmail.sendKeys(newEmail);
		
	}
	
	public void enterNewPassword(String newPwd){
		
		txtLoginpwd.sendKeys(newPwd);
		
	}
	
	public void clickSubmit() {
		btnSubmit.click();
	}
	
}
