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

public class CreditCardValidatorTest extends iTrustSeleniumTest {

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
	public void testGoodMasterCards() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		new Select(driver.findElement(By.name("creditCardType")))
				.selectByVisibleText("Mastercard");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"5593090746812380");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"5437693863890467");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"5343017708937494");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
	}

	@Test
	public void testBadMasterCards() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		new Select(driver.findElement(By.name("creditCardType")))
				.selectByVisibleText("Mastercard");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"1593090746812380");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"not properly filled in: [Credit Card Number]"));
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"4539592576502361");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"not properly filled in: [Credit Card Number]"));
	}

	@Test
	public void testGoodVisas() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		new Select(driver.findElement(By.name("creditCardType")))
				.selectByVisibleText("Visa");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"4539592576502361");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"4716912133362668");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"4485333709241203");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
	}

	@Test
	public void testBadVisas() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		new Select(driver.findElement(By.name("creditCardType")))
				.selectByVisibleText("Visa");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"5593090746812380");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"not properly filled in: [Credit Card Number]"));
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"6437693863890467");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"not properly filled in: [Credit Card Number]"));
	}

	@Test
	public void testGoodDiscovers() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		new Select(driver.findElement(By.name("creditCardType")))
				.selectByVisibleText("Discover");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"6011263089803439");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"6011953266156193");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
	}

	@Test
	public void testBadDiscovers() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		new Select(driver.findElement(By.name("creditCardType")))
				.selectByVisibleText("Discover");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"5593090746812380");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"not properly filled in: [Credit Card Number]"));
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"6437693863890467");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"not properly filled in: [Credit Card Number]"));
	}

	@Test
	public void testGoodAmex() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		new Select(driver.findElement(By.name("creditCardType")))
				.selectByVisibleText("American Express");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"343570480641495");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"377199947956764");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
	}

	@Test
	public void testBadAmex() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		new Select(driver.findElement(By.name("creditCardType")))
				.selectByVisibleText("American Express");
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"5593090746812380");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"not properly filled in: [Credit Card Number]"));
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"6437693863890467");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"not properly filled in: [Credit Card Number]"));
	}

	@Test
	public void testEmptyTypeEmptyNumber() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
		
	}

	@Test
	public void testEmptyTypeFilledNumber() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("My Demographics")).click();
		driver.findElement(By.name("creditCardNumber")).clear();
		driver.findElement(By.name("creditCardNumber")).sendKeys(
				"5593090746812380");
		driver.findElement(By.name("action")).click();
		Assert.assertTrue(driver.getPageSource().contains(
				"not properly filled in: [Credit Card Number]"));
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
