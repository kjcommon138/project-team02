package edu.ncsu.csc.itrust.selenium;



import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class ViewHealthRecordsHistoryTest extends iTrustSeleniumTest {
  private static final String JavaScriptExecutor = null;
private StringBuffer verificationErrors = new StringBuffer();

  @Override
	protected void setUp() throws Exception {
	  	super.setUp();
		gen.clearAllTables();
		gen.standardData();
	  }

  	  //testViewHealthRecordsHistory1
	  @Test
	  public void testOfficeVisit4MonthOldViewHealthMetrics() throws Exception {
		  gen.clearAllTables();
			gen.standardData();
			//This logs us into iTrust and returns the HtmlUnitDriver for use in this case
			HtmlUnitDriver driver = super.login("8000000011", "pw");
			
		    assertEquals("iTrust - HCP Home", driver.getTitle());
		    
		    //Clicks Office Visit -> Document Office Visit
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		    driver.findElement(By.linkText("Document Office Visit")).click();
		    
		    //Enters the patient's MID in the search box
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.name("UID_PATIENTID")).sendKeys("101");
		    driver.findElement(By.xpath("//input[@value='101']")).submit();
		    assertEquals("iTrust - Document Office Visit", driver.getTitle());
		    
		    //Click the Document Office Visit Button
		    assertEquals("", driver.findElement(By.cssSelector("input[type=\"submit\"]")).getText());
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    
		    //Now we're on the document office visit form
		    driver.findElement(By.name("visitDate")).clear();
		    driver.findElement(By.name("visitDate")).sendKeys("10/01/2013");
		    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Test Hospital 8181818181");
		    driver.findElement(By.name("notes")).clear();
		    driver.findElement(By.name("notes")).sendKeys("Brynn can start eating rice cereal mixed with breast milk or formula once a day.");
		    driver.findElement(By.id("update")).click();
		    assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    
		    driver.findElement(By.name("weight")).clear();
		    driver.findElement(By.name("weight")).sendKeys("16.5");
		    driver.findElement(By.name("height")).clear();
		    driver.findElement(By.name("height")).sendKeys("22.3");
		    driver.findElement(By.name("headCircumference")).clear();
		    driver.findElement(By.name("headCircumference")).sendKeys("16.1");
		    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");	    
		    
		    driver.findElement(By.id("addHR")).click();
		    assertEquals("Health information successfully updated.", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[3]")).getText());
		    
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("Basic Health Information")).click();
		    
		    assertEquals("10/01/2013", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td")).getText());
		    assertEquals("22.3in", driver.findElement(By.id("viewHeightPercentile0")).getText());
		    assertEquals("16.5lbs", driver.findElement(By.id("viewWeightPercentile0")).getText());
		    assertEquals("23.33", driver.findElement(By.id("viewBmiPercentile0")).getText());
		    assertEquals("16.1in", driver.findElement(By.id("viewHeadCircPercentile0")).getText());
		    assertEquals("Non-smoking household", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[6]")).getText());
		    
	  }
	  
	  //testViewHealthRecordsHistory2
//	  @Test
	  public void testViewHealthMetricsByHCP() throws Exception {
		  HtmlUnitDriver driver = super.login("8000000011", "pw");
		  
		  assertEquals("iTrust - HCP Home", driver.getTitle());
		  
		  driver.findElement(By.cssSelector("h2.panel-title")).click();
		  driver.findElement(By.linkText("Basic Health Information")).click();
		  
		  driver.findElement(By.id("searchBox")).clear();
		  driver.findElement(By.name("UID_PATIENTID")).sendKeys("102");
		  driver.findElement(By.xpath("//input[@value='102']")).submit();

		  assertEquals("iTrust - Edit Basic Health Record", driver.getTitle());
		  
		  //First row
		  assertEquals("10/28/2013", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td")).getText());
		  assertEquals("34.7in", driver.findElement(By.id("viewHeightPercentile0")).getText());
		  assertEquals("30.2lbs", driver.findElement(By.id("viewWeightPercentile0")).getText());
		  assertEquals("Indoor smokers", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[6]")).getText());
		  assertEquals("02/02/2012", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td")).getText());
		  
		  //Second Row
		  assertEquals("25.7in", driver.findElement(By.id("viewHeightPercentile1")).getText());
		  assertEquals("15.8lbs", driver.findElement(By.id("viewWeightPercentile1")).getText());
		  assertEquals("17.1in", driver.findElement(By.id("viewHeadCircPercentile1")).getText());
		  assertEquals("Indoor smokers", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[6]")).getText());
		  assertEquals("12/01/2011", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td")).getText());
		  
		  //Third Row
		  assertEquals("22.5in", driver.findElement(By.id("viewHeightPercentile2")).getText());
		  assertEquals("12.1lbs", driver.findElement(By.id("viewWeightPercentile2")).getText());
		  assertEquals("16.3in", driver.findElement(By.id("viewHeadCircPercentile2")).getText());
		  assertEquals("Indoor smokers", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[6]")).getText());
		  assertEquals("11/01/2011", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[6]/td")).getText());
		  
		  //Fourth Row
		  assertEquals("21.1in", driver.findElement(By.id("viewHeightPercentile3")).getText());
		  assertEquals("10.3lbs", driver.findElement(By.id("viewWeightPercentile3")).getText());
		  assertEquals("15.3in", driver.findElement(By.id("viewHeadCircPercentile3")).getText());
		  assertEquals("Indoor smokers", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[6]/td[6]")).getText());
		  assertEquals("10/01/2011", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[7]/td")).getText());
		  
		  //Fifth Row
		  assertEquals("19.6in", driver.findElement(By.id("viewHeightPercentile4")).getText());
		  assertEquals("8.3lbs", driver.findElement(By.id("viewWeightPercentile4")).getText());
		  assertEquals("14.5in", driver.findElement(By.id("viewHeadCircPercentile4")).getText());
		  assertEquals("Indoor smokers", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[7]/td[6]")).getText());
		  assertEquals("19.4in", driver.findElement(By.id("viewHeadCircPercentile0")).getText());
		   
//		  driver.findElement(By.linkText("Logout")).click();
	  }
	  
	  //testViewHealthRecordsHistory3
	  @Test
	  public void testOfficeVisit5YrOldViewHealthMetrics() throws Exception {
		HtmlUnitDriver driver = super.login("8000000011", "pw");  

	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Basic Health Information")).click();
		
		driver.findElement(By.id("searchBox")).clear();
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("103");
	    driver.findElement(By.xpath("//input[@value='103']")).submit();

		assertEquals("iTrust - Edit Basic Health Record", driver.getTitle());
	   
		//Youth Row 1
		assertEquals("10/14/2013", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[6]/td")).getText());
	    assertEquals("42.9in", driver.findElement(By.id("viewHeightPercentile")).getText());
	    assertEquals("37.9lbs", driver.findElement(By.id("viewWeightPercentile")).getText());
	    assertEquals("95/65 mmHg", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[6]/td[5]")).getText());
	    assertEquals("Outdoor smokers", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[6]/td[6]")).getText());
	    
	    //Youth Row 2
	    assertEquals("10/15/2012", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[7]/td")).getText());
	    assertEquals("41.3in", driver.findElement(By.linkText("41.3in")).getText());
	    assertEquals("35.8lbs", driver.findElement(By.linkText("35.8lbs")).getText());
	    assertEquals("95/65 mmHg", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[7]/td[5]")).getText());
	    assertEquals("Indoor smokers", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[7]/td[6]")).getText());
	    
	    //Baby Row 1
	    assertEquals("10/01/2011", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td")).getText());
	    assertEquals("39.3in", driver.findElement(By.id("viewHeightPercentile0")).getText());
	    assertEquals("36.5lbs", driver.findElement(By.id("viewWeightPercentile0")).getText());
	    assertEquals("19.9in", driver.findElement(By.id("viewHeadCircPercentile0")).getText());
	    assertEquals("Indoor smokers", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[6]")).getText());

//	    driver.findElement(By.linkText("Logout")).click();
	  }
	  
	  public void testOfficeVisit20YrOldViewHealthMetrics() throws Exception {
			HtmlUnitDriver driver = super.login("8000000011", "pw");
			
			driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("Basic Health Information")).click();
		    
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.name("UID_PATIENTID")).sendKeys("104");
		    driver.findElement(By.xpath("//input[@value='104']")).submit();
		    
		    assertEquals("iTrust - Edit Basic Health Record", driver.getTitle());
		    
		    //Check row 1
		    assertEquals("10/25/2013", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td")).getText());
		    assertEquals("62.3in", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[2]")).getText());
		    assertEquals("124.3lbs", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[3]")).getText());
		    assertEquals("22.51", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[4]")).getText());
		    assertEquals("Normal", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[5]")).getText());
		    assertEquals("100/75 mmHg", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[6]")).getText());
		    assertEquals("N", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[7]")).getText());
		    assertEquals("Non-smoking household", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[8]")).getText());
		    assertEquals("65 mg/dL", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[9]")).getText());
		    assertEquals("102 mg/dL", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[10]")).getText());
		    
		    //Check Row 2
		    assertEquals("10/20/2012", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td")).getText());
		    assertEquals("62.3in", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[2]")).getText());
		    assertEquals("120.7lbs", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[3]")).getText());
		    assertEquals("21.86", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[4]")).getText());
		    assertEquals("N/A", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[5]")).getText());
		    assertEquals("107/72 mmHg", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[6]")).getText());
		    assertEquals("Y", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[7]")).getText());
		    assertEquals("Indoor smokers", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[8]")).getText());
		    assertEquals("63 mg/dL", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[9]")).getText());
		    assertEquals("103 mg/dL", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[4]/td[10]")).getText());
		    
		    //Check Row 3
		    assertEquals("10/10/2011", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td")).getText());
		    assertEquals("62.3in", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[2]")).getText());
		    assertEquals("121.3lbs", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[3]")).getText());
		    assertEquals("21.97", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[4]")).getText());
		    assertEquals("N/A", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[5]")).getText());
		    assertEquals("105/73 mmHg", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[6]")).getText());
		    assertEquals("Y", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[7]")).getText());
		    assertEquals("Indoor smokers", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[8]")).getText());
		    assertEquals("64 mg/dL", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[9]")).getText());
		    assertEquals("102 mg/dL", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[5]/td[10]")).getText());
		  
	  }
	  
	  public void testOfficeVisit24YrOldViewHealthMetrics() throws Exception {
			HtmlUnitDriver driver = super.login("105", "pw");
			
			assertEquals("iTrust - Patient Home", driver.getTitle());

			driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
			driver.findElement(By.linkText("View My Records")).click();
			assertEquals("iTrust - View My Records", driver.getTitle());
			
			//Check the Row
			assertEquals("10/25/2013", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td")).getText());
		    assertEquals("73.1in", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[2]")).getText());
		    assertEquals("210.1lbs", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[3]")).getText());
		    assertEquals("27.64", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[4]")).getText());
		    assertEquals("Overweight", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[5]")).getText());
		    assertEquals("160/100 mmHg", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[6]")).getText());
		    assertEquals("N", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[7]")).getText());
		    assertEquals("Non-smoking household", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[8]")).getText());
		    assertEquals("37 mg/dL", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[9]")).getText());
		    assertEquals("141 mg/dL", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[10]")).getText());
		    assertEquals("162 mg/dL", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[11]")).getText());
	  }
	  
	  public void testHCPLoggingAction() throws Exception {
			HtmlUnitDriver driver = super.login("8000000011", "pw");

			assertEquals("iTrust - HCP Home", driver.getTitle());
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("Basic Health Information")).click();
		    
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.name("UID_PATIENTID")).sendKeys("102");
		    driver.findElement(By.xpath("//input[@value='102']")).submit();

		    assertEquals("iTrust - Edit Basic Health Record", driver.getTitle());
		    driver.findElement(By.id("logoutbutton")).click();
		    driver = super.login("102", "pw");
			WebElement elem = driver.findElement(By.xpath("//*[contains(.,'Shelly Vang viewed your health records history')]")); 

			assertTrue(elem != null);
	  }
	  
	  public void testDeletedHealthRecord() throws Exception {
		  gen.clearAllTables();
			gen.standardData();
			HtmlUnitDriver driver = super.login("9000000000", "pw");
			
			assertEquals("iTrust - HCP Home", driver.getTitle());
		    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
		    driver.findElement(By.linkText("Document Office Visit")).click();
		    
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		    driver.findElement(By.xpath("//input[@value='1']")).submit();

		    assertEquals("iTrust - Document Office Visit", driver.getTitle());
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    assertEquals("iTrust - Document Office Visit", driver.getTitle());
		    
		    driver.findElement(By.name("visitDate")).clear();
		    driver.findElement(By.name("visitDate")).sendKeys("10/01/2013");
		    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Central Hospital");
		    driver.findElement(By.name("notes")).clear();
		    driver.findElement(By.name("notes")).sendKeys("Random has consumed unknown seed 32912");
		    driver.findElement(By.id("update")).click();
		    assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    
		    driver.findElement(By.name("weight")).clear();
		    driver.findElement(By.name("weight")).sendKeys("165.8");
		    driver.findElement(By.name("height")).clear();
		    driver.findElement(By.name("height")).sendKeys("74");
		    driver.findElement(By.name("bloodPressureN")).clear();
		    driver.findElement(By.name("bloodPressureN")).sendKeys("110");
		    driver.findElement(By.name("bloodPressureD")).clear();
		    driver.findElement(By.name("bloodPressureD")).sendKeys("75");
		    new Select(driver.findElement(By.id("isSmoker"))).selectByVisibleText("3 - Former smoker");
		    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
		    driver.findElement(By.name("cholesterolHDL")).clear();
		    driver.findElement(By.name("cholesterolHDL")).sendKeys("68");
		    driver.findElement(By.name("cholesterolLDL")).clear();
		    driver.findElement(By.name("cholesterolLDL")).sendKeys("107");
		    driver.findElement(By.name("cholesterolTri")).clear();
		    driver.findElement(By.name("cholesterolTri")).sendKeys("162");
		    driver.findElement(By.id("addHR")).click();
		    assertEquals("Health information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
//		    driver.findElement(By.id("addHR")).click();

//		    driver.findElement(By.id("removebutton")).click();

//		    assertEquals("Health information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    
		    driver.findElementByLinkText("Basic Health History").click();

		    assertEquals("iTrust - Edit Basic Health Record", driver.getTitle());
		    
		    assertEquals("10/01/2013", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td")).getText());
		    assertEquals("74.0in", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[2]")).getText());
		    assertEquals("165.8lbs", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[3]")).getText());
		    assertEquals("21.29", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[4]")).getText());
		    assertEquals("Normal", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[5]")).getText());
		    assertEquals("110/75 mmHg", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[6]")).getText());
		    assertEquals("N", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[7]")).getText());
		    assertEquals("Non-smoking household", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[8]")).getText());
		    assertEquals("68 mg/dL", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[9]")).getText());
		    assertEquals("107 mg/dL", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[10]")).getText());
		    assertEquals("162 mg/dL", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[11]")).getText());
		    
	  }
	  
	  public void testNoHealthRecordsExistByHCP() throws Exception {
			HtmlUnitDriver driver = super.login("8000000011", "pw");
			
			assertEquals("iTrust - HCP Home", driver.getTitle());
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("Basic Health Information")).click();
		    
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.name("UID_PATIENTID")).sendKeys("101");
		    driver.findElement(By.xpath("//input[@value='101']")).submit();

		    assertEquals("iTrust - Edit Basic Health Record", driver.getTitle());
		    assertEquals("No health records available", driver.findElement(By.cssSelector("i")).getText());
		  
	  }
	  
	  public void testNoHealthRecordsExistByPatient() throws Exception {
			HtmlUnitDriver driver = super.login("101", "pw");
			
			assertEquals("iTrust - Patient Home", driver.getTitle());
		    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div")).click();
		    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div")).click();
		    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		    driver.findElement(By.linkText("View My Records")).click();
		    assertEquals("No health records available", driver.findElement(By.cssSelector("i")).getText());
		 
			
	  }
	  
	  public void testOfficeVisitDateIsBirthDate() throws Exception {
		  gen.clearAllTables();
			gen.standardData();
			HtmlUnitDriver driver = super.login("8000000011", "pw");
			
			assertEquals("iTrust - HCP Home", driver.getTitle());
		    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		    driver.findElement(By.linkText("Document Office Visit")).click();
		    
		    driver.findElement(By.id("searchBox")).clear();
		    driver.findElement(By.name("UID_PATIENTID")).sendKeys("101");
		    driver.findElement(By.xpath("//input[@value='101']")).submit();

		    assertEquals("iTrust - Document Office Visit", driver.getTitle());
		    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    assertEquals("iTrust - Document Office Visit", driver.getTitle());
		    
		    driver.findElement(By.name("visitDate")).clear();
		    driver.findElement(By.name("visitDate")).sendKeys("05/01/2016");
		    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Central Hospital");
		    driver.findElement(By.name("notes")).clear();
		    driver.findElement(By.name("notes")).sendKeys("Brynn is growing into a beautiful sunflower.");
		    driver.findElement(By.id("update")).click();
		    assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    
		    driver.findElement(By.name("height")).clear();
		    driver.findElement(By.name("height")).sendKeys("42.8");
		    driver.findElement(By.name("weight")).clear();
		    driver.findElement(By.name("weight")).sendKeys("41.2");
		    driver.findElement(By.name("bloodPressureN")).clear();
		    driver.findElement(By.name("bloodPressureN")).sendKeys("123");
		    driver.findElement(By.name("bloodPressureD")).clear();
		    driver.findElement(By.name("bloodPressureD")).sendKeys("64");
		    new Select(driver.findElement(By.id("householdSmokingStatus"))).selectByVisibleText("1 - non-smoking household");
		    driver.findElement(By.id("addHR")).click();
		    assertEquals("Health information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		    
		    driver.findElement(By.cssSelector("h2.panel-title")).click();
		    driver.findElement(By.linkText("Basic Health Information")).click();
		    assertEquals("iTrust - Edit Basic Health Record", driver.getTitle());
		    
		    assertEquals("05/01/2016", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td")).getText());
		    assertEquals("42.8in", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[2]")).getText());
		    assertEquals("41.2lbs", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[3]")).getText());
		    assertEquals("123/64 mmHg", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[5]")).getText());
		    assertEquals("Non-smoking household", driver.findElement(By.xpath("//table[@id='HealthRecordsTable']/tbody/tr[3]/td[6]")).getText());
		  
	  }
	  
	  private boolean isElementPresent(HtmlUnitDriver driver, By by) {
		    try {
		      driver.findElement(by);
		      return true;
		    } catch (NoSuchElementException e) {
		      return false;
		    }
		  }

}
