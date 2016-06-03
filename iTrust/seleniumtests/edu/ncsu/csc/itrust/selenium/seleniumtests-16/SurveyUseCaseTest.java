package edu.ncsu.csc.itrust.seleniumtests;

import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class SurveyUseCaseTest extends iTrustSeleniumTest{
	
	private HtmlUnitDriver driver;
	private String baseUrl;

	@Before
	public void setUp() throws Exception {
	  super.setUp();
	  gen.clearAllTables();
	  gen.standardData();	
	  driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
	  baseUrl = "http://localhost:8080/iTrust/";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  driver.setJavascriptEnabled(true);
	}

	  @Test
	  public void testTakeSatisfactionSurveySuccess() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
	    assertEquals("Patient Information", driver.findElement(By.cssSelector("th")).getText());
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
	    driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[2]")).click();
	    assertEquals("iTrust Patient Survey for Office Visit on Jun 10, 2007", driver.findElement(By.cssSelector("h1")).getText());
	    driver.findElement(By.name("waitingMinutesString")).clear();
	    driver.findElement(By.name("waitingMinutesString")).sendKeys("15");
	    driver.findElement(By.name("examMinutesString")).clear();
	    driver.findElement(By.name("examMinutesString")).sendKeys("10");
	    driver.findElement(By.name("Treradios")).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("Survey Successfully Submitted", driver.findElement(By.cssSelector("div.iTrustMessage")).getText());
		assertLogged(TransactionType.SATISFACTION_SURVEY_TAKE, 2L, 2L, "");
	    assertEquals("", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr/td/div[2]/table/tbody/tr[3]/td[2]")).getText());
	  }
	
	  @Test
	  public void testTakeSatisfactionSurveySuccess2() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
	    assertEquals("Patient Information", driver.findElement(By.cssSelector("th")).getText());
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
	    driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[2]")).click();
	    driver.findElement(By.name("examMinutesString")).clear();
	    driver.findElement(By.name("examMinutesString")).sendKeys("10");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("Survey Successfully Submitted", driver.findElement(By.cssSelector("div.iTrustMessage")).getText());
		assertLogged(TransactionType.SATISFACTION_SURVEY_TAKE, 2L, 2L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
	    assertEquals("", driver.findElement(By.xpath("//div[@id='iTrustContent']/table/tbody/tr/td/div[2]/table/tbody/tr[3]/td[2]")).getText());
	  }
	  
	  @Test
	  public void testTakeSatisfactionSurveyCancel() throws Exception {
	    driver.get(baseUrl);
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
	    assertEquals("Patient Information", driver.findElement(By.cssSelector("th")).getText());
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
	    driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[2]")).click();
	    driver.findElement(By.name("examMinutesString")).clear();
	    driver.findElement(By.name("examMinutesString")).sendKeys("10");
	    driver.findElement(By.cssSelector("a.navbar-brand")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
	    driver.findElement(By.linkText("View My Records")).click();
	    assertEquals("Patient Information", driver.findElement(By.cssSelector("th")).getText());
	    assertEquals("Complete Visit Survey", driver.findElement(By.xpath("(//a[contains(text(),'Complete Visit Survey')])[2]")).getText());
		assertNotLogged(TransactionType.SATISFACTION_SURVEY_TAKE, 2L, 2L, "");

	  }

}
