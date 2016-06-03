package edu.ncsu.csc.itrust.seleniumtests;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import edu.ncsu.csc.itrust.beans.TransactionBean;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

abstract public class iTrustSeleniumTest extends TestCase{
	/*
	 * The URL for iTrust, change as needed
	 */
	/**ADDRESS*/
	protected String baseUrl = "http://localhost:8080/iTrust/";
	
	/**gen*/
	protected TestDataGenerator gen = new TestDataGenerator();
	
	protected HtmlUnitDriver driver;
	
	protected boolean acceptNextAlert = true;
	
	protected StringBuffer verificationErrors = new StringBuffer();

	  @Before
	  protected void setUp() throws Exception {
		driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	  }
	
	  @After
	  protected void tearDown() throws Exception {
	    driver.quit();
	  }

	  protected boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  protected boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  protected String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
}
