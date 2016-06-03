package edu.ncsu.csc.itrust.selenium;


import java.util.List;

import junit.framework.TestCase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

/**
 * Base class for all Selenium tests
 *
 * @author Jakin Ivey
 */
public abstract class iTrustSeleniumTest extends TestCase { 
    /**ADDRESS*/ 
    public static final String ADDRESS = "http://localhost:8080/iTrust/";
    
    protected TestDataGenerator gen = new TestDataGenerator();

    protected void setUp() throws Exception {
        gen.clearAllTables();
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
     * Asserts that the text is on the page
     * @param text
     * @param driver
     */
    public void assertTextPresent(String text, WebDriver driver) {
        List<WebElement> list = driver.findElements(By
                .xpath("//*[contains(body, \"" + text + "\")]"));
        assertTrue("Text not found!", list.size() > 0);
    }

    /**
     * Asserts that the text is not on the page. Does not pause for text to appear.
     * @param text
     * @param driver
     */
    public void assertTextNotPresent(String text, WebDriver driver) {
        assertFalse("Text should not be found!",
                driver.findElement(By.cssSelector("BODY")).getText().contains(text));
    }

}
