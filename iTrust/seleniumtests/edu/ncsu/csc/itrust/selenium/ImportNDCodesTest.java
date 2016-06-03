package edu.ncsu.csc.itrust.selenium;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class ImportNDCodesTest extends iTrustSeleniumTest {
	private HtmlUnitDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.admin1();
		driver = new HtmlUnitDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	/*
	 * Test that drugs can be updated as a list
	 */
	public void testImportDrugs() throws Exception {
		// login admin
		driver = (HtmlUnitDriver) login("9000000001", "pw");
		driver.setJavascriptEnabled(false);
		assertEquals("iTrust - Admin Home", driver.getTitle());
		// click on Edit ND Codes
		driver.findElement(By.xpath("//*[@id=\"iTrustMenu\"]/div/div[2]/div[1]/h2")).click();
		driver.findElement(By.linkText("Edit ND Codes")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		driver.findElement(By.xpath("//*[@id=\"import\"]")).click();
		assertEquals("iTrust - Import ND Codes", driver.getTitle());
		File f = tempNDCFile();
		driver.findElement(By.name("fileIn")).sendKeys(f.getAbsolutePath());
		new Select(driver.findElement(By.name("strategy"))).selectByVisibleText("Ignore Duplicate ND Codes");
		driver.findElement(By.name("import")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		assertEquals("05730-150", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[3]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[3]/td[2]/a")).getText().contains("ADVIL"));
		assertEquals("10544-591", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[4]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[4]/td[2]/a")).getText().contains("OxyContin"));
		assertEquals("11523-7197", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[5]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[5]/td[2]/a")).getText().contains("Claritin"));
		assertEquals("50458-513", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[6]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[6]/td[2]/a")).getText().contains("TYLENOL with Codeine"));
		//delete temp file
		f.delete();
	}
	
	/*
	 * Test that a new list of drugs can update an existing list
	 */
	public void testImportDrugs_UpdateDupes() throws Exception {
		// login admin
		driver = (HtmlUnitDriver) login("9000000001", "pw");
		driver.setJavascriptEnabled(false);
		assertEquals("iTrust - Admin Home", driver.getTitle());
		// click on Edit ND Codes
		driver.findElement(By.xpath("//*[@id=\"iTrustMenu\"]/div/div[2]/div[1]/h2")).click();
		driver.findElement(By.linkText("Edit ND Codes")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		driver.findElement(By.xpath("//*[@id=\"import\"]")).click();
		assertEquals("iTrust - Import ND Codes", driver.getTitle());
		File f = tempNDCFile();
		driver.findElement(By.name("fileIn")).sendKeys(f.getAbsolutePath());
		new Select(driver.findElement(By.name("strategy"))).selectByVisibleText("Ignore Duplicate ND Codes");
		driver.findElement(By.name("import")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		assertEquals("05730-150", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[3]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[3]/td[2]/a")).getText().contains("ADVIL"));
		assertEquals("10544-591", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[4]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[4]/td[2]/a")).getText().contains("OxyContin"));
		assertEquals("11523-7197", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[5]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[5]/td[2]/a")).getText().contains("Claritin"));
		assertEquals("50458-513", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[6]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[6]/td[2]/a")).getText().contains("TYLENOL with Codeine"));
		//delete temp file
		f.delete();
		
		
		driver.findElement(By.xpath("//*[@id=\"import\"]")).click();
		assertEquals("iTrust - Import ND Codes", driver.getTitle());
		f = tempNDCFile2();
		driver.findElement(By.name("fileIn")).sendKeys(f.getAbsolutePath());
		new Select(driver.findElement(By.name("strategy"))).selectByVisibleText("Update Duplicate ND Codes");
		driver.findElement(By.name("import")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		assertEquals("05730-150", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[3]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[3]/td[2]/a")).getText().contains("ADVIL NEW"));
		assertEquals("10544-591", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[4]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[4]/td[2]/a")).getText().contains("OxyContin"));
		assertEquals("11523-7197", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[5]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[5]/td[2]/a")).getText().contains("Claritin"));
		assertEquals("50458-513", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[6]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[6]/td[2]/a")).getText().contains("TYLENOL with Coke"));
		//delete temp file
		f.delete();
	}
	
	/*
	 * Test the return button
	 */
	public void testImportDrugs_IgnoreDupes() throws Exception {
		// login admin
		driver = (HtmlUnitDriver) login("9000000001", "pw");
		driver.setJavascriptEnabled(false);
		assertEquals("iTrust - Admin Home", driver.getTitle());
		// click on Edit ND Codes
		driver.findElement(By.xpath("//*[@id=\"iTrustMenu\"]/div/div[2]/div[1]/h2")).click();
		driver.findElement(By.linkText("Edit ND Codes")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		driver.findElement(By.xpath("//*[@id=\"import\"]")).click();
		assertEquals("iTrust - Import ND Codes", driver.getTitle());
		File f = tempNDCFile();
		driver.findElement(By.name("fileIn")).sendKeys(f.getAbsolutePath());
		new Select(driver.findElement(By.name("strategy"))).selectByVisibleText("Ignore Duplicate ND Codes");
		driver.findElement(By.name("import")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		assertEquals("05730-150", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[3]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[3]/td[2]/a")).getText().contains("ADVIL"));
		assertEquals("10544-591", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[4]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[4]/td[2]/a")).getText().contains("OxyContin"));
		assertEquals("11523-7197", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[5]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[5]/td[2]/a")).getText().contains("Claritin"));
		assertEquals("50458-513", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[6]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[6]/td[2]/a")).getText().contains("TYLENOL with Codeine"));
		//delete temp file
		f.delete();
		
		
		driver.findElement(By.xpath("//*[@id=\"import\"]")).click();
		assertEquals("iTrust - Import ND Codes", driver.getTitle());
		f = tempNDCFile2();
		driver.findElement(By.name("fileIn")).sendKeys(f.getAbsolutePath());
		new Select(driver.findElement(By.name("strategy"))).selectByVisibleText("Ignore Duplicate ND Codes");
		driver.findElement(By.name("import")).click();
		assertEquals("iTrust - Maintain ND Codes", driver.getTitle());
		assertEquals("05730-150", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[3]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[3]/td[2]/a")).getText().contains("ADVIL"));
		assertEquals("10544-591", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[4]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[4]/td[2]/a")).getText().contains("OxyContin"));
		assertEquals("11523-7197", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[5]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[5]/td[2]/a")).getText().contains("Claritin"));
		assertEquals("50458-513", driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[6]/td[1]")).getText());
		assertTrue(driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/div/form/table[1]/tbody/tr[6]/td[2]/a")).getText().contains("TYLENOL with Codeine"));
		//delete temp file
		f.delete();
	}
	
	private File tempNDCFile() throws IOException {
		File f = File.createTempFile("ndcodes", null);
		FileWriter fw = new FileWriter(f);
		fw.write(
				"0573-0150	HUMAN OTC DRUG	ADVIL		IBUPROFEN	TABLET, COATED	ORAL	19840518		NDA	NDA018989	Pfizer Consumer Healthcare	IBUPROFEN	200	mg/1	Nonsteroidal Anti-inflammatory Drug [EPC], Cyclooxygenase Inhibitors [MoA], Nonsteroidal Anti-inflammatory Compounds [Chemical/Ingredient]	\n" +
				"50458-513	HUMAN PRESCRIPTION DRUG	TYLENOL with Codeine		ACETAMINOPHEN AND CODEINE PHOSPHATE	TABLET	ORAL	19770817		ANDA	ANDA085055	Janssen Pharmaceuticals, Inc.	ACETAMINOPHEN; CODEINE PHOSPHATE	300; 30	mg/1; mg/1		CIII\n" +
				"10544-591	HUMAN PRESCRIPTION DRUG	OxyContin		OXYCODONE HYDROCHLORIDE	TABLET, FILM COATED, EXTENDED RELEASE	ORAL	20100126		NDA	NDA020553	Blenheim Pharmacal, Inc.	OXYCODONE HYDROCHLORIDE	10	mg/1	Opioid Agonist [EPC], Full Opioid Agonists [MoA]	CII\n" +
				"11523-7197	HUMAN OTC DRUG	Claritin		LORATADINE	SOLUTION	ORAL	20110301		NDA	NDA020641	Schering Plough Healthcare Products Inc.	LORATADINE	5	mg/5mL		\n"
				);
		fw.flush();
		fw.close();
		return f;
	}

	private File tempNDCFile2() throws IOException {
		File f = File.createTempFile("ndcodes2", null);
		FileWriter fw = new FileWriter(f);
		fw.write(
				"0573-0150	HUMAN OTC DRUG	ADVIL NEW		IBUPROFEN	TABLET, COATED	ORAL	19840518		NDA	NDA018989	Pfizer Consumer Healthcare	IBUPROFEN	200	mg/1	Nonsteroidal Anti-inflammatory Drug [EPC], Cyclooxygenase Inhibitors [MoA], Nonsteroidal Anti-inflammatory Compounds [Chemical/Ingredient]	\n" +
				"50458-513	HUMAN PRESCRIPTION DRUG	TYLENOL with Coke		ACETAMINOPHEN AND CODEINE PHOSPHATE	TABLET	ORAL	19770817		ANDA	ANDA085055	Janssen Pharmaceuticals, Inc.	ACETAMINOPHEN; CODEINE PHOSPHATE	300; 30	mg/1; mg/1		CIII\n"
				);
		fw.flush();
		fw.close();
		return f;
	}
	
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
