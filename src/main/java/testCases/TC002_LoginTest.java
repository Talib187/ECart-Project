package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.BasePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC002_LoginTest extends BaseClass {

	@Test (groups = {"sanity", "master"})
	public void verifyLogin() {

		logger.info("********** Starting TC_002Login Test**************");

		try {

			LoginPage lp = new LoginPage(driver);

			lp.clickMyAccount();
			lp.clickLoginBtn();
			lp.enterEmail(prop.getProperty("email"));
			lp.enterPwd(prop.getProperty("password"));
			lp.clickSubmit();

			MyAccountPage mp = new MyAccountPage(driver);

			boolean loginPage = mp.isMyAccountPageExist();

			Assert.assertEquals(loginPage, true, "Login Failed");

		}

		catch (Exception e) {

			System.out.println(e);
			Assert.fail();
		}

		logger.info("********** Finished TC_002Login Test **************");

	}
}
