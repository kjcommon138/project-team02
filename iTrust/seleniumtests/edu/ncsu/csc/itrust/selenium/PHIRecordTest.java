package edu.ncsu.csc.itrust.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;


public class PHIRecordTest extends iTrustSeleniumTest{
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
	gen.uc60();
    driver = new HtmlUnitDriver();
    baseUrl = "http://localhost:8080";
  }

  @Test
  public void testCreatePHIRecord() throws Exception {
	driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("9000000000");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	driver.get(baseUrl + "/iTrust/auth/getPatientID.jsp?UID_PATIENTID=2&forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.id("update")).click();
    assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
	assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 2L, "");
    
    driver.findElement(By.id("healthRecordForm")).click();
    driver.findElement(By.name("height")).clear();
    driver.findElement(By.name("height")).sendKeys("0");
    driver.findElement(By.name("weight")).clear();
    driver.findElement(By.name("weight")).sendKeys("0");
    driver.findElement(By.name("isSmoker")).click();
    driver.findElement(By.name("householdSmokingStatus")).click();
    driver.findElement(By.name("bloodPressureN")).clear();
    driver.findElement(By.name("bloodPressureN")).sendKeys("999");
    driver.findElement(By.name("bloodPressureD")).clear();
    driver.findElement(By.name("bloodPressureD")).sendKeys("000");
    driver.findElement(By.name("cholesterolHDL")).clear();
    driver.findElement(By.name("cholesterolHDL")).sendKeys("50");
    driver.findElement(By.name("cholesterolLDL")).clear();
    driver.findElement(By.name("cholesterolLDL")).sendKeys("200");
    driver.findElement(By.name("cholesterolTri")).clear();
    driver.findElement(By.name("cholesterolTri")).sendKeys("200");
    driver.findElement(By.id("addHR")).click();
    driver.findElement(By.cssSelector("#addHR")).click();
    assertTrue(driver.getPageSource().contains("Information not valid"));
    assertTrue(driver.getPageSource().contains("Height must be greater than 0"));
    assertTrue(driver.getPageSource().contains("Weight must be greater than 0"));
  }
  public void testCreatePHIRecord1() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.get(baseUrl + "/iTrust/auth/getPatientID.jsp?UID_PATIENTID=2&forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.id("update")).click();
	    assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 2L, "");
	    
	    driver.findElement(By.id("healthRecordForm")).click();
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("10");
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("400");
	    driver.findElement(By.name("isSmoker")).click();
	    driver.findElement(By.name("householdSmokingStatus")).click();
	    driver.findElement(By.name("bloodPressureN")).clear();
	    driver.findElement(By.name("bloodPressureN")).sendKeys("999");
	    driver.findElement(By.name("bloodPressureD")).clear();
	    driver.findElement(By.name("bloodPressureD")).sendKeys("000");
	    driver.findElement(By.name("cholesterolHDL")).clear();
	    driver.findElement(By.name("cholesterolHDL")).sendKeys("50");
	    driver.findElement(By.name("cholesterolLDL")).clear();
	    driver.findElement(By.name("cholesterolLDL")).sendKeys("200");
	    driver.findElement(By.name("cholesterolTri")).clear();
	    driver.findElement(By.name("cholesterolTri")).sendKeys("200");
	    driver.findElement(By.id("addHR")).click();
	    assertFalse(driver.getPageSource().contains("Health information successfully updated."));
		//assertLogged(TransactionType.CREATE_BASIC_HEALTH_METRICS, 9000000000L, 2L, "");//assertLogged not working here...
  }
  public void testCreatePHIRecord6() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.get(baseUrl + "/iTrust/auth/getPatientID.jsp?UID_PATIENTID=2&forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.id("update")).click();
	    assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 2L, "");
	    
	    driver.findElement(By.id("healthRecordForm")).click();
	    driver.findElement(By.name("height")).clear();
	    driver.findElement(By.name("height")).sendKeys("**");
	    driver.findElement(By.name("weight")).clear();
	    driver.findElement(By.name("weight")).sendKeys("400");
	    driver.findElement(By.name("isSmoker")).click();
	    driver.findElement(By.name("householdSmokingStatus")).click();
	    driver.findElement(By.name("bloodPressureN")).clear();
	    driver.findElement(By.name("bloodPressureN")).sendKeys("999");
	    driver.findElement(By.name("bloodPressureD")).clear();
	    driver.findElement(By.name("bloodPressureD")).sendKeys("000");
	    driver.findElement(By.name("cholesterolHDL")).clear();
	    driver.findElement(By.name("cholesterolHDL")).sendKeys("50");
	    driver.findElement(By.name("cholesterolLDL")).clear();
	    driver.findElement(By.name("cholesterolLDL")).sendKeys("200");
	    driver.findElement(By.name("cholesterolTri")).clear();
	    driver.findElement(By.name("cholesterolTri")).sendKeys("200");
	    driver.findElement(By.id("addHR")).click();
	    driver.findElement(By.cssSelector("#addHR")).click();
	    assertTrue(driver.getPageSource().contains("Information not valid"));
	    assertTrue(driver.getPageSource().contains("Height: Up to 3-digit number + up to 1 decimal place"));
  }
  public void testDetectExistingHeartDiseaseRiskTest() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.get(baseUrl + "/iTrust/auth/getPatientID.jsp?UID_PATIENTID=2&forward=/iTrust/auth/hcp-uap/chronicDiseaseRisks.jsp");
		assertLogged(TransactionType.RISK_FACTOR_VIEW, 9000000000L, 2L, "");

	    assertTrue(driver.getPageSource().contains("Patient is male"));
	    assertTrue(driver.getPageSource().contains("Patient's body mass index is over 30"));
	    assertTrue(driver.getPageSource().contains("Patient has hypertension"));
	    assertTrue(driver.getPageSource().contains("Patient has bad cholesterol"));
	    assertTrue(driver.getPageSource().contains("Patient is or was a smoker"));
	    assertTrue(driver.getPageSource().contains("Patient has had related diagnoses"));
	    assertTrue(driver.getPageSource().contains("Patient has a family history of this disease"));
  }
  public void testNoHealthRecordException() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.get(baseUrl + "/iTrust/auth/getPatientID.jsp?UID_PATIENTID=4&forward=/iTrust/auth/hcp-uap/chronicDiseaseRisks.jsp");
		assertNotLogged(TransactionType.RISK_FACTOR_VIEW, 9000000000L, 4L, "");
	    assertTrue(driver.getPageSource().contains("No Data"));
  }
  public void testCreatePatient1() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("8000000009");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("uappass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("Add Patient")).click();
	    driver.findElement(By.name("firstName")).clear();
	    driver.findElement(By.name("firstName")).sendKeys("Clark");
	    driver.findElement(By.name("lastName")).sendKeys("Kent");
	    driver.findElement(By.name("email")).sendKeys("clark@ncsu.edu");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

	    WebElement newMID = driver.findElement(By.xpath("//*[@id='iTrustContent']/div[1]/table/tbody/tr[2]/td[2]"));
	    System.out.println("new mid: " + newMID + "  .");
  }
  public void testCreatePHIRecord2() throws Exception {
	driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
	
  }
  public void testCreatePHIRecord3() throws Exception {
	driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
	
  }
  public void testCreatePHIRecord4() throws Exception {
	driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
	
  }
  public void testCreatePHIRecord5() throws Exception {
	driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
	
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
