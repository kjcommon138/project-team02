package edu.ncsu.csc.itrust.selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

abstract public class iTrustSeleniumTest extends TestCase {
	/** ADDRESS */
	public static final String ADDRESS = "http://localhost:8080/iTrust/auth/forwardUser.jsp";
	/** gen */
	protected TestDataGenerator gen = new TestDataGenerator();

	/**
	 * Helper method for logging in to iTrust
	 * @param username username
	 * @param password password
	 * @return HtmlUnitDriver
	 * @throws Exception
	 */
	public HtmlUnitDriver login(String username, String password)
			throws Exception {
		// start the HtmlUnitDriver
		HtmlUnitDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(ADDRESS);
		// log in using the given username and password
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys(username);
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys(password);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		if (driver.getTitle().equals("iTrust - Login")) {
			throw new IllegalArgumentException(
					"Error logging in, user not in database?");
		}
		return driver;
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
}
