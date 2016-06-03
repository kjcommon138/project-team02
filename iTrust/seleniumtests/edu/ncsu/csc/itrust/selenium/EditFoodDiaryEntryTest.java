package edu.ncsu.csc.itrust.selenium;

import org.junit.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;


public class EditFoodDiaryEntryTest extends iTrustSeleniumTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		driver = new HtmlUnitDriver();
	}

	/**
	 * Acceptance test for HW3P3
	 * Login as Emily Prentiss (70, pw)
	 */

	public void testEditFoodDiaryWithValidValues() throws Exception {

		driver.get("http://localhost:8080/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("70");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.linkText("Edit")).click();
		driver.findElement(By.name("numServings")).clear();
		driver.findElement(By.name("numServings")).sendKeys("3");
		driver.findElement(By.name("numCalories")).clear();
		driver.findElement(By.name("numCalories")).sendKeys("1327");
		driver.findElement(By.name("gramsFat")).clear();
		driver.findElement(By.name("gramsFat")).sendKeys("62.5");
		driver.findElement(By.name("mgSodium")).clear();
		driver.findElement(By.name("mgSodium")).sendKeys("687");
		driver.findElement(By.name("gramsCarbs")).clear();
		driver.findElement(By.name("gramsCarbs")).sendKeys("176.4");
		driver.findElement(By.name("gramsSugar")).clear();
		driver.findElement(By.name("gramsSugar")).sendKeys("0");
		driver.findElement(By.name("gramsFiber")).clear();
		driver.findElement(By.name("gramsFiber")).sendKeys("112.4");
		driver.findElement(By.name("gramsProtein")).clear();
		driver.findElement(By.name("gramsProtein")).sendKeys("15.6");
		driver.findElement(By.name("action")).click();
		assertTrue(driver.getPageSource().contains("Food Diary Entry Successfully Edited"));


	}
	/**
	 * Acceptance test for HW3P3 UC70
	 * Login as Aaron  Hotchner (92, pw)
	 */

	public void testEditFoodDiaryWithInvalidValues() throws Exception {
		driver.get("http://localhost:8080/iTrust/auth/forwardUser.jsp");

		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("92");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.linkText("Edit")).click();
		driver.findElement(By.name("numServings")).clear();
		driver.findElement(By.name("numServings")).sendKeys("-17");
		driver.findElement(By.name("action")).click();

		assertTrue(driver.getPageSource().contains("This form has not been validated correctly. "
				+ "The following field are not properly filled in: "
				+ "[Number of Servings Consumed: Must be a positive double greater than 0.]"));


	}
	/**
	 * Acceptance test for HW3P3 UC70
	 * Login as Jennifer Jaraeu (90, pw)
	 */
	public void testDeleteFoodDiaryEntry() throws Exception {
		driver.get("http://localhost:8080/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("90");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.linkText("Delete")).click();

		assertTrue(driver.getPageSource().contains("Food Diary Entry Successfully Deleted"));
	}

	/**
	 * Login as Jennifer Jaraeu (90,pw)
	 * @throws Exception
	 */
	public void testFutureDateInEditFoodDiaryEntry() throws Exception {
		driver.get("http://localhost:8080/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("90");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.linkText("Edit")).click();
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("09/30/2055");
		driver.findElement(By.name("action")).click();

		assertTrue(driver.getPageSource().contains("Date can not be from the future!"));
	}
	/**
	 * Login as Jennifer Jaraeu (90,pw)
	 * @throws Exception
	 */
	public void testInvalidFoodNameInEditFoodDiary() throws Exception {
		driver.get("http://localhost:8080/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("90");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
		driver.findElement(By.linkText("My Food Diary")).click();
		driver.findElement(By.linkText("Edit")).click();
		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("02/04/2015");
		new Select(driver.findElement(By.name("typeOfMeal"))).selectByVisibleText("Dinner");
		driver.findElement(By.name("mealName")).clear();
		driver.findElement(By.name("mealName")).sendKeys("");
		driver.findElement(By.name("action")).click();

		assertTrue(driver.getPageSource().contains("Invalid entry: This form has not been validated correctly. The following field are not properly filled in: [Food Item Name]"));
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
