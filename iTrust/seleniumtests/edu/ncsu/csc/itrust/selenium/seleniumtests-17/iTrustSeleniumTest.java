package edu.ncsu.csc.itrust.seleniumtests;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.meterware.httpunit.HttpUnitOptions;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;
import junit.framework.TestResult;

public abstract class iTrustSeleniumTest extends TestCase {

	private WebDriver driver = null;

	/*
	 * The URL for iTrust, change as needed
	 */
	/** ADDRESS */
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	/** gen */
	protected TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		HttpUnitOptions.clearScriptErrorMessages();
		HttpUnitOptions.setScriptingEnabled(true);
		HttpUnitOptions.setExceptionsThrownOnScriptError(false);
		gen.clearAllTables();
	}

	/**
	 * Helper method for logging in to iTrust
	 * 
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @return
	 * @throws
	 */
	public WebDriver login(String username, String password) throws Exception {

		driver = new HtmlUnitDriver();
		// begin at the iTrust home page
		driver.get(ADDRESS);

		// Enter Username on the element found by above desc.
		driver.findElement(By.id("j_username")).sendKeys(username);

		// Enter Password on the element found by the above desc.

		driver.findElement(By.id("j_password")).sendKeys(password);
		driver.findElement(By.id("Login_Button")).submit();

		assertLogged(TransactionType.LOGIN_SUCCESS, Long.parseLong(username),
				Long.parseLong(username), "");

		return driver;
	}

	// Method to check and see if text is present on a webpage
	public boolean isTextPresent(String textToBeVerified){
		if (driver.getPageSource().contains(textToBeVerified)){
			return true;
		}
		return false;
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
		//System.out.println("Code: " + code);

		List<TransactionBean> transList = TestDAOFactory.getTestInstance()
				.getTransactionDAO().getAllTransactions();

		for (TransactionBean t : transList) {
			// For Testing Purposes
			// System.out.println(t.getTransactionType());
			// System.out.println("Code2: " + code);
			// System.out.println(t.getTimeLogged());
			// System.out.println("getloggedin: " + t.getLoggedInMID() +
			// " loggedinatm: " + loggedInMID);
			// System.out.println("getsecondary: " + t.getSecondaryMID() +
			// " secondaryatm: " + secondaryMID + "/n");
			if ((t.getTransactionType() == code)
					&& (t.getLoggedInMID() == loggedInMID)
					&& (t.getSecondaryMID() == secondaryMID)) {
				assertTrue(t.getTransactionType() == code);
				// System.out.println("here");
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
	public static void assertNotLogged(TransactionType code, long loggedInMID,
			long secondaryMID, String addedInfo) throws DBException {
		List<TransactionBean> transList = TestDAOFactory.getTestInstance()
				.getTransactionDAO().getAllTransactions();
		for (TransactionBean t : transList) {
			if ((t.getTransactionType() == code)
					&& (t.getLoggedInMID() == loggedInMID)
					&& (t.getSecondaryMID() == secondaryMID)
					&& (t.getAddedInfo().trim().contains(addedInfo))) {
				fail("Event was logged, but should NOT have been logged");
				return;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#countTestCases()
	 */
	@Override
	public int countTestCases() {
		// TODO Auto-generated method stub
		return super.countTestCases();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#createResult()
	 */
	@Override
	protected TestResult createResult() {
		// TODO Auto-generated method stub
		return super.createResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#run()
	 */
	@Override
	public TestResult run() {
		// TODO Auto-generated method stub
		return super.run();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#run(junit.framework.TestResult)
	 */
	@Override
	public void run(TestResult result) {
		// TODO Auto-generated method stub
		super.run(result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#runBare()
	 */
	@Override
	public void runBare() throws Throwable {
		// TODO Auto-generated method stub
		super.runBare();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#runTest()
	 */
	@Override
	protected void runTest() throws Throwable {
		// TODO Auto-generated method stub
		super.runTest();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		super.setName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
