package edu.ncsu.csc.itrust.seleniumtests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SearchUserTest extends iTrustSeleniumTest {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.standardData();
	}

  @Test
  public void testGetPatient() throws Exception {
    logIn("9000000000", "pw");
      driver.findElement(By.id("patientinfo")).click();
      driver.findElement(By.linkText("Basic Health Information")).click();
//    driver.findElement(By.id("searchBox")).clear();
//    driver.findElement(By.id("searchBox")).sendKeys("Random Person" + Keys.ENTER);

      driver.findElement(By.name("FIRST_NAME")).clear();
      driver.findElement(By.name("FIRST_NAME")).sendKeys("Random");
      driver.findElement(By.xpath("//input[@value='User Search']")).click();
      assertTrue(driver.findElement(By.id("UserSearch")).getText().contains("Random Person"));
  }
  
  @Test
  public void testGetPatient2() throws Exception {
	  logIn("9000000000", "pw");
	  driver.findElement(By.id("patientinfo")).click();
	  driver.findElement(By.linkText("Basic Health Information")).click();
      driver.findElement(By.name("FIRST_NAME")).clear();
      driver.findElement(By.name("FIRST_NAME")).sendKeys("Andy");
      driver.findElement(By.xpath("//input[@value='User Search']")).click();
	  assertTrue(driver.findElement(By.id("UserSearch")).getText().contains("Andy Programmer"));
  }
  
  @Test
  public void testGetPatient3() throws Exception {
	  logIn("9000000000", "pw");
	  driver.findElement(By.id("other")).click();
	  driver.findElement(By.linkText("UAPs")).click();
	  driver.findElement(By.name("FIRST_NAME")).clear();
	  driver.findElement(By.name("FIRST_NAME")).sendKeys("Kelly");
	  driver.findElement(By.name("LAST_NAME")).clear();
	  driver.findElement(By.name("LAST_NAME")).sendKeys("Doctor");
	  driver.findElement(By.xpath("//input[@value='User Search']")).click();
	  assertTrue(driver.findElement(By.id("searchresult")).getText().contains("KellyDoctor"));
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
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
