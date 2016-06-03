package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class HealthDataChartTest extends iTrustSeleniumTest {
	private HtmlUnitDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.healthData();
		driver = new HtmlUnitDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void testGetWeightLineChart() throws Exception {
		driver = (HtmlUnitDriver) login("9000000000", "pw");
		driver.setJavascriptEnabled(true);
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Basic Health Information")).click();
		driver.findElement(By.xpath("//*[@id=\"searchBox\"]")).sendKeys("2");
		driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input")).click();
		assertLogged(TransactionType.HCP_VIEW_BASIC_HEALTH_METRICS, 9000000000L, 2L, "");
		
		driver.findElement(By.id("viewWeightChart")).click();
		assertEquals("iTrust - Weight Chart", driver.getTitle());
		
		WebElement element = driver.findElement(By
				.xpath("//*[@id=\"iTrustContent\"]/img"));
		assertTrue(element.getAttribute("src").contains("/iTrust/charts/"));
		assertLogged(TransactionType.BASIC_HEALTH_CHARTS_VIEW, 9000000000L, 2L, "Weight");
	}

	public void testCalculateBMI() throws Exception {
		driver = (HtmlUnitDriver) login("9000000000", "pw");
		driver.setJavascriptEnabled(true);
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Basic Health Information")).click();
		driver.findElement(By.xpath("//*[@id=\"searchBox\"]")).sendKeys("2");
		driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input")).click();
		assertLogged(TransactionType.HCP_VIEW_BASIC_HEALTH_METRICS, 9000000000L, 2L, "");
		
		WebElement element = driver.findElement(By.id("HealthRecordsTable"));
		assertEquals(
				"Andy Programmer's Basic Adult Health History",
				element.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[1]/th")).getText());
		assertEquals(
				"2007-08-12 08:34:58.0",
				element.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[9]/td[13]")).getText());
		assertEquals(
				"37.34",
				element.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[9]/td[4]/a")).getText());
		assertEquals(
				"2007-10-30 10:54:22.0",
				element.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[8]/td[13]")).getText());
		assertEquals(
				"38.24",
				element.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[8]/td[4]/a")).getText());
	}
	
	public void testGetHeightLineChart() throws Exception {
		driver = (HtmlUnitDriver) login("9000000000", "pw");
		driver.setJavascriptEnabled(true);
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Basic Health Information")).click();
		driver.findElement(By.xpath("//*[@id=\"searchBox\"]")).sendKeys("2");
		driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input")).click();
		assertLogged(TransactionType.HCP_VIEW_BASIC_HEALTH_METRICS, 9000000000L, 2L, "");
		
		driver.findElement(By.id("viewHeightChart")).click();
		assertEquals("iTrust - Height Chart", driver.getTitle());

		// get the image element
		WebElement element = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/img"));
		assertTrue(element.getAttribute("src").contains("/iTrust/charts/"));
		assertLogged(TransactionType.BASIC_HEALTH_CHARTS_VIEW, 9000000000L, 2L, "Height");
	}
	
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
