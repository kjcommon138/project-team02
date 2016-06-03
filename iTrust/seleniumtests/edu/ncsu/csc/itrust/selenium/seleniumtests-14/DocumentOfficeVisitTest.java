package edu.ncsu.csc.itrust.seleniumtests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class DocumentOfficeVisitTest extends iTrustSeleniumTest{
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
    driver = new HtmlUnitDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  /*
   * Authenticate HCP
   * MID 9000000000
   * Password: pw
   * Choose Document Office Visit
   * Enter Patient MID 2 and confirm
   * Choose to document new office vist.
   * Enter Fields:
   * Date: 2005-11-2
   * Notes: Great patient!
   */  
  @Test
  public void testDocumentOfficeVisit1() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000000");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - HCP Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
    driver.findElement(By.linkText("Document Office Visit")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
    driver.findElement(By.xpath("//input[@value='2']")).submit();
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - Document Office Visit", driver.getTitle());
    driver.findElement(By.name("visitDate")).clear();
    driver.findElement(By.name("visitDate")).sendKeys("11/02/2005");
    new Select(driver.findElement(By.name("apptType"))).selectByVisibleText("General Checkup");
    driver.findElement(By.name("notes")).clear();
    driver.findElement(By.name("notes")).sendKeys("Great Patient!");
    driver.findElement(By.id("update")).click();
    //Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Information Successfully Updated[\\s\\S]*$"));
	assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 2L, "Office visit");
  }

  /*
   * Authenticate HCP
   * MID 9000000000
   * Password: pw
   * Choose Document Office Visit
   * Enter Patient MID 2 and confirm
   * Choose to document new office vist.
   * Enter Fields:
   * Date: 2005-11-21
   * Notes: <script>alert('ha ha ha');</script>
   */
  @Test
  public void testDocumentOfficeVisit2() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000000");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - HCP Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
    driver.findElement(By.linkText("Document Office Visit")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
    driver.findElement(By.xpath("//input[@value='2']")).submit();
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - Document Office Visit", driver.getTitle());
    driver.findElement(By.name("visitDate")).clear();
    driver.findElement(By.name("visitDate")).sendKeys("11/21/2005");
    new Select(driver.findElement(By.name("apptType"))).selectByVisibleText("General Checkup");
    driver.findElement(By.name("notes")).clear();
    driver.findElement(By.name("notes")).sendKeys("<script>alert('ha ha ha');</script>");
    driver.findElement(By.id("update")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Notes: Up to 300 alphanumeric characters, with space, and other punctuation[\\s\\S]*$"));
	assertNotLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 2L, "Office visit");
  }
  
  /*
   * Authenticate UAP
   * MID 8000000009
   * Password: uappass1
   * Choose "Document Office Visit"
   * Enter Patient MID 1
   * Enter Fields:
   * Date: 2005-11-21
   * Notes: "I like diet-coke"
   */
  @Test
  public void testDocumentOfficeVisit6() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("8000000009");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("uappass1");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - UAP Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
    driver.findElement(By.linkText("Document Office Visit")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
    driver.findElement(By.xpath("//input[@value='1']")).submit();
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - Document Office Visit", driver.getTitle());
    driver.findElement(By.name("visitDate")).clear();
    driver.findElement(By.name("visitDate")).sendKeys("11/21/2005");
    new Select(driver.findElement(By.name("apptType"))).selectByVisibleText("General Checkup");
    driver.findElement(By.name("notes")).clear();
    driver.findElement(By.name("notes")).sendKeys("I like diet-coke");
    driver.findElement(By.id("update")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Information Successfully Updated[\\s\\S]*$"));
	assertLogged(TransactionType.OFFICE_VISIT_CREATE, 8000000009L, 1L, "Office visit");
  }
  
  /*
   * Authenticate HCP
   * MID 8000000009
   * Password: uappass1
   * Choose Document Office Visit
   * Enter Patient MID 1 and confirm
   * Choose to document new office vist.
   * Enter Fields:
   * Date: 2005-11-21
   * Notes:"I like diet-coke #"
   */
  @Test
  public void testUpdateOfficeVisitOctothorpe() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("8000000009");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("uappass1");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - UAP Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
    driver.findElement(By.linkText("Document Office Visit")).click();
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
    driver.findElement(By.xpath("//input[@value='1']")).submit();
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - Document Office Visit", driver.getTitle());
    driver.findElement(By.name("visitDate")).clear();
    driver.findElement(By.name("visitDate")).sendKeys("11/21/2005");
    new Select(driver.findElement(By.name("apptType"))).selectByVisibleText("General Checkup");
    driver.findElement(By.name("notes")).clear();
    driver.findElement(By.name("notes")).sendKeys("I like diet-coke #");
    driver.findElement(By.id("update")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Information Successfully Updated[\\s\\S]*$"));
	assertLogged(TransactionType.OFFICE_VISIT_CREATE, 8000000009L, 1L, "Office visit");
  }

  /*
   * Authenticate HCP
   * MID 8000000009
   * Password: uappass1
   * Choose Document Office Visit
   * Enter Patient MID 1 and confirm
   * Choose to document new office vist.
   * Enter Fields:
   * Date: 2005-11-21
   * Notes:"I like diet-coke ;"
   */
  @Test
  public void testUpdateOfficeVisitSemicolon() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("8000000009");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("uappass1");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - UAP Home", driver.getTitle());
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
    driver.findElement(By.linkText("Document Office Visit")).click();
    driver.get(ADDRESS + "/iTrust/auth/getPatientID.jsp?forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp");
    driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
    driver.findElement(By.xpath("//input[@value='1']")).submit();
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    assertEquals("iTrust - Document Office Visit", driver.getTitle());
    driver.findElement(By.name("visitDate")).clear();
    driver.findElement(By.name("visitDate")).sendKeys("11/21/2005");
    new Select(driver.findElement(By.name("apptType"))).selectByVisibleText("General Checkup");
    driver.findElement(By.name("notes")).clear();
    driver.findElement(By.name("notes")).sendKeys("I like diet-coke ;");
    driver.findElement(By.id("update")).click();
    // Warning: assertTextPresent may require manual changes
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*Information Successfully Updated[\\s\\S]*$"));
	assertLogged(TransactionType.OFFICE_VISIT_CREATE, 8000000009L, 1L, "Office visit");
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
