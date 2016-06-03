package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class DrugInteractionTest extends iTrustSeleniumTest {
	
	private WebDriver driver = new HtmlUnitDriver();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	/*
	 * Authenticate admin 90000000001
	 * Choose "Edit ND Codes"
	 * Choose "Edit Interactions"
	 * Choose "Adefovir" as one drug
	 * Choose "Aspirin" as the other drug
	 * Enter "May increase the risk and severity of nephrotoxicity due to additive effects on the kidney."
	 * Click submit
	 */
	public void testRecordDrugInteraction() throws Exception {
		// login
		driver = login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");

		// click on Edit ND Codes
		driver.findElement(By.xpath("//*[@id=\"edit-menu\"]/ul/li[5]/a")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		
		/* The rest of this test doesn't work with HTMLUnitDriver. Explanation on github.
			// The following click does not work.
		
		// Click Add Interaction
		driver.findElement(By.xpath("//*[@id=\"editInt\"]")).click();//not clicking the add interaction button
		assertEquals("iTrust - Edit ND Code Interactions", driver.getTitle());
		
		
		// Add the drug Interaction information
		Select select = new Select(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table/tbody/tr/td[1]/select")));
		select.selectByVisibleText("Adefovir");
		select = new Select(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table/tbody/tr/td[2]/select")));
		select.selectByVisibleText("Aspirin");
		driver.findElement(By.xpath("//*[@id=\"description\"]")).sendKeys("May increase the risk and severity of nephrotoxicity due to additive effects on the kidney.");
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/input[2]")).click();
		
		assertTrue(driver.getPageSource().contains("Interaction recorded successfully"));
		assertLogged(TransactionType.DRUG_INTERACTION_ADD, 9000000001L, 0L, "");
		*/
	}
	
	/*
	 * Authenticate admin 90000000001
	 * Choose "Edit ND Codes"
	 * Choose "Tetracycline"
	 * Click delete interaction
	 */
	public void testDeleteDrugInteraction() throws Exception {
		// login
		driver = login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");

		// click on Edit ND Codes
		driver.findElement(By.xpath("//*[@id=\"edit-menu\"]/ul/li[5]/a")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		
		// Click Tetracycline
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[5]/td[2]/a")).click();
		assertTrue(driver.getPageSource().contains("Forms an insoluble complex that is poorly absorbed from the gastrointestinal tract."));
		/* The rest of this test doesn't work with HTMLUnitDriver. Explanation on github.
		     //The following click does not work. 
		driver.findElement(By.xpath("//*[@id=\"delete\"]")).click(); //the drug interaction was not deleted
		assertTrue(driver.getPageSource().contains("Interaction deleted successfully"));
		assertLogged(TransactionType.DRUG_INTERACTION_DELETE, 9000000001L, 0L, "Drug");
		*/
	}
	
	/*
	 * Authenticate admin 90000000001
	 * Choose "Edit ND Codes"
	 * Choose "Edit Interactions"
	 * Choose "Adefovir" as both drug1 and drug2
	 * Enter "Mixing this drug with itself will cause the person taking it to implode."
	 * Click submit
	 */
	public void testRecordDrugInteractionSameDrugs() throws Exception {
		// login
		driver = login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");

		// click on Edit ND Codes
		driver.findElement(By.xpath("//*[@id=\"edit-menu\"]/ul/li[5]/a")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		/* The rest of this test doesn't work with HTMLUnitDriver. Explanation on github.
		      //The following click doesn't work
		// Click Add Interaction
		driver.findElement(By.xpath("//*[@id=\"editInt\"]")).click();//not clicking the add interaction button
		assertEquals("iTrust - Edit ND Code Interactions", driver.getTitle());
		
		// Add the same drug
		Select select = new Select(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table/tbody/tr/td[1]/select")));
		select.selectByVisibleText("Adefovir");
		select = new Select(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table/tbody/tr/td[2]/select")));
		select.selectByVisibleText("Adefovir");
		driver.findElement(By.xpath("//*[@id=\"description\"]")).sendKeys("May increase the risk and severity of nephrotoxicity due to additive effects on the kidney.");
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/input[2]")).click();
		
		assertTrue(driver.getPageSource().contains("Interactions can only be recorded between two different drugs"));
		*/
	}
	
	public void testAddNewOverrideReason() throws Exception {
		// login
		driver = login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		// Click on Edit OR Codes
		driver.findElement(By.xpath("//*[@id=\"edit-menu\"]/ul/li[8]/a")).click();
		assertEquals("iTrust - Maintain Override Reason Codes", driver.getTitle());
		
		// Add codes and information
		driver.findElement(By.xpath("//*[@id=\"code\"]")).sendKeys("22222");
		driver.findElement(By.xpath("//*[@id=\"description\"]")).sendKeys("Interaction not applicable to this patient");
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/input[4]")).click();
		
		// verify change
		assertTrue(driver.getCurrentUrl().toString().contains("auth/admin/editORCodes"));
		assertTrue(driver.getPageSource().contains("Success: 22222 - Interaction not applicable to this patient added"));
		assertLogged(TransactionType.OVERRIDE_CODE_ADD, 9000000001L, 0L, "");
	}
	
	public void testEditOverrideReason() throws Exception {
		// login
		driver = login("9000000001", "pw");
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		// Click on Edit OR Codes
		driver.findElement(By.xpath("//*[@id=\"edit-menu\"]/ul/li[8]/a")).click();
		assertEquals("iTrust - Maintain Override Reason Codes", driver.getTitle());
		
		// Update codes and information
		driver.findElement(By.xpath("//*[@id=\"code\"]")).sendKeys("00001");
		driver.findElement(By.xpath("//*[@id=\"description\"]")).sendKeys("Alerted interaction not super duper significant");
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/input[5]")).click();
		
		// verify change
		assertTrue(driver.getCurrentUrl().toString().contains("auth/admin/editORCodes"));
		assertTrue(driver.getPageSource().contains("Success"));
		assertTrue(driver.getPageSource().contains("Alerted interaction not super duper significant"));
		assertLogged(TransactionType.OVERRIDE_CODE_EDIT, 9000000001L, 0L, "");
	}
}
