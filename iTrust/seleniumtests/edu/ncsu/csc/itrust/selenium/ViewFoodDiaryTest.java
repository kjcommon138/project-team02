package edu.ncsu.csc.itrust.selenium;


import org.junit.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.*;


public class ViewFoodDiaryTest extends iTrustSeleniumTest {
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
	 * Test to ensure that the diary entries are properly filtered
	 * Login as Aaron
	 * Set dates and make sure some of the entries are filtered out.
	 * @throws Exception
	 */
	public void testFilterDate() throws Exception {
		driver.get("http://localhost:8080/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("92");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();

		assertTrue(driver.getPageSource().contains("Oreos"));

		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("05/19/2013");
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys("06/01/2013");
		driver.findElement(By.name("submit")).click();
		assertEquals(driver.getTitle(), "iTrust - View My Food Diary");
		assertFalse(driver.getPageSource().contains("Oreos"));


	}

	/**
	 * Determine if a patient can view his or her own fooddiary
	 * Login as Jennifer Jareau (90, pw)
	 * Determine if she can view her own fooddiary
	 * @throws Exception
	 */
	public void testPatientViewsPersonalFoodDiary() throws Exception {
		driver.get("http://localhost:8080/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("92");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("My Food Diary")).click();

		assertEquals(driver.getCurrentUrl(), "http://localhost:8080/iTrust/auth/patient/viewMyFoodDiary.jsp");
	}

	/**
	 * Acceptance test for HW3P2
	 * Login as HCP Spencer Reid  (9000000011, pw)
	 * Determine if nutritionist can view patient fooddiary
	 */

	public void testNutritionistViewsPatientFoodDiary() throws Exception {
		driver.get("http://localhost:8080/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000006900");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Patient Food Diary")).click();
		driver.get("http://localhost:8080/iTrust/auth/getPatientID.jsp?UID_PATIENTID=92&forward=hcp/viewPatientFoodDiary.jsp");


		assertEquals(driver.getCurrentUrl(), "http://localhost:8080/iTrust/auth/hcp/viewPatientFoodDiary.jsp");

	}
	/**
	 * Verify that a non-nutritionist CANNOT access patient fooddiary
	 * Login as Kelly Doctor (9000000000, pw)
	 * Ensure she is not directed to viewPatientFoodDiary.jsp page
	 * @throws Exception
	 */
	public void testNonNutritionistViewsPatientFoodDiary() throws Exception {
		driver.get("http://localhost:8080/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Patient Food Diary")).click();


		assertTrue(driver.getPageSource().contains("ITrustException: Invalid credentials. You must be a Nutritionist to view"));
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
		gen.clearAllTables();
	}
}
