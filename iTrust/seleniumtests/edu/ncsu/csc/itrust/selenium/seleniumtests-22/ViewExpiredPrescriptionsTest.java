package edu.ncsu.csc.itrust.selenium;

import java.util.List;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class ViewExpiredPrescriptionsTest extends iTrustSeleniumTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.icd9cmCodes();
		gen.ndCodes();
		gen.uap1();
		gen.patient2();
		gen.patient1();
		gen.patient4();
		gen.patient9();
		gen.hcp0();
		gen.hcp1();
		gen.hcp2();
		gen.clearLoginFailures();
		gen.hcp3();
	}

	@Test
	public void testViewExpired1() throws Exception {
		driver = super.login("2", "pw");
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.xpath("//li[8]/a")).click();
		assertNotNull(driver.findElement(By.linkText("00904-2407")));
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + "9000000000" + "')]"));
		assertTrue(list.size() == 0);
		driver.findElement(By.linkText("Kelly Doctor")).click();
		String text = driver.findElement(By.cssSelector("table.fTable")).getText();
	    assertTrue("Text not found!", text.contains("surgeon"));
	    assertTrue("Text not found!", text.contains("4321 My Road St"));
	    assertTrue("Text not found!", text.contains("New York"));
	    assertTrue("Text not found!", text.contains("NY"));
	    assertTrue("Text not found!", text.contains("10453"));
	    assertTrue("Text not found!", text.contains("999-888-7777"));
	    assertTrue("Text not found!", text.contains("kdoctor@iTrust.org"));
	    assertTrue("Text not found!", !text.contains("9000000000"));
	    
	}

	@Test
	public void testViewExpired2() throws Exception {
		driver = super.login("99", "pw");
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.xpath("//li[8]/a")).click();
		assertNotNull(driver.findElement(By.linkText("00904-2407")));
		assertNotNull(driver.findElement(By.linkText("08109-6")));
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + "9000000000" + "')]"));
		assertTrue(list.size() == 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'" + "9900000000" + "')]"));
		assertTrue(list.size() == 0);
		driver.findElement(By.linkText("Tester Arehart")).click();
		String text = driver.findElement(By.cssSelector("table.fTable")).getText();
	    assertTrue("Text not found!", text.contains("Neurologist"));
	    assertTrue("Text not found!", text.contains("2110 Thanem Circle"));
	    assertTrue("Text not found!", text.contains("Raleigh"));
	    assertTrue("Text not found!", text.contains("NC"));
	    assertTrue("Text not found!", text.contains("999-888-7777"));
	    assertTrue("Text not found!", text.contains("tarehart@iTrust.org"));
	    assertTrue("Text not found!", !text.contains("9900000000"));
	}
	
	@Test
	public void testViewExpired3() throws Exception {
		driver = super.login("99", "pw");
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.xpath("//li[8]/a")).click();
		assertNotNull(driver.findElement(By.linkText("00904-2407")));
		assertNotNull(driver.findElement(By.linkText("08109-6")));
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + "9000000000" + "')]"));
		assertTrue(list.size() == 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'" + "9900000000" + "')]"));
		assertTrue(list.size() == 0);
		driver.findElement(By.linkText("Kelly Doctor")).click();
		String text = driver.findElement(By.cssSelector("table.fTable")).getText();
	    assertTrue("Text not found!", text.contains("surgeon"));
	    assertTrue("Text not found!", text.contains("4321 My Road St"));
	    assertTrue("Text not found!", text.contains("New York"));
	    assertTrue("Text not found!", text.contains("NY"));
	    assertTrue("Text not found!", text.contains("999-888-7777"));
	    assertTrue("Text not found!", text.contains("kdoctor@iTrust.org"));
	    assertTrue("Text not found!", !text.contains("9000000000"));
	}
	
	@Test
	public void testViewExpired4() throws Exception {
		driver = super.login("99", "pw");
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.xpath("//li[8]/a")).click();
		assertNotNull(driver.findElement(By.linkText("00904-2407")));
		assertNotNull(driver.findElement(By.linkText("08109-6")));
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'" + "9000000000" + "')]"));
		assertTrue(list.size() == 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'" + "9900000000" + "')]"));
		assertTrue(list.size() == 0);
		driver.findElement(By.linkText("Jimmy Incomplete")).click();
		String text = driver.findElement(By.cssSelector("table.fTable")).getText();
	    assertTrue("Text not found!", !text.contains("null"));
	    assertTrue("Text not found!", !text.contains("AK"));
	    assertTrue("Text not found!", !text.contains("9990000000"));
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
