package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ChangePasswordTest extends iTrustSeleniumTest {
	
    private static WebDriver driver = null;
	
	@Before
	public void setUp() throws Exception {
	    // Create a new instance of the driver
	    driver = new HtmlUnitDriver();
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testChangePassword_Acceptance_Short() throws Exception {
		//Patient1 logs into iTrust
		driver = (HtmlUnitDriver)login("1", "pw");
		assertTrue(driver.getTitle().contains("iTrust - Patient Home"));  

		//User goes to change password
		List<WebElement> links = driver.findElements(By.tagName("a"));

		int count = 0;
		//get the second link with General Checkup-5
		for(int i = 0; i < links.size(); i++) {
			if(links.get(i).getAttribute("href").contains("changePassword"))
			{
				count = i;
				break;
			}
		}
		
		links.get(count).click();
		
		//User types in their current, new, and confirm passwords
        driver.findElement(By.name("oldPass")).sendKeys("pw");
        driver.findElement(By.name("newPass")).sendKeys("pass1");
        driver.findElement(By.name("confirmPass")).sendKeys("pass1");
        driver.findElement(By.name("oldPass")).submit();

        assertTrue(driver.getPageSource().contains("Password Changed"));
		assertLogged(TransactionType.PASSWORD_CHANGE, 1L, 0, "");
		
		//User logs out
		links = driver.findElements(By.tagName("a"));
		links.get(count - 1).click();		
		
		//User can't log in with old password, but can with new one
		driver = (HtmlUnitDriver)login("1", "pw");
		assertEquals("iTrust - Login", driver.getTitle());
		driver = (HtmlUnitDriver)login("1", "pass1");
		assertEquals("iTrust - Patient Home", driver.getTitle());
	}

/*	public void testChangePassword_Acceptance_Long() throws Exception {
		//Patient1 logs into iTrust
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		//User goes to change password
		wr = wr.getLinkWith("Change Password").click();
		
		//User types in their current, new, and confirm passwords
		WebForm wf = wr.getFormWithID("mainForm");
		wf.setParameter("oldPass", "pw");
		wf.setParameter("newPass", "pass12345abcde");
		wf.setParameter("confirmPass", "pass12345abcde");
		
		//User submits password change. Change logged
		wf.submit(wf.getSubmitButtons()[0]);
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Password Changed"));
		assertLogged(TransactionType.PASSWORD_CHANGE, 1L, 0, "");
		
		//User logs out
		wr = wr.getLinkWith("Logout").click();
		
		//User can't log in with old password, but can with new one
		wc = login("1", "pw");
		assertEquals("iTrust - Login", wc.getCurrentPage().getTitle());
		wc = login("1", "pass12345abcde");
		assertEquals("iTrust - Patient Home", wc.getCurrentPage().getTitle());
	}
	
	public void testChangePassword_Invalid_Length() throws Exception {
		//Patient1 logs into iTrust
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		//User goes to change password
		wr = wr.getLinkWith("Change Password").click();
		
		//User types in their current, new, and confirm passwords
		WebForm wf = wr.getFormWithID("mainForm");
		wf.setParameter("oldPass", "pw");
		wf.setParameter("newPass", "pas1");
		wf.setParameter("confirmPass", "pas1");
		
		//User submits password change. Change logged
		wf.submit(wf.getSubmitButtons()[0]);
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Invalid password"));
		assertLogged(TransactionType.PASSWORD_CHANGE_FAILED, 1L, 0, "");
		
		//User logs out
		wr = wr.getLinkWith("Logout").click();
		
		//User can log in with old password, but can't with new one
		wc = login("1", "pas1");
		assertEquals("iTrust - Login", wc.getCurrentPage().getTitle());
		wc = login("1", "pw");
		assertEquals("iTrust - Patient Home", wc.getCurrentPage().getTitle());
	}
	
	public void testChangePassword_Invalid_Characters() throws Exception {
		//Patient1 logs into iTrust
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		//User goes to change password
		wr = wr.getLinkWith("Change Password").click();
		
		//User types in their current, new, and confirm passwords
		WebForm wf = wr.getFormWithID("mainForm");
		wf.setParameter("oldPass", "pw");
		wf.setParameter("newPass", "password");
		wf.setParameter("confirmPass", "password");
		
		//User submits password change. Change logged
		wf.submit(wf.getSubmitButtons()[0]);
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Invalid password"));
		assertLogged(TransactionType.PASSWORD_CHANGE_FAILED, 1L, 0, "");
		
		//User logs out
		wr = wr.getLinkWith("Logout").click();
		
		//User can log in with old password, but can't with new one
		wc = login("1", "password");
		assertEquals("iTrust - Login", wc.getCurrentPage().getTitle());
		wc = login("1", "pw");
		assertEquals("iTrust - Patient Home", wc.getCurrentPage().getTitle());
	}
	
	public void testChangePassword_Failed_Confirmation() throws Exception {
		//Patient1 logs into iTrust
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		//User goes to change password
		wr = wr.getLinkWith("Change Password").click();
		
		//User types in their current, new, and confirm passwords
		WebForm wf = wr.getFormWithID("mainForm");
		wf.setParameter("oldPass", "pw");
		wf.setParameter("newPass", "pass1");
		wf.setParameter("confirmPass", "pass2");
		
		//User submits password change. Change logged
		wf.submit(wf.getSubmitButtons()[0]);
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Invalid password"));
		assertLogged(TransactionType.PASSWORD_CHANGE_FAILED, 1L, 0, "");
		
		//User logs out
		wr = wr.getLinkWith("Logout").click();
		
		//User can log in with old password, but can't with new one
		wc = login("1", "pas1");
		assertEquals("iTrust - Login", wc.getCurrentPage().getTitle());
		wc = login("1", "pw");
		assertEquals("iTrust - Patient Home", wc.getCurrentPage().getTitle());
	}
	
	public void testChangePassword_Invalid_Password() throws Exception {
		//Patient1 logs into iTrust
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		//User goes to change password
		wr = wr.getLinkWith("Change Password").click();
		
		//User types in their current, new, and confirm passwords
		WebForm wf = wr.getFormWithID("mainForm");
		wf.setParameter("oldPass", "password");
		wf.setParameter("newPass", "pass1");
		wf.setParameter("confirmPass", "pass1");
		
		//User submits password change. Change logged
		wf.submit(wf.getSubmitButtons()[0]);
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Invalid password"));
		assertLogged(TransactionType.PASSWORD_CHANGE_FAILED, 1L, 0, "");
		
		//User logs out
		wr = wr.getLinkWith("Logout").click();
		
		//User can log in with old password, but can't with new one
		wc = login("1", "pass1");
		assertEquals("iTrust - Login", wc.getCurrentPage().getTitle());
		wc = login("1", "pw");
		assertEquals("iTrust - Patient Home", wc.getCurrentPage().getTitle());
	}
	*/

}
