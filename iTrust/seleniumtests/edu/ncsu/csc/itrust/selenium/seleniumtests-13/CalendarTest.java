package edu.ncsu.csc.itrust.selenium;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class CalendarTest extends iTrustSeleniumTest {

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
	public void testHCPViewAppointmentCalendar() throws Exception {

		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Appointment Calendar")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"Appointment Calendar"));

		if (driver.getPageSource().contains("5")) {
			Assert.assertTrue(driver.getPageSource().contains("Colonoscopy"));
		}

		if (driver.getPageSource().contains("18")) {
			Assert.assertTrue(driver.getPageSource().contains("Colonoscopy"));
		}

		if (driver.getPageSource().contains("28")) {
			Assert.assertTrue(driver.getPageSource().contains("Physical"));
		}
	}

	@Test
	public void testPatientViewFullCalendarOfficeVisitDetails()
			throws Exception {
		driver.findElement(By.id("j_username")).sendKeys("2");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Full Calendar")).click();
		driver.findElement(By.linkText("Read Details")).click();
		Assert.assertTrue(driver.getPageSource().contains("Andy Programmer"));
		Assert.assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		Assert.assertTrue(driver.getPageSource().contains("General Checkup"));
		Assert.assertTrue(driver.getPageSource().contains("45 minutes"));
		Assert.assertTrue(driver.getPageSource().contains("No Comment"));
	}

	public void testPatientViewFullCalendarPrescriptionDetails()
			throws Exception {
		driver.findElement(By.id("j_username")).sendKeys("2");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Full Calendar")).click();
		driver.findElement(By.linkText("Read Details")).click();
		Assert.assertTrue(driver.getPageSource().contains("Andy Programmer"));
		Assert.assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		Assert.assertTrue(driver.getPageSource().contains("General Checkup"));
		Assert.assertTrue(driver.getPageSource().contains("45 minutes"));
		Assert.assertTrue(driver.getPageSource().contains("No Comment"));
	}

	public void testHCPViewAppointmentCalendarDetails() throws Exception {
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Appointment Calendar")).click();
		driver.findElement(By.cssSelector("a[name=\"General Checkup-5\"]"))
		.click();
		Assert.assertTrue(driver.getPageSource().contains("Random Person"));
		Assert.assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		Assert.assertTrue(driver.getPageSource().contains("General Checkup"));
		Assert.assertTrue(driver.getPageSource().contains("45 minutes"));
		Assert.assertTrue(driver.getPageSource().contains("No Comment"));
	}

}
