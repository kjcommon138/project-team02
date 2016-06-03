package edu.ncsu.csc.itrust.seleniumtests;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class SurveyUseCaseTest extends iTrustSeleniumTest {
	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		gen.hcp0();
		gen.patient2();		
	}
	
	/*
	 * Precondition:
	 * Patient 2 and HCP 9000000000 are in the system. 
	 * Patient 2 had an office visit with HCP 9000000000 on 6/10/2007. 
	 * Patient 2 has successfully authenticated.
	 * Description:
	 * Patient 2 chooses to view his records.
	 * Patient 2 clicks a link next to his office visit on 6/10/2007 to take satisfaction survey.
	 * He inputs the following information and submits:
	 * 15 minutes 
	 * 10 minutes 
	 * 3 
	 * 5
	 * Expected Results:
	 * The survey answers are stored and the event is logged.
	 */
	public void testTakeSatisfactionSurveySuccess() throws Exception {		
	    logIn("2", "pw");
	    assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();

		driver.findElement(By.linkText("View My Records")).click();
	    assertEquals("Patient Information", driver.findElement(By.cssSelector("th")).getText());
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
		
	    driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[1]")).click();
	    assertEquals("iTrust Patient Survey for Office Visit on Jun 10, 2007", driver.findElement(By.cssSelector("h1")).getText());
	    driver.findElement(By.name("waitingMinutesString")).clear();
	    driver.findElement(By.name("waitingMinutesString")).sendKeys("15");
	    driver.findElement(By.name("examMinutesString")).clear();
	    driver.findElement(By.name("examMinutesString")).sendKeys("10");
	    List<WebElement> satRadios = driver.findElements(By.name("Satradios"));
	    satRadios.get(2).click();
	    List<WebElement> treRadios = driver.findElements(By.name("Treradios"));
	    treRadios.get(0).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("Survey Successfully Submitted", driver.findElement(By.cssSelector("div.iTrustMessage")).getText());
		assertLogged(TransactionType.SATISFACTION_SURVEY_TAKE, 2L, 2L, "");
		
		assertEquals(" ", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr/td/div[2]/table/tbody/tr[3]/td[2]")).getText());
	}
	
	/*
	 * Precondition:
	 * Patient 2 and HCP 9000000000 are in the system. 
	 * Patient 2 had an office visit with HCP 9000000000 on 6/10/2007. 
	 * Patient 2 has successfully authenticated.
	 * Description:
	 * Patient 2 chooses to view his records.
	 * Patient 2 clicks a link next to his office visit on 6/10/2007 to take satisfaction survey.
	 * He inputs the following information and submits:
	 * [none] 
	 * 10 minutes 
	 * 3 
	 * [none]
	 * Expected Results:
	 * The survey answers are stored and the event is logged.
	 */
	public void testTakeSatisfactionSurveySuccess2() throws Exception{
	    logIn("2", "pw");
	    assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
	    assertEquals("Patient Information", driver.findElement(By.cssSelector("th")).getText());
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
		
	    driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[1]")).click();
	    assertEquals("iTrust Patient Survey for Office Visit on Jun 10, 2007", driver.findElement(By.cssSelector("h1")).getText());
	    driver.findElement(By.name("examMinutesString")).clear();
	    driver.findElement(By.name("examMinutesString")).sendKeys("10");
	    List<WebElement> satRadios = driver.findElements(By.name("Satradios"));
	    satRadios.get(2).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("Survey Successfully Submitted", driver.findElement(By.cssSelector("div.iTrustMessage")).getText());
		assertLogged(TransactionType.SATISFACTION_SURVEY_TAKE, 2L, 2L, "");

		// make sure survey cannot be taken again
		assertEquals(" ", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr/td/div[2]/table/tbody/tr[3]/td[2]")).getText());
	}
	
	/*
	 * Precondition:
	 * Patient 2 and HCP 9000000000 are in the system. 
	 * Patient 2 had an office visit with HCP 9000000000 on 6/10/2007. 
	 * Patient 2 has successfully authenticated.
	 * Description:
	 * Patient 2 chooses to view his records.
	 * Patient 2 clicks a link next to his office visit on 6/10/2007 to take satisfaction survey.
	 * Patient 2 changes his mind and decides to cancel his input.
	 */
	public void testTakeSatisfactionSurveyCancel() throws Exception{
		logIn("2", "pw");
	    assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
	    assertEquals("Patient Information", driver.findElement(By.cssSelector("th")).getText());
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
		
	    driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[1]")).click();
	    assertEquals("iTrust Patient Survey for Office Visit on Jun 10, 2007", driver.findElement(By.cssSelector("h1")).getText());
	    driver.findElement(By.name("examMinutesString")).clear();
	    driver.findElement(By.name("examMinutesString")).sendKeys("10");
	    List<WebElement> satRadios = driver.findElements(By.name("Satradios"));
	    satRadios.get(2).click();
	    
		// patient changes his mind and cancels his input
	    driver.findElement(By.xpath("(//a[contains(@href, '/iTrust/')])[2]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
		// make sure survey CAN still be taken.  This will throw an exception if the survey is not available
	    assertEquals("Complete Visit Survey", driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[1]")).getText());
		assertNotLogged(TransactionType.SATISFACTION_SURVEY_TAKE, 2L, 2L, "");
	}
}
