package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.*;
 
public class BasicHealthInfoTest extends iTrustSeleniumTest {
 
	@Override
	protected void setUp() throws Exception{
		super.setUp();
	}
	
	public void testBasicHealthViewed() throws Exception{
		WebDriver driver = login("9000000000", "pw");
		
		System.out.println(driver.getTitle());
		
		//assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, ""); //still have to implement this
		
		driver.findElement(By.linkText("Basic Health Information")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.cssSelector("input[value='2']")).submit();
		
		assertEquals(ADDRESS + "auth/hcp-uap/viewBasicHealth.jsp", driver.getCurrentUrl());
		
		driver.findElement(By.cssSelector("a[href='/iTrust/logout.jsp']")).click(); //By.linkText won't work for some reason...
		
		assertEquals(ADDRESS + "auth/forwardUser.jsp", driver.getCurrentUrl());
		
		driver.quit();
		
		driver = login("2", "pw");
		
		assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		assertTrue(driver.getPageSource().contains("viewed your health records history today at"));
	}
	
	/*public void testBasicHealthSmokingStatus() throws Exception{
		WebConversation wc = login("9000000000", "pw");	
		WebResponse wr = wc.getCurrentPage();
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click Document Office Visit
		wr = wr.getLinkWith("Document Office Visit").click();
		assertEquals(ADDRESS + "auth/getPatientID.jsp?forward=/iTrust/auth/hcp-uap/documentOfficeVisit.jsp", wr.getURL().toString());
		//Choose patient 2
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", wr.getURL().toString());
		
		//Click Yes, Document Office Visit
		WebForm form = wr.getForms()[0];
		form.getButtons()[0].click();
		//Verify Edit Office Visit page
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Document Office Visit", wr.getTitle());
		
		//Add a new office visit
		form = wr.getFormWithID("mainForm");
		//Click the create button
		form.getButtonWithID("update").click();
		
		//Verify "Information Successfully Updated" message
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 2L, "");
		
		//Check patient smoking status options
		assertTrue(wr.getText().contains("1 - Current every day smoker"));
		assertTrue(wr.getText().contains("2 - Current some day smoker"));
		assertTrue(wr.getText().contains("3 - Former smoker"));
		assertTrue(wr.getText().contains("4 - Never smoker"));
		assertTrue(wr.getText().contains("5 - Smoker, current status unknown"));
		assertTrue(wr.getText().contains("9 - Unknown if ever smoked"));
	}*/
}