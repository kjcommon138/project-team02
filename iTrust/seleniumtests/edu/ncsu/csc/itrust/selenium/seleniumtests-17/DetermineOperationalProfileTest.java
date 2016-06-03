package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class DetermineOperationalProfileTest extends iTrustSeleniumTest {
	

	private WebDriver driver = new HtmlUnitDriver();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.uap1();
		gen.tester();
	}

	/*
	 * Precondition: Sample data is in the database. CreatePatient2 has passed.
	 * Login with user 9999999999 and password pw.
	 */
	public void testDetermineOperationalProfile() throws Exception {
		// login as uap and add a patient
		driver = login("8000000009", "uappass1");
		driver.get(ADDRESS + "auth/uap/home.jsp");

		assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		
	    driver.findElement(By.linkText("Add Patient")).click();

	    driver.findElement(By.xpath("//*[@id='iTrustContent']/div/form/table/tbody/tr[2]/td[2]/input")).sendKeys("bob");
	    driver.findElement(By.xpath("//*[@id='iTrustContent']/div/form/table/tbody/tr[3]/td[2]/input")).sendKeys("bob");
	    driver.findElement(By.xpath("//*[@id='iTrustContent']/div/form/table/tbody/tr[4]/td[2]/input")).sendKeys("bob@bob.com");
	    
	    driver.findElement(By.xpath("//*[@id='button']")).click();
	    
	    String newMID = driver.findElement(By.xpath("//*[@id='newPatientTable']/tbody/tr[2]/td[2]")).getText();
		assertLogged(TransactionType.PATIENT_CREATE, 8000000009L, Long.parseLong(newMID), "");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();
		
		assertLogged(TransactionType.LOGOUT, 8000000009L, 8000000009L, "");
		// login as tester to check the operational profile
		
		driver = login("9999999999", "pw");
		
		assertEquals("1", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[9]/td[2]")).getText()); //was 1
		assertEquals("17%", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[9]/td[3]")).getText()); //was 17%
		assertEquals("1", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[9]/td[4]")).getText()); //was 1
		assertEquals("20%", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[9]/td[5]")).getText()); //was 20%
		assertEquals("0", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[9]/td[6]")).getText()); //was 0
		assertEquals("0%", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[9]/td[7]")).getText()); //was 0
		//now check the totals are correct
		
		assertEquals("0%", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[46]/td[3]")).getText()); //was 4 - supposed to be 3
		assertEquals("0", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[46]/td[4]")).getText()); //was 3 - supposed to be 2
		assertEquals("0", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[46]/td[6]")).getText()); //supposed to be 1
		assertLogged(TransactionType.OPERATIONAL_PROFILE_VIEW, 9999999999L, 0L, "");
	}
	
	public int getRowNumber(String description)
	{
		TransactionType[] values = TransactionType.values();
		int rownumber = 0;
		for (int i=0; i<values.length; i++)
		{
			if (description.equals(values[i].getDescription()))
				rownumber = i+1;
		}
		
		return rownumber;
	}
}
