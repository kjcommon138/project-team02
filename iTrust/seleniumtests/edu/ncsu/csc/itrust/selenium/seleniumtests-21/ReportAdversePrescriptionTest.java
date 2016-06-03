package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ReportAdversePrescriptionTest extends iTrustSeleniumTest {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.hcp0();
		gen.ovMed();
		gen.patient2();
		gen.patient1();
		
	}
	
	/**
	 * testReport
	 * @throws Exception
	 */
	public void testReport() throws Exception {
		logIn("2", "pw");
	    assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		//navigate to the Prescription Records link and click
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Prescription Records")).click();
		assertEquals("iTrust - Get My Prescription Report", driver.getTitle());
		
		//Click View Current
	    driver.findElement(By.name("mine")).click();
		assertLogged(TransactionType.PRESCRIPTION_REPORT_VIEW, 2L, 2L, "");
		
		//Enter Y into the box
	    driver.findElement(By.name("checking0")).clear();
	    driver.findElement(By.name("checking0")).sendKeys("Y");
	    //Click Report Adverse Events
	    driver.findElement(By.name("adevent")).click();
	    
	    //Enter the comment for the report
	    driver.findElement(By.name("Comment")).clear();
	    driver.findElement(By.name("Comment")).sendKeys("My joints hurt and my muscles ache. I've been having severe nausea after taking this medication.");
	    //Submit
	    driver.findElement(By.name("addReport")).click();
	    
	    //Ensure we're back on the home page, the success message is displayed, and the information was logged.
		assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertEquals("Adverse Event Successfully Reported", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		assertLogged(TransactionType.ADVERSE_EVENT_REPORT, 2L, 0, "");
	}
	
	/**
	 * testReportAdverseEventsButton
	 * @throws Exception
	 */
	public void testReportAdverseEventsButton() throws Exception{
		logIn("1", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0, "");
			
		//navigate to the Prescription Records link and click
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Prescription Records")).click();
		assertEquals("iTrust - Get My Prescription Report", driver.getTitle());
		
		//Click View Current
	    driver.findElement(By.name("mine")).click();
	    //Sleep to ensure that there is enough time between the link click and the code that checks the database for the log message.
	    Thread.sleep(500);
		assertLogged(TransactionType.PRESCRIPTION_REPORT_VIEW, 1L, 1L, "");
		
		//Assure that the page displays No prescriptions found
	    assertEquals("No prescriptions found", driver.findElement(By.cssSelector("table.fTable > tbody > tr > td > i")).getText());
	}
}
