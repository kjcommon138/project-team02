package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Test of use case 58 and 59, which focus on patients being
 * dependents of other patients.
 */
public class DependentsTest extends iTrustSeleniumTest {
	
	public static final String ADDRESSADD = "http://localhost:8080/iTrust/auth/hcp-uap/addPatient.jsp";
	private WebDriver driver = new HtmlUnitDriver();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	/**
	 * Test adding a new patient as a dependent
	 * @throws Exception
	 *
	 */
	public void testAddDependentPatient() throws Exception {
		// Login
		driver = login("9000000000", "pw");
		
		//Check to make sure we are on the home page
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Navigate to the Add Patient page
		driver.get(ADDRESSADD);

        //Add new dependent patient Bob Marley

		driver.findElement(By.name("firstName")).sendKeys("Bob");
		driver.findElement(By.name("lastName")).sendKeys("Marley");
		driver.findElement(By.name("email")).sendKeys("bmarley@test.com");
		driver.findElement(By.name("isDependent")).click();
		driver.findElement(By.name("repId")).sendKeys("102");
		driver.findElement(By.name("button")).click();
		assertTrue(driver.getPageSource().contains("successfully added"));

		String sCellValue = driver.findElement(By.xpath("//*[@id='newPatientTable']/tbody/tr[2]/td[2]")).getText();
		 
		assertLogged(TransactionType.HCP_CREATED_DEPENDENT_PATIENT, 9000000000L, Long.parseLong(sCellValue, 10), "");
	}
	
	/**
	 * Tests adding a dependent / representative relationship to existing patients
	 * @throws Exception
	 */
	public void testEditDependentRepresentative() throws Exception {
		// Login
		driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Click Representatives
		driver.findElement(By.xpath("//*[@id=\"info-menu\"]/ul/li[5]/a")).click();
		assertTrue(driver.getPageSource().contains("Search by name or MID:"));
		
		// Choose patient 103
		driver.get("http://localhost:8080/iTrust/" + "auth/getPatientID.jsp?UID_PATIENTID=103&forward=hcp/editRepresentatives.jsp");
		assertEquals(ADDRESS + "auth/hcp/editRepresentatives.jsp", driver.getCurrentUrl());
		assertTrue(driver.getTitle().equals("iTrust - Manage Representatives"));
		
		//Add Caldwell Hudson as a representative
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/table/tbody/tr[4]/td/table/tbody/tr/td[3]/input")).click();
		//Enter and search for a patient mid
		driver.findElement(By.cssSelector("input[type=\"button\"]")).click();
		assertTrue(true);
		
		/* The rest of this test doesn't work with HTMLUnitDriver. Explanation on github.
		driver.findElement(By.name("mid")).clear();// cannot find field????
		driver.findElement(By.name("mid")).sendKeys("102");
		driver.findElement(By.cssSelector("input.getUserSearchButton")).click();
		driver.findElement(By.name("correct")).click();
		driver.findElement(By.name("action")).click();
		
		//Another option for this test using xpath.
		
		//driver.findElement(By.xpath("/html/body/form/table/tbody/tr[2]/td[2]/input")).sendKeys("102");//fails here
		//driver.findElement(By.xpath("/html/body/form/table/tbody/tr[3]/td/input")).click();
		//driver.findElement(By.xpath("/html/body/form/table/tbody/tr/td/input[1]")).click();
		//driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/div/input")).click();
		
		//Check to make sure the representative was added
		assertTrue(driver.getPageSource().contains("Caldwell Hudson"));
		*/
	}
	
