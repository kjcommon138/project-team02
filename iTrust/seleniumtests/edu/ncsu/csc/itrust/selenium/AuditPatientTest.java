package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;

public class AuditPatientTest extends iTrustSeleniumTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.patientDeactivate();
	}
	
	@Test
	public void testHCPDeactivatePatient() throws Exception {
		WebDriver driver = login("9000000000", "pw");
		
		driver.findElement(By.linkText("Audit Patients")).click();
		assertEquals(driver.getTitle(), "iTrust - Please Select a Patient");
		
		
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.cssSelector("input[value='Deactivate']")).submit();
		assertEquals(driver.getTitle(), "iTrust - Audit Page (UC62)");
		
//		WebForm patientForm = wr.getForms()[0];
//		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
//		patientForm.getButtons()[1].click();
//		wr = wc.getCurrentPage();
//		assertEquals(ADDRESS + "auth/hcp/auditPage.jsp", wr.getURL().toString());
//		
//		WebForm editPatientForm = wr.getForms()[0];
//		editPatientForm.setParameter("understand", "I UNDERSTAND");
//		editPatientForm.getButtons()[0].click();
//		wr = wc.getCurrentPage();
//		assertTrue(wr.getText().contains("Patient Successfully Deactivated"));
	}
	
//	public void testHCPDeactivatePatientWrongConfirmation() throws Exception{
//		WebConversation wc = login("9000000000", "pw");	
//		WebResponse wr = wc.getCurrentPage();
//		
//		wr = wr.getLinkWith("Audit Patients").click();
//		WebForm patientForm = wr.getForms()[0];
//		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
//		patientForm.getButtons()[1].click();
//		wr = wc.getCurrentPage();
//		assertEquals(ADDRESS + "auth/hcp/auditPage.jsp", wr.getURL().toString());
//		
//		WebForm editPatientForm = wr.getForms()[0];
//		editPatientForm.setParameter("understand", "iunderstand");
//		editPatientForm.getButtons()[0].click();
//		wr = wc.getCurrentPage();
//		assertTrue(wr.getText().contains("You must type \"I UNDERSTAND\" in the textbox."));
//	}
//	
//	public void testHCPActivatePatient() throws Exception {
//		WebConversation wc = login("9000000000", "pw");	
//		WebResponse wr = wc.getCurrentPage();
//		
//		wr = wr.getLinkWith("Audit Patients").click();
//		WebForm patientForm = wr.getForms()[0];
//		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "314159");
//		patientForm.getButtons()[1].click();
//		wr = wc.getCurrentPage();
//		assertEquals(ADDRESS + "auth/hcp/auditPage.jsp", wr.getURL().toString());
//		
//		WebForm editPatientForm = wr.getForms()[0];
//		editPatientForm.setParameter("understand", "I UNDERSTAND");
//		editPatientForm.getButtons()[0].click();
//		wr = wc.getCurrentPage();
//		assertTrue(wr.getText().contains("Patient Successfully Activated"));
//	}
//	
//	public void testHCPActivatePatientWrongConfirmation() throws Exception {
//		WebConversation wc = login("9000000000", "pw");	
//		WebResponse wr = wc.getCurrentPage();
//		
//		wr = wr.getLinkWith("Audit Patients").click();
//		WebForm patientForm = wr.getForms()[0];
//		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "314159");
//		patientForm.getButtons()[1].click();
//		wr = wc.getCurrentPage();
//		assertEquals(ADDRESS + "auth/hcp/auditPage.jsp", wr.getURL().toString());
//		
//		WebForm editPatientForm = wr.getForms()[0];
//		editPatientForm.setParameter("understand", "WOAH");
//		editPatientForm.getButtons()[0].click();
//		wr = wc.getCurrentPage();
//		assertTrue(wr.getText().contains("You must type \"I UNDERSTAND\" in the textbox."));
//	}

}
