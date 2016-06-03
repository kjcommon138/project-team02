package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.*;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class SendEmailNotificationTest extends iTrustSeleniumTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		gen.clearAllTables();
		gen.icd9cmCodes();
		gen.ndCodes();
		gen.hospitals();
		gen.hcp1();
		gen.hcp2();
		gen.hcp3();
		gen.er4();
		gen.patient9();
		
		gen.UC32Acceptance();
		gen.clearLoginFailures();
	}
	
	public void testPrescriptionRenewalEmail() throws Exception {
		//Login as Tester Arehart
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9900000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    //Logging Login
	    assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
		//Select Potential Prescription-Renewals under Other
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[10]/div/h2")).click();
	    driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
	    //Click Darryl Thompson and check that the email form is displayed
	    driver.findElement(By.linkText("Darryl Thompson")).click();
	    assertTrue(driver.findElement(By.cssSelector("h3")).isDisplayed());
	    assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 99L, "");
	}
	
	public void testOfficeVisitRemindersEmail() throws Exception {
		//Login as Tester Arehart
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9900000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    //Logging Login
	    assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
		//Select Office Visit Reminders under Office Visits
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
	    driver.findElement(By.linkText("Office Visit Reminders")).click();
	    //Click the Get Reminders button and check that Darryl Thompson is displayed
	    driver.findElement(By.id("getReminders")).click();
	    assertTrue(driver.findElement(By.linkText("Darryl Thompson")).isDisplayed());
	    //Click Darryl Thompson and check that the email form is displayed
	    driver.findElement(By.linkText("Darryl Thompson")).click();
	    assertTrue(driver.findElement(By.cssSelector("h3")).isDisplayed());
	    assertLogged(TransactionType.PATIENT_REMINDERS_VIEW, 9900000000L, 0L, "");
	}
	
	public void testSendAnEmail() throws Exception {
		//Login as Tester Arehart
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9900000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");		
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    //Logging Login
	    assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
		//Select Office Visit Reminders under Office Visits
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Office Visit Reminders")).click();
	    //Click the Get Reminders button and check that Darryl Thompson is displayed
	    driver.findElement(By.id("getReminders")).click();
	    assertTrue(driver.findElement(By.linkText("Darryl Thompson")).isDisplayed());
	    //Click Darryl Thompson and click the Send Email button
	    driver.findElement(By.linkText("Darryl Thompson")).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    //Check email was sent and logged
	    assertTrue(driver.findElement(By.cssSelector("font")).isDisplayed());
	    assertLogged(TransactionType.PATIENT_REMINDERS_VIEW, 9900000000L, 0L, "");
	}
	
}
