package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {

		super(driver);
	}
	
	@FindBy(xpath = "//span[normalize-space()='My Account']")
	WebElement linkMyAccount;
	
	@FindBy (xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Login']")
	WebElement btnLogin;
	
	@FindBy (xpath = "//*[@id=\"input-email\"]")
	WebElement txtLoginEmail;
	
	@FindBy (xpath = "//*[@id=\"input-password\"]")
	WebElement txtLoginpwd;
	
	@FindBy (xpath = "//*[@type=\"submit\"]")
	WebElement btnSubmit;
	
	public void clickMyAccount() {
		linkMyAccount.click();
	}
	
	public void clickLogin() {
		
		btnLogin.click();
	}

	public void enterEmail(String email) {
		
		txtLoginEmail.sendKeys(email);
		
	}
	
	public void enterPwd(String pwd){
		
		txtLoginpwd.sendKeys(pwd);
		
	}
	
	public void clickSubmit() {
		btnSubmit.click();
	}
	
}
