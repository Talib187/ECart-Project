package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test (groups = {"regression", "master"})
	public void verifyAccountRegistratioin() {

		logger.info("********** Starting TC001_AccountRegistrationTest *********");
		
		try {
			
			HomePage hp = new HomePage(driver);

			hp.clickMyAccount();
			hp.clickRegister();

			logger.info("Homepage Activity Ended");

			AccountRegistrationPage ac = new AccountRegistrationPage(driver);

			logger.info("Registration Page object class initiated");

			String firstName = randomName().toUpperCase();
			String lastName = randomName().toUpperCase();
			String email = randomName() + "@yopmail.com";
			String telephone = randomTelephone();
			String password = randomPassword();

			logger.info("Initiated Entering Account Registration Information");

			ac.setFirstName(firstName);
			ac.setLastName(lastName);
			ac.setEmail(email);
			ac.setTelephone(telephone);
			ac.setPassword(password);
			ac.setPrivacyPolicy();
			ac.clickContinue();

			logger.info("Account Registratoin activity ended.");

			String msg = ac.getSuccessLabel();
			Assert.assertEquals("Your Account Has Been Created!", msg, "Registration Failed");

			// Now I will check if I can login to the system with the new credentials.
			logger.info("Account registration verified successfully");

			ac.loggOut();

			logger.info("Logged out from account and started re-login with new account");

			hp.clickMyAccount();
			hp.clickLogIn();

			ac.enterNewEmail(email);
			ac.enterNewPassword(password);

			ac.clickSubmit();

			logger.info("Logged in with new account creation.");

			logger.info("Test case executed");

			
		} catch (Exception e) {
			
			logger.error("Test Failed");
			logger.debug("Debug logs.........");
			Assert.fail();

		}
		
		logger.info("********** Ended TC001_AccountRegistrationTest *********");

		}

}
