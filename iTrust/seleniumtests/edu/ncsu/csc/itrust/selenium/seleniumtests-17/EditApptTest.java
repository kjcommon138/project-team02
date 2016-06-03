package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class EditApptTest extends iTrustSeleniumTest {
	
	private WebDriver driver = new HtmlUnitDriver();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testEditAppt() throws Exception {
		// login hcp
		driver = login("9000000000", "pw");
		assertTrue(driver.getTitle().equals("iTrust - HCP Home"));
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		// Click View My Appointments
		driver.findElement(By.xpath("//*[@id='appt-menu']/ul/li[2]/a")).click();
		assertLogged(TransactionType.APPOINTMENT_ALL_VIEW, 9000000000L, 0L, "");
		
		// Click First Andy Programmer
		driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[4]/td[6]/a")).click();// fails here
		assertTrue(driver.getPageSource().contains("Andy Programmer"));
		
		// Add a new comment and click the change button
		driver.findElement(By.xpath("//*[@id='mainForm']/table/tbody/tr[2]/td/textarea")).clear();
		driver.findElement(By.xpath("//*[@id='mainForm']/table/tbody/tr[2]/td/textarea")).sendKeys("New comment!");
		/* The rest of this test doesn't work with HTMLUnitDriver. Explanation on github.
		
		
		driver.findElement(By.xpath("//*[@id='changeButton']")).click();		
		
		// Make sure editing the appt worked
		assertTrue(driver.getPageSource().contains("Success: Appointment changed"));
		assertLogged(TransactionType.APPOINTMENT_EDIT, 9000000000L, 2L, "");
		*/
	}
	
	/**
	 * testSetPassedDate
	 * @throws Exception
	 */
	public void testSetPassedDate() throws Exception {
		// login hcp
		driver = login("9000000000", "pw");
		assertTrue(driver.getTitle().equals("iTrust - HCP Home"));
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		// Click View My Appointments
		driver.findElement(By.xpath("//*[@id='appt-menu']/ul/li[2]/a")).click();
		assertLogged(TransactionType.APPOINTMENT_ALL_VIEW, 9000000000L, 0L, "");
		/* The rest of this test doesn't work with HTMLUnitDriver. Explanation on github.
		
		// Click First Andy Programmer
		driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[4]/td[6]/a")).click();//fails here, same spot as above
		assertTrue(driver.getPageSource().contains("Andy Programmer"));
		
		// Enter an invalid date range and click the change button
		driver.findElement(By.xpath("//*[@id='mainForm']/div/table/tbody/tr[4]/td/input[1]")).sendKeys("10/10/2009");
		driver.findElement(By.xpath("//*[@id='changeButton']")).click();
		
		// Make sure the date was not changed
		assertTrue(driver.getPageSource().contains("The scheduled date of this appointment")); 
		assertTrue(driver.getPageSource().contains("has already passed."));
		assertNotLogged(TransactionType.APPOINTMENT_EDIT, 9000000000L, 100L, "");
		*/
	}
	
	/**
	 * testRemoveAppt
	 * @throws Exception
	 */
	public void testRemoveAppt() throws Exception {
		// login hcp
		driver = login("9000000000", "pw");
		assertTrue(driver.getTitle().equals("iTrust - HCP Home"));
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		/* The rest of this test doesn't work with HTMLUnitDriver. Explanation on github.
		
		// Click View My Appointments
		driver.findElement(By.xpath("//*[@id='appt-menu']/ul/li[2]/a")).click();
		assertLogged(TransactionType.APPOINTMENT_ALL_VIEW, 9000000000L, 0L, "");
		
		// Click first Andy Programmer
		driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[4]/td[6]/a")).click();//same spot again
		assertTrue(driver.getPageSource().contains("Andy Programmer"));
		
		// Click the remove button in order to remove the appt
		driver.findElement(By.xpath("//*[@id='removeButton']")).click();
		
		// Make sure the appt was removed
		assertTrue(driver.getPageSource().contains("Success: Appointment removed"));
		assertLogged(TransactionType.APPOINTMENT_REMOVE, 9000000000L, 100L, "");
		*/
	}
}
