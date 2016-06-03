package edu.ncsu.csc.itrust.seleniumtests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

abstract public class iTrustSeleniumTest extends TestCase {

	/** ADDRESS */
	public static final String ADDRESS = "http://localhost:8080/iTrust/";

	/** gen */
	protected TestDataGenerator gen = new TestDataGenerator();

	protected WebDriver driver;
	// protected HtmlUnitDriver driver;
	
	protected boolean acceptNextAlert = true;

	protected StringBuffer verificationErrors = new StringBuffer();

	@Override
	protected void setUp() throws Exception {
		// driver = new FirefoxDriver();
		driver = new HtmlUnitDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		gen.clearAllTables();
		driver.get(ADDRESS);
	}

	/**
	 * assertLogged
	 * 
	 * @param code
	 *            code
	 * @param loggedInMID
	 *            loggedInMID
	 * @param secondaryMID
	 *            secondaryMID
	 * @param addedInfo
	 *            addedInfo
	 * @throws DBException
	 */
	public static void assertLogged(TransactionType code, long loggedInMID,
			long secondaryMID, String addedInfo) throws DBException {

		List<TransactionBean> transList = TestDAOFactory.getTestInstance()
				.getTransactionDAO().getAllTransactions();
		for (TransactionBean t : transList) {
			if ((t.getTransactionType() == code)
					&& (t.getLoggedInMID() == loggedInMID)
					&& (t.getSecondaryMID() == secondaryMID)) {
				assertTrue(t.getTransactionType() == code);
				if (!t.getAddedInfo().trim().contains(addedInfo.trim())) {
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
	
	public void logIn(String username, String password) {
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys(username);
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys(password);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
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
