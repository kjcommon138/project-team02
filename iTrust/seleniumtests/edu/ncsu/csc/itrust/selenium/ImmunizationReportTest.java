package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ImmunizationReportTest extends iTrustSeleniumTest{
	private HtmlUnitDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	public void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		driver = new HtmlUnitDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void testImmunizationReportKindergartenPatient() throws Exception {
		driver = (HtmlUnitDriver) login("300", "pw");
		driver.setJavascriptEnabled(false);
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Immunization Report")).click();
		assertEquals("iTrust - View My Immunization Records", driver.getTitle());
		
		// check table format
		int rowCount = driver.findElements(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr")).size();
		assertEquals(13, rowCount);
		assertEquals("CPT Code",driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[2]/th[1]")).getText());
		assertEquals("Description",driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[2]/th[2]")).getText());
		assertEquals("Date Received",driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[2]/th[3]")).getText());
		assertEquals("HCP",driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[2]/th[4]")).getText());
		assertEquals("CPT Code",driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[12]/th[1]")).getText());
		assertEquals("Description",driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[12]/th[2]")).getText());
		
		//Check the eight immunizations received by Adam Sandler
		// DTP
		assertEquals("90696",driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[3]/td[1]")).getText());
		assertEquals("Diphtheria, Tetanus, Pertussis",driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[3]/td[2]")).getText());
		assertEquals("Dec 31, 2012",driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[3]/td[3]")).getText());
		assertEquals("Kelly Doctor",driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[3]/td[4]")).getText());
		// Polio
		assertEquals("90712", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[4]/td[1]")).getText());
		assertEquals("Poliovirus", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[4]/td[2]")).getText());
		assertEquals("Jan 1, 2013", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[4]/td[3]")).getText());
		assertEquals("Kelly Doctor", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[4]/td[4]")).getText());
		// MMR
		assertEquals("90707", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[5]/td[1]")).getText());
		assertEquals("Measles, Mumps, Rubella", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[5]/td[2]")).getText());
		assertEquals("Jan 1, 2013", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[5]/td[3]")).getText());
		assertEquals("Kelly Doctor", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[5]/td[4]")).getText());
		// Hep B
		assertEquals("90371", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[6]/td[1]")).getText());
		assertEquals("Hepatitis B", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[6]/td[2]")).getText());
		assertEquals("Jan 2, 2013", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[6]/td[3]")).getText());
		assertEquals("Kelly Doctor", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[6]/td[4]")).getText());
		// Varicella
		assertEquals("90396", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[7]/td[1]")).getText());
		assertEquals("Varicella", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[7]/td[2]")).getText());
		assertEquals("Jan 3, 2013", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[7]/td[3]")).getText());
		assertEquals("Kelly Doctor", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[7]/td[4]")).getText());
		// Rotavirus
		assertEquals("90681", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[8]/td[1]")).getText());
		assertEquals("Rotavirus", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[8]/td[2]")).getText());
		assertEquals("Jan 3, 2013", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[8]/td[3]")).getText());
		assertEquals("Kelly Doctor", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[8]/td[4]")).getText());
		// Hep A
		assertEquals("90633", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[9]/td[1]")).getText());
		assertEquals("Hepatitis A", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[9]/td[2]")).getText());
		assertEquals("Jan 3, 2013", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[9]/td[3]")).getText());
		assertEquals("Kelly Doctor", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[9]/td[4]")).getText());
		// Hib
		assertEquals("90645", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[10]/td[1]")).getText());
		assertEquals("Haemophilus influenzae", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[10]/td[2]")).getText());
		assertEquals("Jan 4, 2013", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[10]/td[3]")).getText());
		assertEquals("Kelly Doctor", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[10]/td[4]")).getText());
		
		// No immunizations needed for patient.
		assertEquals("No further immunizations needed", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[13]/td")).getText());
		
		assertLogged(TransactionType.IMMUNIZATION_REPORT_PATIENT_VIEW, 300L, 0L, "");
	}
	
	public void testImmunizationReportSixthGradePatient() throws Exception {
		// Log in as Natalie Portman
		driver = (HtmlUnitDriver) login("301", "pw");
		driver.setJavascriptEnabled(false);
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Immunization Report")).click();
		assertEquals("iTrust - View My Immunization Records", driver.getTitle());
		
		// check table format
		int rowCount = driver.findElements(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr")).size();
		assertEquals(13, rowCount);
		
		// Check the eight immunizations received by Natalie Portman
		// DTP
		assertEquals("90696", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[3]/td[1]")).getText());
		// Polio
		assertEquals("90712", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[4]/td[1]")).getText());
		// MMR
		assertEquals("90707", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[5]/td[1]")).getText());
		// Hep B
		assertEquals("90371", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[6]/td[1]")).getText());
		// Rotavirus
		assertEquals("90681", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[7]/td[1]")).getText());
		// Hep A
		assertEquals("90633", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[8]/td[1]")).getText());
		// Varicella
		assertEquals("90396", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[9]/td[1]")).getText());
		// Hib
		assertEquals("90645", driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/table/tbody/tr[10]/td[1]")).getText());
		
		// Check logging
		assertLogged(TransactionType.IMMUNIZATION_REPORT_PATIENT_VIEW, 301L, 0L, "");
	}
	
	public void testImmunizationReportAdultPatient() throws Exception {
		//Test data for an adult patient viewing his own immunization report.
		//The following received immunizations should be displayed:
			//DTP (Diphtheria, Tetanus, Pertussis)
			//MMR (Measles, Mumps, Rubella)
			//Hepatitis B
		//No required immunizations are displayed.
			//No Polio needed because patient is >=18yrs
		
		driver = (HtmlUnitDriver) login("302", "pw");
		driver.setJavascriptEnabled(false);
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Immunization Report")).click();
		assertEquals("iTrust - View My Immunization Records", driver.getTitle());
		assertEquals(8, driver.findElements(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr")).size());
		
		//Check the three immunizations received by Will Smith
		//DTP
		assertEquals("90696", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[3]/td[1]")).getText());
		//MMR
		assertEquals("90707", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[4]/td[1]")).getText());
		//Hep B
		assertEquals("90371", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[5]/td[1]")).getText());
		
		//Check that no immunizations are required
		assertEquals("No further immunizations needed", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[8]/td[1]")).getText());
		
		//Check logging
		assertLogged(TransactionType.IMMUNIZATION_REPORT_PATIENT_VIEW, 302L, 0L, "");
	}
	
	public void testImmunizationReportNeedImmunziations() throws Exception {
		//Test data for patient with immunizations needed.
		//The following received immunizations should be displayed:
			//Rotavirus
			//Hep B
		//The following required immunizations should be displayed:
			//DTP
			//Polio
			//MMR
			//Hib
			//Varicella
		
		driver = (HtmlUnitDriver) login("303", "pw");
		driver.setJavascriptEnabled(false);
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Immunization Report")).click();
		assertEquals("iTrust - View My Immunization Records", driver.getTitle());
		assertEquals(10, driver.findElements(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr")).size());
		
		//Check the two immunizations received by Julia Roberts
		assertEquals("90681", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[3]/td[1]")).getText());
		assertEquals("90371", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[4]/td[1]")).getText());
		
		//Check the five required immunizations
		assertEquals("90696", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[7]/td[1]")).getText());
		assertEquals("90712", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[8]/td[1]")).getText());
		assertEquals("90707", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[9]/td[1]")).getText());
		assertEquals("90396", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[10]/td[1]")).getText());
		
		//Check logging
		assertLogged(TransactionType.IMMUNIZATION_REPORT_PATIENT_VIEW, 303L, 0L, "");
	}
	
	public void testImmunizationReportPriorDiagnosis() throws Exception {
		//Test data for patient with prior Chicken Pox diagnosis.
		//The following immunizations should be displayed as received:
			//DTP
			//Polio
			//MMR
			//Hep B
			//Hib
			//Rotavirus
		//No required immunizations should be displayed.
			//No Varicella is needed because of prior Chicken Pox diagnosis
		
		driver = (HtmlUnitDriver) login("305", "pw");
		driver.setJavascriptEnabled(false);
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Immunization Report")).click();
		assertEquals("iTrust - View My Immunization Records", driver.getTitle());
		assertEquals(11, driver.findElements(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr")).size());
		
		//Check the six immunizations received by Christina Aguillera
		assertEquals("90681", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[3]/td[1]")).getText());
		assertEquals("90371", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[4]/td[1]")).getText());
		assertEquals("90696", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[5]/td[1]")).getText());
		assertEquals("90712", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[6]/td[1]")).getText());
		assertEquals("90707", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[7]/td[1]")).getText());
		assertEquals("90645", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[8]/td[1]")).getText());
		
		//Check that no immunizations are logged as required.
		assertEquals("No further immunizations needed", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[11]/td[1]")).getText());
		
		//Check logging
		assertLogged(TransactionType.IMMUNIZATION_REPORT_PATIENT_VIEW, 305L, 0L, "");
	}
	
	public void testImmunizationReportOverMaxAge() throws Exception {
		//Test data for patient over the max age for needing a vaccine.
		//No immunizations should be displayed as received.
		//The following required immunizations should be displayed:
			//DTP
			//MMR
			//No Hep B needed because patient is born before July 1994
		
		driver = (HtmlUnitDriver) login("308", "pw");
		driver.setJavascriptEnabled(false);
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Immunization Report")).click();
		assertEquals("iTrust - View My Immunization Records", driver.getTitle());
		assertEquals(7, driver.findElements(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr")).size());
		
		//Check that no immunizations logged as received
		assertEquals("No immunizations received.", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[3]/td[1]")).getText());
		
		//Check the two immunizations required
		//DTP
		assertEquals("90696", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[6]/td[1]")).getText());
		//MMR
		assertEquals("90707", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[7]/td[1]")).getText());
		
		//Check logging
		assertLogged(TransactionType.IMMUNIZATION_REPORT_PATIENT_VIEW, 308L, 0L, "");
	}
	
	public void testImmunizationReportHCPView() throws Exception {
		//Test data for HCP viewing a patient's immunization report.
		//No immunizations should be displayed as received.
		//The following required immunizations should be displayed:
			//DTP
			//MMR
			//No Hep B needed because patient is born before July 1994
		
		driver = (HtmlUnitDriver) login("9000000000", "pw");
		driver.setJavascriptEnabled(true);
		assertEquals("iTrust - HCP Home", driver.getTitle());

		driver.findElement(By.xpath("//*[@id=\"iTrustMenu\"]/div/div[1]/div[1]/h2")).click();
	    driver.findElement(By.linkText("Immunization Report")).click();
		// choose patient Charlie Chaplin (MID 308)
		int attempts = 0;
		int totalTime = 0;
		while (attempts <= 10) {
			try {
				driver.findElement(By.xpath("//*[@id=\"searchBox\"]")).clear();
				driver.findElement(By.xpath("//*[@id=\"searchBox\"]"))
						.sendKeys("308");
				
				while (totalTime < 10000) {
					if (driver.findElement(By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input")).getAttribute("value").equals("308")) {
						System.out.println("*****Finally is the right patient!!!*****");
						break;
					} else {
						System.out.println("*****Not the right Patient!!!*****");
						Thread.sleep(500);
						totalTime += 500;
					}
				}

				driver.findElement(
						By.xpath("//*[@id=\"searchTarget\"]/table/tbody/tr[2]/td[1]/input")).click();
				break;
			} catch (StaleElementReferenceException e) {
				System.out.println(e);
			}
			attempts++;
		}

		assertEquals(7, driver.findElements(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr")).size());
		
		//Check that no immunizations logged as received
		assertEquals("No immunizations received.", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[3]/td[1]")).getText());
		
		//Check the two immunizations required
		//DTP
		assertEquals("90696", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[6]/td[1]")).getText());
		//MMR
		assertEquals("90707", driver.findElement(By.xpath("//*[@id=\"immunizationReport\"]/tbody/tr[7]/td[1]")).getText());
		
		//Check logging
		assertLogged(TransactionType.IMMUNIZATION_REPORT_HCP_VIEW, 9000000000L, 308L, "");
	}
	
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
