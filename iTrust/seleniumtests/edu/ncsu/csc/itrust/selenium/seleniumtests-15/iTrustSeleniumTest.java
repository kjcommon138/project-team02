package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

	/**
	 * There's nothing special about this class other than adding a few handy test utility methods and
	 * variables. When extending this class, be sure to invoke super.setUp() first.
	 */
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
			HttpUnitOptions.clearScriptErrorMessages();
			HttpUnitOptions.setScriptingEnabled(true);
			HttpUnitOptions.setExceptionsThrownOnScriptError(false);
			gen.clearAllTables();
		}

		public HtmlUnitDriver login(String username, String password)
				throws Exception {
			// start the HtmlUnitDriver
			HtmlUnitDriver driver = new HtmlUnitDriver();
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
		
		public void selectComboValue(final String elementName, final String value, final WebDriver driver) {
		    final Select selectBox = new Select(driver.findElement(By.name(elementName)));
		    selectBox.selectByValue(value);
		}
}
