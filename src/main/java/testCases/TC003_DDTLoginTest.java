package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.BasePage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

public class TC003_DDTLoginTest extends BaseClass {

	/*
	 * Data is valid - Login success, test pass -logout Data is valid - Login
	 * failed, test failed - no logout
	 * 
	 * Data is invalid - login success - test fail - no logout Data is invalid login
	 * failed - test pass - no logut
	 */

	@Test(dataProvider = "loginData", dataProviderClass = DataProviders.class, groups = {"dataDriven", "master"} ,enabled=true) // if data provider is in same metod we
																				// dont need to specify the class
	public void VerifyLoginDDt(String email, String pwd, String actrCredential) {

		HomePage hp = new HomePage(driver);

		hp.clickMyAccount();
		hp.clickLogIn();

		// Login

		LoginPage lp = new LoginPage(driver);

		lp.enterEmail(email);
		lp.enterPwd(pwd);
		lp.clickSubmit();

		// My Account page

		MyAccountPage mp = new MyAccountPage(driver);

		boolean status = mp.isMyAccountPageExist();

		if (actrCredential.equalsIgnoreCase("valid")) {
			if (status == true) {

				Assert.assertTrue(true);
				mp.clickLogout();

			}

			else {
				Assert.assertTrue(false);
			}
		}

		if (actrCredential.equalsIgnoreCase("Invalid")) {

			if (status == true) {
				mp.clickLogout();
				Assert.assertTrue(false);
			}

			else {

				Assert.assertTrue(true);
			}
		}

	}

}
