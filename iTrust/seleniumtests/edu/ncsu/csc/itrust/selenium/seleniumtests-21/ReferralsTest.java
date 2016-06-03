package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ReferralsTest extends iTrustSeleniumTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.standardData();
		gen.officeVisit3();
		gen.hcp4();
		gen.hcp5();
		gen.referrals();
		gen.referral_sort_testdata();
		gen.patient2();
		gen.referrals();
	}
	
	public void testCreateNewReferral() throws Exception {
		//Login as Kelly Doctor
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    //Check login succeeded and logged
	    assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    //Click on Document Office Visit under Office Visits
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    //Search for MID '1' and select Random Person
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
	    driver.findElement(By.xpath("//input[@value='1']")).submit();
	    //Click the date 09/17/2009 and then click the Add Refferral button
	    driver.findElement(By.linkText("09/17/2009")).click();
	    driver.findElement(By.id("add_referral")).click();
	    //Enter Gandalf Stormcrow and click the button with his MID on it
	    driver.findElement(By.name("FIRST_NAME")).clear();
	    driver.findElement(By.name("FIRST_NAME")).sendKeys("Gandalf");
	    driver.findElement(By.name("LAST_NAME")).clear();
	    driver.findElement(By.name("LAST_NAME")).sendKeys("Stormcrow");
	    driver.findElement(By.xpath("//input[@value='User Search']")).click();
	    driver.findElement(By.xpath("(//input[@value='9000000003'])[2]")).click();
	    //Set priority to 1, fill out form, click submit, then check if the new referral is displayed in the table
	    new Select(driver.findElement(By.name("priority"))).selectByVisibleText("1");
	    driver.findElement(By.name("referralDetails")).clear();
	    driver.findElement(By.name("referralDetails")).sendKeys("See Gandalf. He will translate the engravings on that ring for you.");
	    driver.findElement(By.id("submitCreate")).click();
	    assertTrue(driver.findElement(By.xpath("//table[@id='referralsTable']/tbody/tr[3]/td[2]")).isDisplayed());
	}
	
	public void testDeleteExistingReferral() throws Exception {
		//Login as Kelly Doctor
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    //Check login succeeded and logged
	    assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    //Click Sent Referrals under Office Visit
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Sent Referrals")).click();
	    //Click the Details button next to the first entry, then click Delete and confirm deletion
	    driver.findElement(By.cssSelector("#editReferralForm > input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Edit Referral", driver.getTitle());
	    driver.findElement(By.id("submitDelete")).click();
	    driver.findElement(By.id("submitCreate")).click();
	    //check that you returned to the Sent Referrals page
	    assertEquals("iTrust - View Sent Referrals", driver.getTitle());	
	}
	
	public void testModifyExistingReferral() throws Exception {
		//Login as Kelly Doctor
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    //Check login succeeded and logged
	    assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		//Click Document Office Visit under Office Visits, search for MID 2, and select Andy Programmer
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    /*driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.id("searchBox")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2']")).submit();*/
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2']")).submit();
	    assertEquals("iTrust - Document Office Visit", driver.getTitle());
	    //Click 06/10/2007 and click the View/Edit button next to the 07/09/2007 referral
	    driver.findElement(By.linkText("06/10/2007")).click();
	    driver.findElement(By.xpath("(//input[@value='View/Edit'])[2]")).click();
	    //Set priority to 1, edit form, and click submit
	    new Select(driver.findElement(By.name("priority"))).selectByVisibleText("1");
	    driver.findElement(By.name("referralDetails")).clear();
	    driver.findElement(By.name("referralDetails")).sendKeys("Gandalf will take care of you--for a price!");
	    driver.findElement(By.id("submitEdit")).click();
	    //Check that the referral has been edited
	    assertEquals("Gandalf Stormcrow", driver.findElement(By.xpath("//table[@id='referralsTable']/tbody/tr[4]/td")).getText());
	    assertEquals("Gandalf will take care of you--for a price!", driver.findElement(By.xpath("//table[@id='referralsTable']/tbody/tr[4]/td[2]")).getText());
	    assertEquals("1", driver.findElement(By.xpath("//table[@id='referralsTable']/tbody/tr[4]/td[3]")).getText());
	    assertEquals("07/09/2007\n00:00 AM", driver.findElement(By.xpath("//table[@id='referralsTable']/tbody/tr[4]/td[4]")).getText());
	}
	
	public void testHCPViewSentReferrals() throws Exception {
		//Login as Tester Arehart
		driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000003");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    //Check login succeeded and logged
	    assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000003L, 0L, "");
		//Click on Sent Referrals under Office Visits
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Sent Referrals")).click();
	    //Check that referrals are populating the table in order
	    assertEquals("12/22/2011\n00:00 AM", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[3]/td[3]")).getText());
	    assertEquals("12/10/2011\n00:00 AM", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[4]/td[3]")).getText());
	    assertEquals("12/01/2010\n00:00 AM", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[5]/td[3]")).getText());
	    assertEquals("11/10/2010\n00:00 AM", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[6]/td[3]")).getText());
	    assertEquals("10/13/2010\n00:00 AM", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[7]/td[3]")).getText());
	    assertEquals("08/10/2010\n00:00 AM", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[8]/td[3]")).getText());
	    assertEquals("11/30/2009\n00:00 AM", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[9]/td[3]")).getText());
	    assertEquals("09/10/2009\n00:00 AM", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[10]/td[3]")).getText());
	    assertEquals("10/10/2008\n00:00 AM", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[11]/td[3]")).getText());
	    //Click the sort by HCP button and check that referrals are in order
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("Beaker Beaker", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[3]/td")).getText());
	    assertEquals("Beaker Beaker", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[4]/td")).getText());
	    assertEquals("Kelly Doctor", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[5]/td")).getText());
	    assertEquals("Kelly Doctor", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[6]/td")).getText());
	    assertEquals("Antonio Medico", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[7]/td")).getText());
	    assertEquals("Antonio Medico", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[8]/td")).getText());
	    assertEquals("Antonio Medico", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[9]/td")).getText());
	    assertEquals("Sarah Soulcrusher", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[10]/td")).getText());
	    assertEquals("Sarah Soulcrusher", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[11]/td")).getText());
	    //Click the sort by HCP button again to reverse the sort and check that referrals are in order
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("Beaker Beaker", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[11]/td")).getText());
	    assertEquals("Beaker Beaker", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[10]/td")).getText());
	    assertEquals("Kelly Doctor", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[9]/td")).getText());
	    assertEquals("Kelly Doctor", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[8]/td")).getText());
	    assertEquals("Antonio Medico", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[7]/td")).getText());
	    assertEquals("Antonio Medico", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[6]/td")).getText());
	    assertEquals("Antonio Medico", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[5]/td")).getText());
	    assertEquals("Sarah Soulcrusher", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[4]/td")).getText());
	    assertEquals("Sarah Soulcrusher", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[3]/td")).getText());
	    //Click the sort by patient button and check that referrals are in order
	    driver.findElement(By.cssSelector("#sortByPatient > input[type=\"submit\"]")).click();
	    assertEquals("Fozzie Bear", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[3]/td[2]")).getText());
	    assertEquals("Fozzie Bear", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[4]/td[2]")).getText());
	    assertEquals("Fozzie Bear", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[5]/td[2]")).getText());
	    assertEquals("Random Person", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[6]/td[2]")).getText());
	    assertEquals("Random Person", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[7]/td[2]")).getText());
	    assertEquals("Random Person", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[8]/td[2]")).getText());
	    assertEquals("Andy Programmer", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[9]/td[2]")).getText());
	    assertEquals("Andy Programmer", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[10]/td[2]")).getText());
	    assertEquals("Andy Programmer", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[11]/td[2]")).getText());
	    //Click the sort by priority button and check that referrals are in order
	    driver.findElement(By.cssSelector("#sortByPriority > input[type=\"submit\"]")).click();
	    assertEquals("1", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[3]/td[4]")).getText());
	    assertEquals("1", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[4]/td[4]")).getText());
	    assertEquals("1", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[5]/td[4]")).getText());
	    assertEquals("2", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[6]/td[4]")).getText());
	    assertEquals("2", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[7]/td[4]")).getText());
	    assertEquals("2", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[8]/td[4]")).getText());
	    assertEquals("3", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[9]/td[4]")).getText());
	    assertEquals("3", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[10]/td[4]")).getText());
	    assertEquals("3", driver.findElement(By.xpath("//table[@id='sentReferralsTable']/tbody/tr[11]/td[4]")).getText());
	}
	
	public void testHCPViewReferralsEdit() throws Exception {
		logIn("9000000003", "pw");
		//Check login succeeded and logged
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000003L, 0L, "");
		//Click on Sent Referrals under Office Visits
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Sent Referrals")).click();
	    assertEquals("iTrust - View Sent Referrals", driver.getTitle());
	    assertEquals(11, driver.findElements(By.xpath("//table[@id='sentReferralsTable']/tbody/tr")).size());
	    //driver.findElement(By.id("editReferralForm")).click();
	    driver.findElement(By.cssSelector("#editReferralForm > input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Edit Referral", driver.getTitle());
	    driver.findElement(By.id("submitEdit")).click();
	    assertFalse(driver.getPageSource().contains("NumberFormatException"));
	    assertFalse(driver.getPageSource().contains("<h2>Oops! Your page wasn't found</h2>"));
	    assertFalse(driver.getPageSource().contains("NullPointerException"));
	    
	} 
	
	public void testPatientViewReferralsWithDetails() throws Exception {
		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
	    ((HtmlUnitDriver)driver).setJavascriptEnabled(true);
	    driver.get(ADDRESS);
		logIn("2", "pw");
		//Check login succeeded and logged
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		//Click on Sent Referrals under Office Visits
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Referrals")).click();
	    assertEquals("Gandalf Stormcrow", driver.findElement(By.xpath("//table[@id='patientReferralsTable']/tbody/tr[4]/td[2]")).getText());
	    driver.findElements(By.linkText("View")).get(0).click();
	    assertEquals("iTrust - View Referrals", driver.getTitle());
	    assertTrue(driver.findElement(By.xpath("//table[@id='patientViewingReferral']/tbody/tr[3]/td[2]")).getText().contains("Kelly Doctor"));
	    assertTrue(driver.findElement(By.xpath("//table[@id='patientViewingReferral']/tbody/tr[3]/td[3]")).getText().contains("Gandalf"));
	    assertTrue(driver.findElement(By.xpath("//table[@id='patientViewingReferral']/tbody/tr[3]/td[5]")).getText().contains("Gandalf will make sure that the virus"));
	}
	
	/**
	 * testPatientSendsMessageToReceivingHCP
	 * @throws Exception
	 */
	public void testPatientSendsMessageToReceivingHCP() throws Exception {
		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
	    ((HtmlUnitDriver)driver).setJavascriptEnabled(true);
	    driver.get(ADDRESS);
		logIn("2", "pw");
		//Check login succeeded and logged
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		//Click on Sent Referrals under Office Visits
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("My Referrals")).click();
		assertTrue(driver.findElement(By.xpath("//table[@id='patientReferralsTable']/tbody/tr[4]/td[2]")).getText().contains("Gandalf Stormcrow"));
	    assertTrue(driver.findElement(By.xpath("//table[@id='patientReferralsTable']/tbody/tr[4]/td[3]")).getText().contains("07/15/2007 00:00 AM"));
	    driver.findElement(By.xpath("(//a[contains(text(),'View')])[4]")).click();
	    assertEquals("iTrust - View Referrals", driver.getTitle());
	    assertTrue(driver.findElement(By.xpath("//table[@id='patientViewingReferral']/tbody/tr[3]/td[4]")).getText().contains("06/10/2007"));
	    assertTrue(driver.findElement(By.xpath("//table[@id='patientViewingReferral']/tbody/tr[3]/td[5]")).getText().contains("Gandalf will make sure that the virus does not get past your immune system"));
	    driver.findElements(By.linkText("Email Gandalf Stormcrow")).get(0).click();
	    assertEquals("iTrust - View Referrals", driver.getTitle());
	    assertTrue(driver.getPageSource().contains("To Gandalf Stormcrow"));
	    driver.findElement(By.name("messageBody")).sendKeys("I want an appointment!");
	    driver.findElement(By.cssSelector("#sendMessageForm2 > input[type=\"submit\"]")).click();
	    assertTrue(driver.getPageSource().contains("Your message has successfully been sent!"));
	}
	
	/**
	 * testHCPViewsReferralsList
	 * @throws Exception
	 */
	public void testHCPViewsReferralsList() throws Exception {
		logIn("9000000003", "pw");
		//Check login succeeded and logged
		assertEquals("iTrust - HCP Home", driver.getTitle());
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Received Referrals")).click();
		assertEquals("iTrust - View Received Referrals", driver.getTitle());
		assertTrue(driver.findElement(By.xpath("//table[@id='receivedReferralsTable']/tbody/tr[3]/td[1]")).getText().contains("1"));
		assertTrue(driver.findElement(By.xpath("//table[@id='receivedReferralsTable']/tbody/tr[4]/td[1]")).getText().contains("1"));
		assertTrue(driver.findElement(By.xpath("//table[@id='receivedReferralsTable']/tbody/tr[5]/td[1]")).getText().contains("1"));
		assertTrue(driver.findElement(By.xpath("//table[@id='receivedReferralsTable']/tbody/tr[6]/td[1]")).getText().contains("1"));
		assertTrue(driver.findElement(By.xpath("//table[@id='receivedReferralsTable']/tbody/tr[7]/td[1]")).getText().contains("2"));
		assertTrue(driver.findElement(By.xpath("//table[@id='receivedReferralsTable']/tbody/tr[8]/td[1]")).getText().contains("2"));
		assertTrue(driver.findElement(By.xpath("//table[@id='receivedReferralsTable']/tbody/tr[9]/td[1]")).getText().contains("3"));
	}
	
	/**
	 * testHCPViewOVFromReferral
	 * @throws Exception
	 */
	public void testHCPViewOVFromReferral() throws Exception{
		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
	    ((HtmlUnitDriver)driver).setJavascriptEnabled(true);
	    driver.get(ADDRESS);
		logIn("9000000003", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Received Referrals")).click();
	    assertEquals("iTrust - View Received Referrals", driver.getTitle());
	    driver.findElements(By.linkText("View")).get(1).click();
	    driver.findElements(By.linkText("06/10/2007")).get(0).click();
	    assertEquals("iTrust - Document Office Visit", driver.getTitle());
	    assertTrue(driver.getPageSource().contains("Viewing information for"));
	}
}
