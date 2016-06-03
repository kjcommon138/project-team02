package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Test all office visit document
 */
public class DocumentOfficeVisitTest extends iTrustSeleniumTest {
	
	
	private WebDriver driver = new HtmlUnitDriver();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.uap1();
		gen.hcp0();
		gen.patient2();
		gen.patient1();
	}
	
	/*
	 * Authenticate UAP
	 * MID 8000000009
	 * Password: uappass1
	 * Choose "Document Office Visit"
	 * Enter Patient MID 1
	 * Enter Fields:
	 * Date: 2005-11-21
	 * Notes: "I like diet-coke"
	 */
	public void testDocumentOfficeVisit6() throws Exception {
		// login UAP
		driver = login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.xpath("//*[@id='other-menu']/ul/li[1]/a")).click();
		driver.get("http://localhost:8080/iTrust/" + "auth/getPatientID.jsp?UID_PATIENTID=1&forward=hcp-uap/documentOfficeVisit.jsp");
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		driver.findElement(By.xpath("//*[@id='formMain']/input[2]")).click();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		driver.findElement(By.name("visitDate")).sendKeys("11/21/2005" + Keys.ENTER);
		System.out.println("date: " + driver.findElement(By.xpath("//*[@id='mainForm']/table/tbody/tr[3]/td[2]/input[1]")).getText());
		driver.findElement(By.xpath("//*[@id='mainForm']/table/tbody/tr[6]/td[2]/textarea")).sendKeys("I like diet-coke");
		driver.findElement(By.xpath("//*[@id='update']")).click();
		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 8000000009L, 1L, "Office visit");
	}

	/*
	 * Authenticate HCP
	 * MID 9000000000
	 * Password: pw
	 * Choose Document Office Visit
	 * Enter Patient MID 2 and confirm
	 * Choose to document new office visit.
	 * Enter Fields:
	 * Date: 2005-11-2
	 * Notes: Great patient!
	 */
	public void testDocumentOfficeVisit1() throws Exception {
		// login UAP
		driver = login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.linkText("Document Office Visit")).click();
		// choose patient 1
		driver.get("http://localhost:8080/iTrust/" + "auth/getPatientID.jsp?UID_PATIENTID=2&forward=hcp-uap/documentOfficeVisit.jsp");
				
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		// click Yes, Document Office Visit
		driver.findElement(By.xpath("//*[@id='formMain']/input[2]")).click();

		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		driver.findElement(By.name("visitDate")).sendKeys("11/02/2005" + Keys.ENTER);

		driver.findElement(By.xpath("//*[@id='mainForm']/table/tbody/tr[6]/td[2]/textarea")).sendKeys("Great Patient!");
				
		driver.findElement(By.xpath("//*[@id='update']")).click();

		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
	}

	/*
	 * Authenticate HCP
	 * MID 9000000000
	 * Password: pw
	 * Choose Document Office Visit
	 * Enter Patient MID 2 and confirm
	 * Choose to document new office vist.
	 * Enter Fields:
	 * Date: 2005-11-21
	 * Notes: <script>alert('ha ha ha');</script>
	 */
	public void testDocumentOfficeVisit2() throws Exception {
		// login UAP
		driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.linkText("Document Office Visit")).click();
		// choose patient 1
		driver.get("http://localhost:8080/iTrust/" + "auth/getPatientID.jsp?UID_PATIENTID=1&forward=hcp-uap/documentOfficeVisit.jsp");
		
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		// click Yes, Document Office Visit
		driver.findElement(By.xpath("//*[@id='formMain']/input[2]")).click();

		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		driver.findElement(By.name("visitDate")).sendKeys("11/21/2005" + Keys.ENTER);
	
		driver.findElement(By.xpath("//*[@id='mainForm']/table/tbody/tr[6]/td[2]/textarea")).sendKeys("<script>alert('ha ha ha');</script>");
		
		driver.findElement(By.xpath("//*[@id='update']")).click();

		assertTrue(driver.getPageSource().contains("Notes: Up to 300 alphanumeric characters, with space, and other punctuation"));
	}
	
	public void testUpdateOfficeVisitSemicolon() throws Exception {
		// login UAP
		driver = login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		// click Document Office Visit
		driver.findElement(By.linkText("Document Office Visit")).click();
		// choose patient 1
		driver.get("http://localhost:8080/iTrust/" + "auth/getPatientID.jsp?UID_PATIENTID=1&forward=hcp-uap/documentOfficeVisit.jsp");
		
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		// click Yes, Document Office Visit
		driver.findElement(By.xpath("//*[@id='formMain']/input[2]")).click();

		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		driver.findElement(By.name("visitDate")).sendKeys("11/21/2005" + Keys.ENTER);
	
		driver.findElement(By.xpath("//*[@id='mainForm']/table/tbody/tr[6]/td[2]/textarea")).sendKeys("I like diet-coke ;");
		
		driver.findElement(By.xpath("//*[@id='update']")).click();

		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 8000000009L, 1L, "Office visit");
	}
	
	/*
	 * Authenticate HCP
	 * MID 8000000009
	 * Password: uappass1
	 * Choose Document Office Visit
	 * Enter Patient MID 1 and confirm
	 * Choose to document new office vist.
	 * Enter Fields:
	 * Date: 2005-11-21
	 */
	public void testUpdateOfficeVisitOctothorpe() throws Exception {
		// login UAP
		driver = login("8000000009", "uappass1");
		assertEquals("iTrust - UAP Home", driver.getTitle());
		// click Document Office Visit
		
		driver.findElement(By.linkText("Document Office Visit")).click();
		// choose patient 1
		driver.get("http://localhost:8080/iTrust/" + "auth/getPatientID.jsp?UID_PATIENTID=1&forward=hcp-uap/documentOfficeVisit.jsp");
		
		assertEquals(ADDRESS + "auth/hcp-uap/documentOfficeVisit.jsp", driver.getCurrentUrl());
		// click Yes, Document Office Visit
		driver.findElement(By.xpath("//*[@id='formMain']/input[2]")).click();

		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		driver.findElement(By.name("visitDate")).sendKeys("11/21/2005" + Keys.ENTER);
	
		driver.findElement(By.xpath("//*[@id='mainForm']/table/tbody/tr[6]/td[2]/textarea")).sendKeys("I like diet coke #");
		
		driver.findElement(By.xpath("//*[@id='update']")).click();

		assertTrue(driver.getPageSource().contains("Information Successfully Updated"));
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 8000000009L, 1L, "Office visit");
	}
}
