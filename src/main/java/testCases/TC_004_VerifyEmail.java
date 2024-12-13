package testCases;

import java.time.Duration;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FromTerm;
import javax.mail.search.SearchTerm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class TC_004_VerifyEmail {

	@Test
	public void verifyEmail() {

	/*	WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://www.amazon.in/");

		driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]/span")).click();
		driver.findElement(By.xpath("//span[@class='a-expander-prompt']")).click();
		driver.findElement(By.xpath("//a[@id='auth-fpp-link-bottom']")).click();

		driver.findElement(By.xpath("//input[@id='ap_email']")).sendKeys("talibmohd0099@gmail.com");
		driver.findElement(By.xpath("//input[@id='continue']")).click();
		
		*/

		String host = "imap.gmail.com";
		String username = "talibmohd0099@gmail.com";
		String appPassword = "ugwk qkbc bxnd ylvy";
		String fromAddress = "workday@workday.crowe.com";

		try {
			// Set properties for IMAP protocol
			Properties properties = new Properties();
			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", "993");
			properties.put("mail.imap.ssl.enable", "true");

			// Create session and connect
			Session session = Session.getInstance(properties);
			Store store = session.getStore("imap");
			store.connect(host, username, appPassword);

			// Open inbox folder
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			
			SearchTerm search = new FromTerm(new InternetAddress(fromAddress));

			// Retrieve messages
			Message[] messages = inbox.search(search);
			for (Message message : messages) {
				// Get sender email
				Address[] from = message.getFrom();
				String senderEmail = (from != null && from.length > 0) ? from[0].toString() : "Unknown sender";

				// Get subject
				String subject = message.getSubject();

				// Get email body
				String body = "";
				if (message.isMimeType("text/plain")) {
					body = message.getContent().toString();
				} else if (message.isMimeType("multipart/*")) {
					Multipart multipart = (Multipart) message.getContent();
					for (int i = 0; i < multipart.getCount(); i++) {
						BodyPart bodyPart = multipart.getBodyPart(i);
						if (bodyPart.isMimeType("text/plain")) {
							body = bodyPart.getContent().toString();
							break;
						}
					}
				}

				// Print email details
				System.out.println("From: " + senderEmail);
				System.out.println("Subject: " + subject);
				System.out.println("Body: " + body);
				System.out.println("-------------------------------");
			}

			// Close connections
			inbox.close(false);
			store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
