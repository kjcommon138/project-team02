package edu.ncsu.csc.itrust.selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ImmunizationUseCasesTest extends iTrustSeleniumTest {
	private HtmlUnitDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		driver = new HtmlUnitDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void testDocumentAndViewImmunizations() throws Exception {
		boolean check1 = false;
		boolean check2 = false;
		driver.setJavascriptEnabled(false);
		driver = (HtmlUnitDriver) login("9000000003", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000003L, 0L, "");
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[4]/div/h2")).click();
	    driver.findElement(By.linkText("Office Visit Reminders")).click();
	    assertEquals("iTrust - Visit Reminders", driver.getTitle());
		// Select "Immunization Needers"
		new Select(driver.findElement(By.id("ReminderType"))).selectByVisibleText("Immunization Needers");
		// Select "Get Reminders"
		driver.findElement(By.id("getReminders")).click();
		// Check for content
		List<WebElement> tableRow = driver.findElements(By.xpath("//*[@id=\"iTrustContent\"]/div/table[1]/tbody/tr"));
		List<WebElement> tableColumn = driver.findElements(By.xpath("//*[@id=\"iTrustContent\"]/div/table[1]/tbody/tr/td"));
		assertEquals(4, tableRow.size());
		assertEquals(6, tableColumn.size());
		if ("Patient Information"
				.equals(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/table[1]/tbody/tr[1]/th")).getText())) {
			if ("Bowser Koopa"
					.equals(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/table[1]/tbody/tr[2]/td[2]")).getText())) {
				assertEquals(
						"Needs Immunization: 90371 Hepatitis B (birth), 90681 Rotavirus (6 weeks), 90696 Diphtheria, Tetanus, Pertussis (6 weeks), 90645 Haemophilus influenzae (6 weeks), 90669 Pneumococcal (6 weeks), 90712 Poliovirus (6 weeks), 90707 Measles, Mumps, Rubekka (12 months), 90396 Varicella (12 months), 90633 Hepatits A (12 months)",
						driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/table[1]/tbody/tr[4]/td[2]")).getText());
				check1 = true;
			}
		}
		
		if ("Patient Information"
				.equals(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/table[2]/tbody/tr[1]/th")).getText())) {
			if ("Princess Peach"
					.equals(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/table[2]/tbody/tr[2]/td[2]/a")).getText())) {
				assertEquals(
						"Needs Immunization: 90371 Hepatitis B (birth), 90681 Rotavirus (6 weeks), 90696 Diphtheria, Tetanus, Pertussis (6 weeks), 90645 Haemophilus influenzae (6 weeks), 90669 Pneumococcal (6 weeks), 90712 Poliovirus (6 weeks), 90707 Measles, Mumps, Rubekka (12 months), 90396 Varicella (12 months), 90633 Hepatits A (12 months)",
						driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/table[2]/tbody/tr[4]/td[2]")).getText());
				check2 = true;
			}
		}
		
		assertTrue(check1);
		assertTrue(check2);
		assertLogged(TransactionType.PATIENT_REMINDERS_VIEW, 9000000003L, 0L, "");
	}
	
	public void testViewImmunizationRecord() throws Exception {
		driver.setJavascriptEnabled(false);
		driver = (HtmlUnitDriver) login("6", "pw");
		assertLogged(TransactionType.HOME_VIEW, 6L, 0L, "");
		driver.findElement(By.xpath("//*[@id=\"records-menu\"]/ul/li[1]/a")).click();
	    assertEquals("iTrust - View My Records", driver.getTitle());
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 6L, 6L, "");
		
		List<WebElement> CTPCodes = driver.findElements(By.xpath("//*[@id=\"iTrustContent\"]/table[5]/tbody/tr/td[1]"));
		assertEquals("90649", CTPCodes.get(1).getText());
		assertEquals("90649", CTPCodes.get(2).getText());
		assertEquals("90707", CTPCodes.get(3).getText());
		assertEquals("90396", CTPCodes.get(4).getText());
		assertEquals("90633", CTPCodes.get(5).getText());
		assertEquals("90645", CTPCodes.get(6).getText());
		assertEquals("90707", CTPCodes.get(7).getText());
		assertEquals("90396", CTPCodes.get(8).getText());
		assertEquals("90633", CTPCodes.get(9).getText());
		assertEquals("90696", CTPCodes.get(10).getText());
		assertEquals("90669", CTPCodes.get(11).getText());
		assertEquals("90712", CTPCodes.get(12).getText());
		assertEquals("90681", CTPCodes.get(13).getText());
		assertEquals("90696", CTPCodes.get(14).getText());
		assertEquals("90645", CTPCodes.get(15).getText());
		assertEquals("90669", CTPCodes.get(16).getText());
		assertEquals("90712", CTPCodes.get(17).getText());
		assertEquals("90681", CTPCodes.get(18).getText());
		assertEquals("90696", CTPCodes.get(19).getText());
		assertEquals("90645", CTPCodes.get(20).getText());
		assertEquals("90669", CTPCodes.get(21).getText());
		assertEquals("90712", CTPCodes.get(22).getText());
		assertEquals("90371", CTPCodes.get(23).getText());
		assertEquals("90371", CTPCodes.get(24).getText());
	}
	
	public void testViewImmunizationRecord2() throws Exception {
		driver.setJavascriptEnabled(false);
		driver = (HtmlUnitDriver) login("2", "pw");
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		driver.findElement(By.xpath("//*[@id=\"records-menu\"]/ul/li[1]/a")).click();
	    assertEquals("iTrust - View My Records", driver.getTitle());
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
		assertEquals("No Data", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/table[5]/tbody/tr[3]/td")).getText());
	}
	
	public void testDocumentImmunization() throws Exception {
		driver = (HtmlUnitDriver) login("9000000000", "pw");
		driver.setJavascriptEnabled(true);
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		driver.findElement(By.xpath("//*[@id=\"iTrustMenu\"]/div/div[4]/div[1]/h2")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.xpath("//*[@id=\"searchBox\"]")).sendKeys("6");
		driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input")).click();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		driver.findElement(By.linkText("07/10/2004")).click();
		assertEquals("90649", driver.findElement(By.xpath("//*[@id=\"immunizationsTable\"]/tbody/tr[3]/td[1]")).getText());
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 6L, "Office visit");
	}
	
	public void testDocumentImmunization2() throws Exception {
		driver = (HtmlUnitDriver) login("9000000000", "pw");
		driver.setJavascriptEnabled(true);
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		driver.findElement(By.xpath("//*[@id=\"iTrustMenu\"]/div/div[4]/div[1]/h2")).click();
		driver.findElement(By.linkText("Document Office Visit")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		driver.findElement(By.xpath("//*[@id=\"searchBox\"]")).sendKeys("7");
		driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input")).click();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		driver.findElement(By.linkText("05/10/2006")).click();
		assertEquals("90696", driver.findElement(By.xpath("//*[@id=\"immunizationsTable\"]/tbody/tr[3]/td[1]")).getText());
		driver.findElement(By.xpath("//*[@id=\"immunizationsTable\"]/tbody/tr[3]/td[3]/a")).click();
		assertEquals("No immunizations on record", driver.findElement(By.xpath("//*[@id=\"immunizationsTable\"]/tbody/tr[3]/td[1]")).getText());
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 7L, "Office visit");
	}
	
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
