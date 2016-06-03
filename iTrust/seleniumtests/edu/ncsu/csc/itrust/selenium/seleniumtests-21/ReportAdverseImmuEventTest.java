package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ReportAdverseImmuEventTest extends iTrustSeleniumTest {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.hcp0();
		gen.cptCodes();
		gen.ovImmune();
		gen.patient1();
	}
	
	public void testReport() throws Exception {
		//Login
		logIn("1", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");

		//Navigate to View My Records and click
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
		assertEquals("iTrust - View My Records", driver.getTitle());
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 1L, 1L, "");
		
	    //Click report
		driver.findElement(By.linkText("Report")).click();
		//Enter the report text
	    driver.findElement(By.name("Comment")).clear();
	    driver.findElement(By.name("Comment")).sendKeys("I've been experiencing extreme fatigue and severe nausea following this immunization.");
	    //Submit the report
	    driver.findElement(By.name("addReport")).click();
	    //Ensure we're on the home page, the success message is displayed, the event was logged
		assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertEquals("Adverse Event Successfully Reported", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		assertLogged(TransactionType.ADVERSE_EVENT_REPORT, 1L, 0L, "");
	}
}
