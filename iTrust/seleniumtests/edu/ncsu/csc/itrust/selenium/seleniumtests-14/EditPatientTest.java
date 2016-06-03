package edu.ncsu.csc.itrust.seleniumtests;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class EditPatientTest extends iTrustSeleniumTest{
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
    driver = new HtmlUnitDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @Test
  public void testCauseOfDeathValidation() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000000");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.cssSelector("div.panel-heading")).click();
    driver.findElement(By.linkText("Patient Information")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
    driver.findElement(By.xpath("//input[@value='2']")).submit();
    assertEquals("iTrust - Edit Patient", driver.getTitle());
    driver.findElement(By.name("dateOfDeathStr")).clear();
    driver.findElement(By.name("dateOfDeathStr")).sendKeys("");
    driver.findElement(By.name("action")).click();
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Cause of Death cannot be specified without Date of Death!]"));
	assertNotLogged(TransactionType.DEMOGRAPHICS_EDIT, 9000000000L, 2L, "");
  }
  
  @Test
  public void testMisspellings() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000000");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.cssSelector("div.panel-heading")).click();
    driver.findElement(By.linkText("Patient Information")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
    driver.findElement(By.xpath("//input[@value='2']")).submit();
    assertEquals("iTrust - Edit Patient", driver.getTitle());
    assertFalse(driver.findElement(By.cssSelector("BODY")).getText().contains("Mother MIDs"));	
  }
  
  @Test
  public void testViewDemographics() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000000");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.cssSelector("h2.panel-title")).click();
    driver.findElement(By.linkText("Patient Information")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
    driver.findElement(By.xpath("//input[@value='2']")).submit();
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys("history@gmail.com");
    driver.findElement(By.name("action")).click();
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Information Successfully Updated"));	
    driver.findElement(By.xpath("//form[@id='viewHistory']/table/tbody/tr[2]/td[2]/input")).click();
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("history@gmail.com"));	
  }
  
  @Test
  public void testMFWithPersonnelMID() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000000");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.cssSelector("h2.panel-title")).click();
    driver.findElement(By.linkText("Patient Information")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
    driver.findElement(By.xpath("//input[@value='2']")).submit();
    driver.findElement(By.name("motherMID")).clear();
    driver.findElement(By.name("motherMID")).sendKeys("9");
    driver.findElement(By.name("fatherMID")).clear();
    driver.findElement(By.name("fatherMID")).sendKeys("98");
    driver.findElement(By.name("action")).click();
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Mother MID: 1-10 digit number not beginning with 9, Father MID: 1-10 digit number not beginning with 9]"));	
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
