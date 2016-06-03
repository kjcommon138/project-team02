package edu.ncsu.csc.itrust.seleniumtests;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class EmergencyUseCaseTest extends iTrustSeleniumTest{
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
    driver = new HtmlUnitDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @Test
  public void testERViewEmergencyReport1() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000006");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.cssSelector("h2.panel-title")).click();
    driver.findElement(By.linkText("Emergency Patient Report")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
    driver.findElement(By.xpath("//input[@value='2']")).submit();
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Blood Type: O-"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Pollen 06/05/2007"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Penicillin 06/04/2007"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("647641512 Prioglitazon"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("no immunizations on record"));
    assertLogged(TransactionType.EMERGENCY_REPORT_VIEW, 9000000006L, 2L, "");
  }

  @Test
  public void testHCPViewEmergencyReport1() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000000");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.cssSelector("h2.panel-title")).click();
    driver.findElement(By.linkText("Emergency Patient Report")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
    driver.findElement(By.xpath("//input[@value='2']")).submit();
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Blood Type: O-"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Pollen 06/05/2007"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Penicillin 06/04/2007"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("647641512 Prioglitazon"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("no immunizations on record"));
    assertLogged(TransactionType.EMERGENCY_REPORT_VIEW, 9000000000L, 2L, "");
  }
  
  @Test
  public void testHCPViewEmergencyReport2() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000000");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.cssSelector("h2.panel-title")).click();
    driver.findElement(By.linkText("Emergency Patient Report")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
    driver.findElement(By.xpath("//input[@value='1']")).submit();
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Blood Type: AB+"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("No allergies on record"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("No current prescriptions on record"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("no immunizations on record"));
    assertLogged(TransactionType.EMERGENCY_REPORT_VIEW, 9000000000L, 1L, "");
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
