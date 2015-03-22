package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;
 
public class CreateHCPTest extends iTrustSeleniumTest {
 
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
		gen.hcp0();
		gen.cptCodes();
	}
	
	/*
	 * Authenticate admin 90000000001
	 * Choose Add HCP option
	 * Physican type not currently implemented
	 * [Role: Licensed Physician]
	 * [Enabled: true]
	 * Last name: Williams
	 * First name: Laurie
	 * Email: laurie@ncsu.edu
	 * Street address 1: 900 Main Campus Dr
	 * Street address 2: BOX 2509
	 * City: Raleigh
	 * State: NC
	 * Zip code: 27606-1234
	 * Phone: 919-100-1000
	 */
	public void testCreateValidHCP() throws Exception {
		WebDriver driver = login("9000000001", "pw");
		
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		
		driver.findElement(By.linkText("Add HCP")).click();

		assertEquals("iTrust - Add HCP", driver.getTitle());

		driver.findElement(By.name("firstName")).sendKeys("Laurie");
		driver.findElement(By.name("lastName")).sendKeys("Williams");
		driver.findElement(By.name("email")).sendKeys("laurie@ncsu.edu");
		driver.findElement(By.name("email")).submit();
		
		String newMID = driver.findElement(By.className("fTable")).findElements(By.cssSelector("td")).get(1).getText();
		
		driver.findElement(By.partialLinkText("Continue")).click();

		assertEquals("iTrust - Edit Personnel", driver.getTitle());

		driver.findElement(By.name("streetAddress1")).sendKeys("900 Main Campus Dr");
		driver.findElement(By.name("streetAddress2")).sendKeys("Box 2509");
		driver.findElement(By.name("city")).sendKeys("Raleigh");
		Select select = new Select(driver.findElement(By.name("state")));
		select.selectByValue("NC");
		driver.findElement(By.name("zip")).sendKeys("27606-1234");
		driver.findElement(By.name("phone")).sendKeys("919-100-1000");
		driver.findElement(By.name("phone")).submit();
		
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.LHCP_CREATE, 9000000001L, Long.parseLong(newMID), "");
	}
	
	public void testEditValidPersonnel() throws Exception {
		WebDriver driver = login("9000000001", "pw");
		
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		
		driver.findElement(By.linkText("Edit Personnel")).click();

		assertEquals("iTrust - Please Select a Personnel", driver.getTitle());

		driver.findElement(By.name("FIRST_NAME")).sendKeys("Kelly");
		driver.findElement(By.name("LAST_NAME")).sendKeys("Doctor");
		driver.findElement(By.name("FIRST_NAME")).submit();

		driver.findElement(By.cssSelector("input[value='9000000000']")).submit();

		driver.findElement(By.name("city")).clear();
		driver.findElement(By.name("city")).sendKeys("Brooklyn");
		driver.findElement(By.name("city")).submit();
		
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.LHCP_EDIT, 9000000001L, 9000000000L, "");
		
		driver.findElement(By.linkText("Edit Personnel")).click();
		
		assertEquals("iTrust - Please Select a Personnel", driver.getTitle());
	}
	
	public void testEditHospitalAssignments() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebDriver driver = login("9000000001", "pw");
		
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		
		driver.findElement(By.linkText("Edit HCP Assignment to Hospital")).click();

		assertEquals("iTrust - Please Select a Personnel", driver.getTitle());

		driver.findElement(By.name("FIRST_NAME")).sendKeys("Kelly");
		driver.findElement(By.name("LAST_NAME")).sendKeys("Doctor");
		driver.findElement(By.name("FIRST_NAME")).submit();

		driver.findElement(By.cssSelector("input[value='9000000000']")).submit();

		assertEquals("iTrust - Hospital Staffing Assignments", driver.getTitle());
		
		driver.findElement(By.linkText("Assign")).click();

		assertTrue(driver.getPageSource().contains("HCP has been assigned"));
		assertLogged(TransactionType.LHCP_ASSIGN_HOSPITAL, 9000000001L, 9000000000L, "");
		
		driver.findElement(By.linkText("Unassign")).click();
		
		assertTrue(driver.getPageSource().contains("HCP has been unassigned"));
		assertLogged(TransactionType.LHCP_REMOVE_HOSPITAL, 9000000001L, 9000000000L, "");
	}
}