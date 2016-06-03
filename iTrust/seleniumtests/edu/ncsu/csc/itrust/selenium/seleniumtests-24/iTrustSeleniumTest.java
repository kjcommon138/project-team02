package edu.ncsu.csc.itrust.seleniumtests;


import java.util.List;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
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
	/*
	 * The URL for iTrust, change as needed
	 */
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	/**gen*/
	protected TestDataGenerator gen = new TestDataGenerator();

	private WebDriver driver;


	@Override
	protected void setUp() throws Exception {
		// Create a new instance of the html unit driver
		driver = new HtmlUnitDriver();

		//Navigate to desired web page
		driver.get(ADDRESS);
		gen.clearAllTables();
		
		
		
	}

	/**
	 * Helper method for logging in to iTrust
	 * @param username username
	 * @param password password
	 * @return WebDriver
	 * @throws Exception
	 */
	public WebDriver login(String username, String password) throws Exception {
		//Thanks to http://stackoverflow.com/questions/3600557/turning-htmlunit-warnings-off
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF); 
		String expectedTitle = "iTrust - Login";
		//get the title of the page
		String actualTitle = driver.getTitle();

		// verify title
		assertEquals(actualTitle, expectedTitle);
		
		driver.findElement(By.id("j_username")).sendKeys(username);
		driver.findElement(By.id("j_password")).sendKeys(password);
		
		driver.findElement(By.name("j_login")).click();

		actualTitle = driver.getTitle();
		if (driver.getTitle().equals("iTrust Login")) {
			throw new IllegalArgumentException("Error logging in, user not in database?");
		}
			assertLogged(TransactionType.LOGIN_SUCCESS, Long.parseLong(username), Long.parseLong(username), "");
		//return driver;
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
	
	
	
	
}
