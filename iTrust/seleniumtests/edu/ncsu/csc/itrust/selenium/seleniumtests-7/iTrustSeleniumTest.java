package edu.ncsu.csc.itrust.seleniumtests;

import java.util.List;

import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import static org.junit.Assert.*;

abstract public class iTrustSeleniumTest {
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	protected static final int SEARCH_TIMEOUT = 2000;
	protected HtmlUnitDriver driver;
	protected TestDataGenerator gen;

	@Before
	public void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
		driver.get("http://localhost:8080/iTrust");
	}

	/**
	 * Asserts that the title of the web page is equal to the expectedTitle
	 *
	 * @param expectedTitle The expected value of the title field of the webpage
	 */
	public void assertTitleEquals(String expectedTitle) {
		//get the title of the page
		String actualTitle = driver.getTitle();

		// verify title
		assertEquals(expectedTitle, actualTitle);
	}

	/**
	 * Logs in using the provided username and password. Verifies that the
	 * login was successful using the web page title. The expected title
	 * depends on the type of user logging in so the expected user type
	 * is required to be passed in as a parameter.
	 *
	 * @param username The username of the user to be logged in.
	 * @param password The password of the user to be logged in.
	 * @param userType The user type of the user to be logged in. Exs. Patient, HCP, UAP, Admin
	 */
	public void login(String username, String password, String userType) {
		boolean hasJavascript = this.driver.isJavascriptEnabled();
		this.driver.setJavascriptEnabled(false); //Turn off javascript for login
		this.driver.findElement(By.id("j_username")).sendKeys(username);
		this.driver.findElement(By.id("j_password")).sendKeys(password);
		this.driver.findElement(By.xpath("//input[@type='submit']")).click();
		this.driver.setJavascriptEnabled(hasJavascript); //restore javascript
	}

	/**
	 * assertLogged
	 * @param code code
	 * @param loggedInMID loggedInMID
	 * @param secondaryMID secondaryMID
	 * @param addedInfo addedInfo
	 * @throws DBException
	 */
	public static void assertLogged(TransactionType code, long loggedInMID,
			long secondaryMID, String addedInfo)
			throws DBException {
		List<TransactionBean> transList = TestDAOFactory.getTestInstance().getTransactionDAO().getAllTransactions();
		for (TransactionBean t : transList)
		{
			if( (t.getTransactionType() == code) &&
				(t.getLoggedInMID() == loggedInMID) &&
				(t.getSecondaryMID() == secondaryMID))
				{
					assertTrue(t.getTransactionType() == code);
					if(!t.getAddedInfo().trim().contains(addedInfo.trim()))
					{
						fail("Additional Information is not logged correctly.");
					}
					return;
				}
		}
		fail("Event not logged as specified.");
	}

	/**
	 * assertNotLogged
	 * @param code code
	 * @param loggedInMID loggedInMID
	 * @param secondaryMID secondaryMID
	 * @param addedInfo addedInfo
	 * @throws DBException
	 */
	public static void assertNotLogged(TransactionType code, long loggedInMID,
			long secondaryMID, String addedInfo)
			throws DBException {
		List<TransactionBean> transList = TestDAOFactory.getTestInstance().getTransactionDAO().getAllTransactions();
		for (TransactionBean t : transList)
		{
			if( (t.getTransactionType() == code) &&
				(t.getLoggedInMID() == loggedInMID) &&
				(t.getSecondaryMID() == secondaryMID) &&
				(t.getAddedInfo().trim().contains(addedInfo)) )
				{
					fail("Event was logged, but should NOT have been logged");
					return;
				}
		}
	}

	/**
	 * Enters the given patient MID into the "Select a Patient" search box and clicks the button for that patient.
	 * This method will fail the calling test if the current page is not the search page or if a patient could not be found.
	 * @param patientId the MID of the patient to look for.
	 */
	public void selectPatientFromSearch(String patientId) throws Exception {
		if (!driver.isJavascriptEnabled()) {
			throw new IllegalArgumentException("Javascript is not enabled");
		}
		if (driver.getTitle().equals("iTrust - Please Select a Patient")) {
			WebElement searchBox = driver.findElement(By.id("searchBox"));
			searchBox.clear();
			new Actions(driver).click(searchBox).sendKeys(patientId).sendKeys(Keys.TAB).perform();

			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("searchResults")));

			List<WebElement> possibleButtons = driver.findElements(By.cssSelector("input[value='" + patientId + "'][type='button']"));
			if (possibleButtons.size() < 1) {
				fail("Unable to find patient with MID: " + patientId);
			} else {
				driver.findElements(By.cssSelector("input[value='" + patientId + "'][type='button']")).get(0).click();
			}
		} else {
			fail("Attempted to search for a patient but was not on the 'Select a Patient' page!");
		}
	}

	/**
	 * Handles the common task of asserting that some text is somewhere in the body of the HTML document.
	 * @param expectedText
	 */
	public void assertTextInBody(String expectedText) {
		assertTrue(driver.findElement(By.tagName("body")).getText().contains(expectedText));
	}

	/**
	 * Finds and clicks the specified navbar heading, expanding it and allowing links to be found and clicked.
	 * @param heading The heading to expand.
	 */
	public void expandNavHeading(NavigationHeading heading) {
		driver.findElementByXPath("//*[@anim-target = '" + heading.toString() + "']").click();
	}

	public String getTableRow(String name, int rowNum){
		WebElement table = driver.findElement(By.id(name));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement row = rows.get(rowNum);
		return row.getText();
	}

	public String getTableCell(String name, int rowNum, int colNum){
		WebElement table = driver.findElement(By.id(name));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement row = rows.get(rowNum);
		List<WebElement> cells = row.findElements(By.tagName("td"));

		return cells.get(colNum).getText();
	}

	public void clickTableButton(String name, int rowNum, String linkText){
		WebElement table = driver.findElement(By.id(name));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement row = rows.get(rowNum);
		row.findElement(By.partialLinkText(linkText)).click();
	}

	public int tableRows(String name){
		WebElement table = driver.findElement(By.id(name));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		return rows.size();
	}

	public int tableColumns(String name){
		WebElement table = driver.findElement(By.id(name));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		List<WebElement> cells = rows.get(1).findElements(By.tagName("td"));
		return cells.size();
	}

	public enum NavigationHeading {
		Info("#info-menu"),
		Appointment("#appt-menu"),
		OfficeVisits("#ov-menu"),
		Messaging("#msg-menu"),
		Telemedicine("#tele-menu"),
		Add("#add-menu"),
		PersonalInfo("#pi-menu"),
		Obstetrics("#ob-menu"),
		Nutrition("#nutrition-menu"),
		Other("#other-menu");

		private String identifier;

		private NavigationHeading(String identifier) {
			this.identifier = identifier;
		}

		@Override
		public String toString() {
			return this.identifier;
		}
	}
}
