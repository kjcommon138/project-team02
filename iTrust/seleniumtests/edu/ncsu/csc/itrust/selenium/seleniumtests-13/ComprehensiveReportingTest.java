package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class ComprehensiveReportingTest extends iTrustSeleniumTest {

	private WebDriver driver;

	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() {
		// Create a new instance of the html unit driver
		driver = new HtmlUnitDriver();

		// Navigate to desired web page
		driver.get("http://localhost:8080/iTrust/");
	}

	@Test
	public void testComprehensiveAcceptanceSuccess() throws Exception {
		String expectedTitle = "iTrust - HCP Home";

		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);

		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Report Requests")).click();
		Assert.assertTrue(driver.getPageSource().contains("Report Requests"));
		driver.findElement(By.linkText("Add a new Report Request")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();
		Assert.assertTrue(driver.getPageSource().contains(
				"Report Request Accepted"));

	}

	@Test
	public void testHCPChoosesInvalidPatient() throws Exception {
		String expectedTitle = "iTrust - HCP Home";

		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Report Requests")).click();
		Assert.assertTrue(driver.getPageSource().contains("Report Requests"));
		driver.findElement(By.linkText("Add a new Report Request")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("260");
		driver.findElement(By.xpath("//input[@value='260']")).submit();
	    //Assert.assertTrue(driver.getPageSource().contains("Found 0 Records"));
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