	/**
	 * Tests that a dependent cannot login
	 * @throws Exception
	 */
	public void testDependentLogin() throws Exception {
		// begin at the iTrust home page
		driver.get(ADDRESS);
				
		// Enter Username on the element found by above desc.
		driver.findElement(By.id("j_username")).sendKeys("580");

		// Enter Password on the element found by the above desc.
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.id("Login_Button")).submit();
		assertTrue(driver.getPageSource().contains("Failed login attempts:"));
	}
	
	/**
	 * Tests that a list of a depedent's representatives is displayed to them
	 * @throws Exception
	 */
	public void testListRepresentatives() throws Exception {
		// Login
		driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		// Click Representatives
		driver.findElement(By.xpath("//*[@id=\"info-menu\"]/ul/li[5]/a")).click();
		assertTrue(driver.getPageSource().contains("Search by name or MID:"));
		
		// Choose patient 2
		driver.get("http://localhost:8080/iTrust/" + "auth/getPatientID.jsp?UID_PATIENTID=2&forward=hcp/editRepresentatives.jsp");
		assertEquals(ADDRESS + "auth/hcp/editRepresentatives.jsp", driver.getCurrentUrl());
		assertTrue(driver.getTitle().equals("iTrust - Manage Representatives"));
		
		// Make sure Representatives are listed
		assertTrue(driver.getPageSource().contains("Random Person"));
		assertTrue(driver.getPageSource().contains("Care Needs"));
		assertTrue(driver.getPageSource().contains("Baby Programmer"));
		assertTrue(driver.getPageSource().contains("Baby A"));
		assertTrue(driver.getPageSource().contains("Baby B"));
		assertTrue(driver.getPageSource().contains("Baby C"));
	}
	
	/**
	 * Tests to make sure representatives can't be dependents themselves
	 * @throws Exception
	 */
	public void testRepresentativeNotDependent() throws Exception {
		//Load UC58 data
		gen.uc58();
		
		// Login
		driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		// Click Representatives
		driver.findElement(By.xpath("//*[@id=\"info-menu\"]/ul/li[5]/a")).click();
		assertTrue(driver.getPageSource().contains("Search by name or MID:"));
		
		//Search and select Bob Marley (MID 580)
		driver.get("http://localhost:8080/iTrust/" + "auth/getPatientID.jsp?UID_PATIENTID=580&forward=hcp/editRepresentatives.jsp");
		assertEquals(ADDRESS + "auth/hcp/editRepresentatives.jsp", driver.getCurrentUrl());
		assertTrue(driver.getTitle().equals("iTrust - Manage Representatives"));
		
		assertTrue(driver.getPageSource().contains("Bob Marley is a dependent"));
		assertTrue(driver.getPageSource().contains("Dependent users cannot represent others."));
	}
	
	public void testRequestRecordsForDependent() throws Exception {
		// Login
		driver = login("1", "pw");
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// Click Records Release Request
		driver.findElement(By.xpath("//*[@id=\"records-menu\"]/ul/li[3]/a")).click();
		assertTrue(driver.getTitle().equals("iTrust - Records Release Request History"));
		
		// Click Submit New Request
		driver.findElement(By.xpath("//*[@id=\"submitReq\"]")).click();
		assertEquals("iTrust - Records Release Request", driver.getTitle());
		
		// Fill in form
		Select select = new Select(driver.findElement(By.xpath("//*[@id=\"mainForm\"]/select")));
		select.selectByVisibleText("Central Hospital");
		driver.findElement(By.xpath("//*[@id=\"recFirstName\"]")).sendKeys("Benedict");
		driver.findElement(By.xpath("//*[@id=\"recLastName\"]")).sendKeys("Cucumberpatch");
		driver.findElement(By.xpath("//*[@id=\"recPhone\"]")).sendKeys("555-666-7777");
		driver.findElement(By.xpath("//*[@id=\"recEmail\"]")).sendKeys("a@b.com");
		driver.findElement(By.xpath("//*[@id=\"recHospitalName\"]")).sendKeys("Rex Hospital");
		driver.findElement(By.xpath("//*[@id=\"recHospitalAddress1\"]")).sendKeys("123 Broad St.");
		driver.findElement(By.xpath("//*[@id=\"recHospitalAddress2\"]")).sendKeys(" ");
		driver.findElement(By.xpath("//*[@id=\"recHospitalCity\"]")).sendKeys("Cary");
		driver.findElement(By.xpath("//*[@id=\"recHospitalState\"]")).sendKeys("NC");
		driver.findElement(By.xpath("//*[@id=\"recHospitalZip\"]")).sendKeys("27164");
		driver.findElement(By.xpath("//*[@id=\"releaseJustification\"]")).sendKeys("Moving");
		driver.findElement(By.xpath("//*[@id=\"verifyForm\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"digitalSig\"]")).sendKeys("Random Person");
		driver.findElement(By.xpath("//*[@id=\"submit\"]")).click();
		
		// Make sure the request was successfully submitted
		assertTrue(driver.getPageSource().contains("Request successfully sent"));
	}
	
	public void testRequestRecordsWithDependentSignature() throws Exception {
		// Login
		driver = login("1", "pw");
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// Click Records Release Request
		driver.findElement(By.xpath("//*[@id=\"records-menu\"]/ul/li[3]/a")).click();
		assertTrue(driver.getTitle().equals("iTrust - Records Release Request History"));
		
		// Click Submit New Request
		driver.findElement(By.xpath("//*[@id=\"submitReq\"]")).click();
		assertEquals("iTrust - Records Release Request", driver.getTitle());
		
		// Fill in form
		Select select = new Select(driver.findElement(By.xpath("//*[@id=\"mainForm\"]/select")));
		select.selectByVisibleText("Central Hospital");
		driver.findElement(By.xpath("//*[@id=\"recFirstName\"]")).sendKeys("Benedict");
		driver.findElement(By.xpath("//*[@id=\"recLastName\"]")).sendKeys("Cucumberpatch");
		driver.findElement(By.xpath("//*[@id=\"recPhone\"]")).sendKeys("555-666-7777");
		driver.findElement(By.xpath("//*[@id=\"recEmail\"]")).sendKeys("a@b.com");
		driver.findElement(By.xpath("//*[@id=\"recHospitalName\"]")).sendKeys("Rex Hospital");
		driver.findElement(By.xpath("//*[@id=\"recHospitalAddress1\"]")).sendKeys("123 Broad St.");
		driver.findElement(By.xpath("//*[@id=\"recHospitalAddress2\"]")).sendKeys(" ");
		driver.findElement(By.xpath("//*[@id=\"recHospitalCity\"]")).sendKeys("Cary");
		driver.findElement(By.xpath("//*[@id=\"recHospitalState\"]")).sendKeys("NC");
		driver.findElement(By.xpath("//*[@id=\"recHospitalZip\"]")).sendKeys("27164");
		driver.findElement(By.xpath("//*[@id=\"releaseJustification\"]")).sendKeys("Moving");
		driver.findElement(By.xpath("//*[@id=\"verifyForm\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"digitalSig\"]")).sendKeys("Billy Ross");
		driver.findElement(By.xpath("//*[@id=\"submit\"]")).click();
		
		// Make sure the request throws an error
		assertTrue(driver.getPageSource().contains("Error"));
	}
	
	public void testRequestRecordsForNotRepresentedDependent() throws Exception {
		// Load UC59 data
		gen.uc59();
		
		// Login
		driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Click Representatives
		driver.findElement(By.xpath("//*[@id=\"info-menu\"]/ul/li[5]/a")).click();
		assertTrue(driver.getPageSource().contains("Search by name or MID:"));
		
		// Choose patient 750
		driver.get("http://localhost:8080/iTrust/" + "auth/getPatientID.jsp?UID_PATIENTID=750&forward=hcp/editRepresentatives.jsp");
		assertEquals(ADDRESS + "auth/hcp/editRepresentatives.jsp", driver.getCurrentUrl());
		assertTrue(driver.getTitle().equals("iTrust - Manage Representatives"));
		
		// Remove the representative and then log out
		driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/form/table/tbody/tr[3]/td[2]/a")).submit();//not removing
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();

		/* The rest of this test doesn't work with HTMLUnitDriver. Explanation on github.
		
		//Log in as Bob Ross
		driver = login("750", "pw");
		assertLogged(TransactionType.HOME_VIEW, 750L, 0L, "");
		
		// Click Records Release Request
		driver.findElement(By.xpath("//*[@id=\"records-menu\"]/ul/li[3]/a")).click();
		assertTrue(driver.getTitle().equals("iTrust - Records Release Request History"));
		assertFalse(driver.getPageSource().contains("Billy Ross"));
		*/
	}
	
	public void testViewRequestedRecordsForDependent() throws Exception {
		// Load UC59 data
		//gen.uc59();
		
		//Log in as Bob Ross
		driver = login("1", "pw");
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// Click Records Release Request
		driver.findElement(By.xpath("//*[@id=\"records-menu\"]/ul/li[3]/a")).click();
		assertTrue(driver.getTitle().equals("iTrust - Records Release Request History"));
		assertTrue(driver.getPageSource().contains("Random Person"));
		
		// View medical release form for Random Person
		driver.findElement(By.xpath("//*[@id=\"requestHistory\"]/tbody/tr[1]/td[4]/a")).click();
		
		//wr = wr.getLinkWith("Request Records Release").click();
		//Submit request for dependent Billy Ross
		//WebForm dependentForm = wr.getFormWithID("dependentForm");
		//dependentForm.setParameter("selectedPatient", "0");
		//dependentForm.submit();
		
		//View medical records release form
		//wr = wc.getCurrentPage();
		//wr = wr.getLinkWith("View").click();
		driver.findElement(By.xpath("//*[@id=\"records-menu\"]/ul/li[1]/a")).click();
		assertEquals("iTrust - View My Records", driver.getTitle());
	}
	
	public void testApproveRecordsRequestForDependent() throws Exception {
		// Load UC59 data
		gen.uc59();

		// Login
		driver = login("9000000000", "pw");
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Click Records Release Request
		driver.findElement(By.xpath("//*[@id=\"info-menu\"]/ul/li[9]/a")).click();
		assertTrue(driver.getTitle().equals("iTrust - Records Release Requests"));
		
		//Approve the most recent record
		driver.findElement(By.xpath("//*[@id=\"requestHistory\"]/tbody/tr[1]/td[4]/a")).click();
		assertEquals("iTrust - Records Release Request", driver.getTitle());
		driver.findElement(By.xpath("//*[@id=\"Approve\"]")).submit();

		assertTrue(driver.getPageSource().contains("Approved"));
	}
}
