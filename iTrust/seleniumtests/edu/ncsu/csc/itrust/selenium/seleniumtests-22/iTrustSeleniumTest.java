package edu.ncsu.csc.itrust.selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

abstract public class iTrustSeleniumTest extends TestCase {
	
	/*
	 * The URL for iTrust, change as needed
	 */
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	/**gen*/
	protected TestDataGenerator gen = new TestDataGenerator();
	
	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * Logs in to iTrust, call this at the beginning of every test method, and then just check the page title
	 * @param username of patient/HCP
	 * @param password of patient/HCP
	 * @return driver to use for the rest of the Selenium test
	 * @throws Exception if things go horribly wrong
	 */
	public HtmlUnitDriver login(String username, String password) throws Exception {
		//We make the driver here in the super class so we don't have to make it in every single tests
		//Good if we need to change what driver we're using!
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(ADDRESS);
		//login to iTrust
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys(username);
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys(password);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		if (driver.getTitle().equals("iTrust - Login")) {
			throw new IllegalArgumentException("Login Failed");
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

	/**
	 * assertNotLogged
	 * @param code code
	 * @param loggedInMID loggedInMID
	 * @param secondaryMID secondaryMID
	 * @param addedInfo addedInfo
	 * @throws DBException
	 */
	public static void assertNotLogged(TransactionType code, long loggedInMID, long secondaryMID, String addedInfo)
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
}

