/**
 * 
 */
package edu.ncsu.csc.itrust.seleniumtests;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * The RequestRecordsReleaseTest contains selenium unit tests for requesting and viewing
 * patient records release requests via a patient, HCP, or UAP role. 
 * 
 * @author Eric Bolender
 *
 */
public class RequestRecordsReleaseTest extends iTrustSeleniumTest {

	/**
	 * This is called before each test to set up each one. It clears all the database tables
	 * and generates a new set of test data.
	 */
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	/**
	 * testPatientRequestNewRecordsRelease
	 * 
	 * Login as Caldwell Hudson
	 * MID: 102
	 * password: pw
	 * 
	 * Go to requests records release page (under records tab)
	 * submit a new release request
	 * fill out form
	 * click submit
	 * review success page
	 * 
	 * @throws Exception
	 */
	public void testPatientRequestNewRecordsRelease() throws Exception{
		// Login as patient Caldwell Hudson
		logIn("102", "pw");	
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 102L, 0L, "");

		// Go to Requests Records Release page
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("Request Records Release")).click();
		assertEquals("iTrust - Records Release Request History", driver.getTitle());

		// fill out form
		driver.findElement(By.id("submitReq")).click();
		assertEquals("iTrust - Records Release Request", driver.getTitle());

		new Select(driver.findElement(By.name("releaseHospital"))).selectByValue("1");
		driver.findElement(By.id("recFirstName")).clear();
		driver.findElement(By.id("recFirstName")).sendKeys("Mike");
		driver.findElement(By.id("recLastName")).clear();
		driver.findElement(By.id("recLastName")).sendKeys("Myers");
		driver.findElement(By.id("recPhone")).clear();
		driver.findElement(By.id("recPhone")).sendKeys("919-123-1234");
		driver.findElement(By.id("recEmail")).clear();
		driver.findElement(By.id("recEmail")).sendKeys("mike.myers@hospital.org");
		driver.findElement(By.id("recHospitalName")).clear();
		driver.findElement(By.id("recHospitalName")).sendKeys("Testing Hospital");
		driver.findElement(By.id("recHospitalAddress1")).clear();
		driver.findElement(By.id("recHospitalAddress1")).sendKeys("101 Testing Hospital Drive");
		driver.findElement(By.id("recHospitalAddress2")).clear();
		driver.findElement(By.id("recHospitalAddress2")).sendKeys("suite 102");
		driver.findElement(By.id("recHospitalCity")).clear();
		driver.findElement(By.id("recHospitalCity")).sendKeys("Raleigh");
		driver.findElement(By.id("recHospitalState")).clear();
		driver.findElement(By.id("recHospitalState")).sendKeys("NC");
		driver.findElement(By.id("recHospitalZip")).clear();
		driver.findElement(By.id("recHospitalZip")).sendKeys("27606");
		driver.findElement(By.id("releaseJustification")).clear();
		driver.findElement(By.id("releaseJustification")).sendKeys("Annual records request");
		driver.findElement(By.id("verifyForm")).click();
		driver.findElement(By.id("digitalSig")).clear();
		driver.findElement(By.id("digitalSig")).sendKeys("Caldwell Hudson");
		driver.findElement(By.id("submit")).click();

		assertLogged(TransactionType.PATIENT_RELEASE_HEALTH_RECORDS, 102L, 102L, "");

		// success page - review all the submitted elements are the same
		assertTrue(driver.getPageSource().contains("Request successfully sent"));
		assertTrue(driver.getPageSource().contains("Pending"));

		assertTrue(driver.getPageSource().contains("Health Institute Dr. E"));
		assertTrue(driver.getPageSource().contains("First name: Mike"));
		assertTrue(driver.getPageSource().contains("Last name: Myers"));
		assertTrue(driver.getPageSource().contains("Phone number: 919-123-1234"));
		assertTrue(driver.getPageSource().contains("Email address: mike.myers@hospital.org"));
		assertTrue(driver.getPageSource().contains("Hospital: Testing Hospital"));
		assertTrue(driver.getPageSource().contains("Hospital address: 101 Testing Hospital Drivesuite 102, Raleigh, NC 27606"));
		assertTrue(driver.getPageSource().contains("Annual records request"));		
	}

	/**
	 * testMedicalRecordsRelease_Patient_NoSignature
	 * 
	 * Login as Caldwell Hudson
	 * MID: 102
	 * password: p2
	 * 
	 * Go to Requests Records Release page
	 * submit a new request
	 * fill out the form, but not the signature
	 * click submit
	 * confirm error occured - and still on form page
	 */
	public void testMedicalRecordsRelease_Patient_NoSignature() throws Exception{
		// Login as patient Caldwell Hudson
		logIn("102", "pw");	
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 102L, 0L, "");

		// Go to Request Records Release page
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("Request Records Release")).click();
		assertEquals("iTrust - Records Release Request History", driver.getTitle());

		// submit request
		driver.findElement(By.id("submitReq")).click();
		assertEquals("iTrust - Records Release Request", driver.getTitle());

		// fill out form - but not the signature
		new Select(driver.findElement(By.name("releaseHospital"))).selectByValue("1");
		driver.findElement(By.id("recFirstName")).clear();
		driver.findElement(By.id("recFirstName")).sendKeys("Mike");
		driver.findElement(By.id("recLastName")).clear();
		driver.findElement(By.id("recLastName")).sendKeys("Myers");
		driver.findElement(By.id("recPhone")).clear();
		driver.findElement(By.id("recPhone")).sendKeys("919-123-1234");
		driver.findElement(By.id("recEmail")).clear();
		driver.findElement(By.id("recEmail")).sendKeys("mike.myers@hospital.org");
		driver.findElement(By.id("recHospitalName")).clear();
		driver.findElement(By.id("recHospitalName")).sendKeys("Testing Hospital");
		driver.findElement(By.id("recHospitalAddress1")).clear();
		driver.findElement(By.id("recHospitalAddress1")).sendKeys("101 Testing Hospital Drive");
		driver.findElement(By.id("recHospitalAddress2")).clear();
		driver.findElement(By.id("recHospitalAddress2")).sendKeys("Suite 102");
		driver.findElement(By.id("recHospitalCity")).clear();
		driver.findElement(By.id("recHospitalCity")).sendKeys("Raleigh");
		driver.findElement(By.id("recHospitalState")).clear();
		driver.findElement(By.id("recHospitalState")).sendKeys("NC");
		driver.findElement(By.id("recHospitalZip")).clear();
		driver.findElement(By.id("recHospitalZip")).sendKeys("27606");
		driver.findElement(By.id("releaseJustification")).clear();
		driver.findElement(By.id("releaseJustification")).sendKeys("Annual records request");
		driver.findElement(By.id("verifyForm")).click();

		// submit
		driver.findElement(By.id("submit")).click();

		//if we are still on the same page then we know that the the submission failed and our test has passed.
		assertEquals("iTrust - Records Release Request", driver.getTitle());	
	}

	/**
	 * testMedicalRecordsRelease_Patient_NotAllFields
	 * 
	 * Login as Caldwell Hudson
	 * MID: 102
	 * password: pw
	 * 
	 * Go to Request Records Release Page
	 * submit a new request
	 * fill out a form - but only some of the fields
	 * click submit
	 * confirm an error occured - and still on form page
	 * 
	 * @throws Exception
	 */
	public void testMedicalRecordsRelease_Patient_NotAllFields() throws Exception{
		// Login as patient Caldwell Hudson
		logIn("102", "pw");	
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 102L, 0L, "");

		// Go to Request Records Release page
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("Request Records Release")).click();
		assertEquals("iTrust - Records Release Request History", driver.getTitle());

		// submit a request
		driver.findElement(By.id("submitReq")).click();
		assertEquals("iTrust - Records Release Request", driver.getTitle());

		// fill out form
		driver.findElement(By.id("verifyForm")).click();
		driver.findElement(By.id("digitalSig")).clear();
		driver.findElement(By.id("digitalSig")).sendKeys("Caldwell Hudson");

		// submit
		driver.findElement(By.id("submit")).click();

		//if we are still on the same page then we know that the the submission failed and our test has passed.
		assertEquals("iTrust - Records Release Request", driver.getTitle());		
	}

	/**
	 * testPatientViewApprovedRequest
	 * 
	 * Login as Caldwell Hudson
	 * MID: 102
	 * password: pw
	 * 
	 * Go to Request Records Release page
	 * click on the 08/17/2012 request that was approved
	 * confirm the values are what as expected
	 * 
	 * @throws Exception
	 */
	public void testPatientViewApprovedRequest() throws Exception{
		// Login as patient Caldwell Hudson
		logIn("102", "pw");	
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 102L, 0L, "");

		// Go to Request Records Release page
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("Request Records Release")).click();
		assertEquals("iTrust - Records Release Request History", driver.getTitle());

		// click the 08/17/2012 approved request
		assertTrue(driver.getPageSource().contains("08/17/2012"));
		driver.findElement(By.xpath("(//a[contains(text(),'View')])[7]")).click();

		// make sure all the values are correct
		assertTrue(driver.getPageSource().contains("Approved"));
		assertTrue(driver.getPageSource().contains("Health Institute Dr. E"));
		assertTrue(driver.getPageSource().contains("First name: Monica"));
		assertTrue(driver.getPageSource().contains("Last name: Brown"));
		assertTrue(driver.getPageSource().contains("Phone number: 329-818-7734"));
		assertTrue(driver.getPageSource().contains("Email address: monica.brown@hartfordradiology.com"));
		assertTrue(driver.getPageSource().contains("Hospital: Hartford Radiology Ltd."));
		assertTrue(driver.getPageSource().contains("Hospital address: 8941 Hargett Way, Hartford, CT 01243"));

	}

	/**
	 * testHCPApprovesRequest
	 * 
	 * Login in as Kelly Doctor
	 * MID: 9000000000
	 * password: pw
	 * 
	 * Go to Records Release Requests page
	 * select the 08/07/2010 request
	 * confirm the data is correct
	 * approve the request
	 * 
	 * @throws Exception
	 */
	public void testHCPApprovesRequest() throws Exception{
		// Login as HCP Kelly Doctor
		logIn("9000000000", "pw");	
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Go to Request Records Release page
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Records Release Requests")).click();
		assertEquals("iTrust - Records Release Requests", driver.getTitle());

		// select the 08/07/2010 request
		assertTrue(driver.getPageSource().contains("08/08/2010"));
		driver.findElement(By.xpath("(//a[contains(text(),'View')])[9]")).click();

		// confirm all the data is correct
		assertTrue(driver.getPageSource().contains("Pending"));
		assertTrue(driver.getPageSource().contains("Fozzie Bear"));

		assertTrue(driver.getPageSource().contains("Health Institute Dr. E"));
		assertTrue(driver.getPageSource().contains("First name: Brian"));
		assertTrue(driver.getPageSource().contains("Last name: McIntyre"));
		assertTrue(driver.getPageSource().contains("Phone number: 744-239-9117"));
		assertTrue(driver.getPageSource().contains("Email address: mcintyre@kellerheart.com"));
		assertTrue(driver.getPageSource().contains("Hospital: Keller Drive Heart Specialists"));
		assertTrue(driver.getPageSource().contains("Hospital address: 622 Center Wood Avenue, Savannah, GA 42991"));

		// approve the request
		driver.findElement(By.id("Approve")).click();
		assertTrue(driver.getPageSource().contains("Approved"));
		assertLogged(TransactionType.HCP_RELEASE_APPROVAL, 9000000000L, 22L, "");
	}

	/**
	 * testHCPDeniesRequest
	 * 
	 * Login as Kelly Doctor
	 * MID: 9000000000
	 * password: pw
	 * 
	 * Go to requests records release page
	 * click on the 10/18/2013 request
	 * confirm all the data is correct
	 * deny the request
	 * 
	 * @throws Exception
	 */
	public void testHCPDeniesRequest() throws Exception{
		// Login as HCP Kelly Doctor
		logIn("9000000000", "pw");	
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Go to Request Records Release page
		driver.findElement(By.cssSelector("h2.panel-title")).click();
		driver.findElement(By.linkText("Records Release Requests")).click();
		assertEquals("iTrust - Records Release Requests", driver.getTitle());

		// click on the 10/18/2013 request
		assertTrue(driver.getPageSource().contains("10/18/2013"));
		driver.findElement(By.xpath("(//a[contains(text(),'View')])[5]")).click();

		// confirm the information is correct
		assertEquals("iTrust - Records Release Request", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Pending"));
		assertTrue(driver.getPageSource().contains("Caldwell Hudson"));

		assertTrue(driver.getPageSource().contains("Health Institute Dr. E"));
		assertTrue(driver.getPageSource().contains("First name: Michael"));
		assertTrue(driver.getPageSource().contains("Last name: Garrison"));
		assertTrue(driver.getPageSource().contains("Phone number: 528-912-9103"));
		assertTrue(driver.getPageSource().contains("Email address: mkgarrison@fairfaxchiro.com"));
		assertTrue(driver.getPageSource().contains("Hospital: Fairfax Chiropractic"));
		assertTrue(driver.getPageSource().contains("Hospital address: 72 Waywind Street, Hartford, CT 01241"));
		assertTrue(driver.getPageSource().contains("Major back pain referral"));

		// deny request
		driver.findElement(By.id("Deny")).click();
		assertTrue(driver.getPageSource().contains("Denied"));
		assertLogged(TransactionType.HCP_RELEASE_DENIAL, 9000000000L, 102L, "");
	}

	/**
	 * testUAPDeniesRequest
	 * 
	 * Login is as FirstUAP LastUAP
	 * MID: 8000000009
	 * password: uappass1
	 * 
	 * Go to Request Records Release page
	 * click on the 11/23/2013 request
	 * confirm all the information is correct
	 * deny the request
	 * 
	 * @throws Exception
	 */
	public void testUAPDeniesRequest() throws Exception{
		// Login as UAP FirstUAP LastUAP
		logIn("8000000009", "uappass1");	
		assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");

		// Go to Request Records Release page
		driver.findElement(By.cssSelector("#view > h2.panel-title")).click();
		driver.findElement(By.linkText("Records Release Requests")).click();
		assertEquals("iTrust - Records Release Requests", driver.getTitle());

		// click on the 11/23/2013 request
		assertTrue(driver.getPageSource().contains("11/23/2013"));
		driver.findElement(By.linkText("View")).click();

		// confirm the information is correct
		assertTrue(driver.getPageSource().contains("Pending"));
		assertTrue(driver.getPageSource().contains("Fozzie Bear"));

		assertTrue(driver.getPageSource().contains("Health Institute Dr. E"));
		assertTrue(driver.getPageSource().contains("First name: Connor"));
		assertTrue(driver.getPageSource().contains("Last name: DunBar"));
		assertTrue(driver.getPageSource().contains("Phone number: 919-733-1991"));
		assertTrue(driver.getPageSource().contains("Email address: c.dunbar@rexhospital.org"));
		assertTrue(driver.getPageSource().contains("Hospital: Rex Hospital"));
		assertTrue(driver.getPageSource().contains("Hospital address: 1829 Lake Boone Trail, Raleigh, NC 27612"));
		assertTrue(driver.getPageSource().contains("Blood test requested from specialist"));
		assertEquals("iTrust - Records Release Request", driver.getTitle());

		// deny the request
		driver.findElement(By.id("Deny")).click();
		assertTrue(driver.getPageSource().contains("Denied"));
		assertLogged(TransactionType.UAP_RELEASE_DENIAL, 8000000009L, 22L, "");
	}

	/**
	 * testUAPViewsApprovedRequest
	 * 
	 * Login as FirstUAP LastUAP
	 * MID: 8000000009
	 * password: uappass1
	 * 
	 * Go to Request Records Release page
	 * click on the 05/03/2008 request
	 * confirm the data is correct
	 * approve the request
	 * 
	 * @throws Exception
	 */
	public void testUAPViewsApprovedRequest() throws Exception{
		// Login as UAP FirstUAP LastUAP
		logIn("8000000009", "uappass1");	
		assertEquals("iTrust - UAP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");

		// Go to Request Records Release page
		driver.findElement(By.cssSelector("#view > h2.panel-title")).click();
		driver.findElement(By.linkText("Records Release Requests")).click();
		assertEquals("iTrust - Records Release Requests", driver.getTitle());

		// click on the 05/03/2008 request
		assertTrue(driver.getPageSource().contains("05/03/2008"));
		driver.findElement(By.xpath("(//a[contains(text(),'View')])[10]")).click();

		// confirm the information is correct
		assertTrue(driver.getPageSource().contains("Approved"));
		assertTrue(driver.getPageSource().contains("Random Person"));

		assertTrue(driver.getPageSource().contains("Health Institute Dr. E"));
		assertTrue(driver.getPageSource().contains("First name: Harold"));
		assertTrue(driver.getPageSource().contains("Last name: McClain"));
		assertTrue(driver.getPageSource().contains("Phone number: 916-991-4124"));
		assertTrue(driver.getPageSource().contains("Email address: hmcclain@easternhealth.com"));
		assertTrue(driver.getPageSource().contains("Hospital: East Health Services"));
		assertTrue(driver.getPageSource().contains("9002 Asheville Avenue, Cary, NC 27511"));
		assertTrue(driver.getPageSource().contains("Referred for services"));
	}

	/**
	 * testViewDependents
	 * 
	 * Login as Caldwell Hudson
	 * MID: 102
	 * password: pw
	 * 
	 * Go to Request Records Release page
	 * try selecting a different dependent that doesn't exist - it should throw an exception
	 * 
	 * @throws Exception
	 */
	public void testViewDependents() throws Exception{
		//Login as patient Caldwell Hudson
		logIn("102", "pw");	
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 102L, 0L, "");

		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
		driver.findElement(By.linkText("Request Records Release")).click();
		assertEquals("iTrust - Records Release Request History", driver.getTitle());

		try {
			new Select(driver.findElement(By.name("selectedPatient"))).selectByValue("3");
		} catch (NoSuchElementException e) {
			assertTrue(true);
		}

	}

	/**
	 * testInvalidInputSQLInjection
	 * 
	 * Login as Caldwell Hudson
	 * MID: 102
	 * password: pw
	 * 
	 * Go to request records release page
	 * make new release request
	 * fill out form will sql injection code
	 * confirm an error occurs - still on the form page
	 * @throws Exception
	 */
	public void testInvalidInputSQLInjection() throws Exception{
		// Login as patient Caldwell Hudson
		logIn("102", "pw");	
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 102L, 0L, "");

		// go to request records release page
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("Request Records Release")).click();
		assertEquals("iTrust - Records Release Request History", driver.getTitle());

		// make new request release request
		driver.findElement(By.id("submitReq")).click();
		assertEquals("iTrust - Records Release Request", driver.getTitle());

		// fill out form - sql injection
		driver.findElement(By.id("recFirstName")).clear();
		driver.findElement(By.id("recFirstName")).sendKeys("\\'");
		driver.findElement(By.id("recLastName")).clear();
		driver.findElement(By.id("recLastName")).sendKeys("\\'");
		driver.findElement(By.id("recPhone")).clear();
		driver.findElement(By.id("recPhone")).sendKeys("\\'");
		driver.findElement(By.id("recEmail")).clear();
		driver.findElement(By.id("recEmail")).sendKeys("\\'");
		driver.findElement(By.id("recHospitalName")).clear();
		driver.findElement(By.id("recHospitalName")).sendKeys("\\'");
		driver.findElement(By.id("recHospitalAddress1")).clear();
		driver.findElement(By.id("recHospitalAddress1")).sendKeys("\\'");
		driver.findElement(By.id("recHospitalAddress2")).clear();
		driver.findElement(By.id("recHospitalAddress2")).sendKeys("\\'");
		driver.findElement(By.id("recHospitalCity")).clear();
		driver.findElement(By.id("recHospitalCity")).sendKeys("\\'");
		driver.findElement(By.id("recHospitalState")).clear();
		driver.findElement(By.id("recHospitalState")).sendKeys("\\'");
		driver.findElement(By.id("recHospitalZip")).clear();
		driver.findElement(By.id("recHospitalZip")).sendKeys("\\'");
		driver.findElement(By.id("releaseJustification")).clear();
		driver.findElement(By.id("releaseJustification")).sendKeys("Annual records request");
		driver.findElement(By.id("verifyForm")).click();
		driver.findElement(By.id("digitalSig")).clear();
		driver.findElement(By.id("digitalSig")).sendKeys("Caldwell Hudson");
		driver.findElement(By.id("submit")).click();


		//if we are still on the same page then we know that the the submission failed and our test has passed.
		assertEquals("iTrust - Records Release Request", driver.getTitle());		
		assertTrue(driver.getPageSource().split("\\'").length >= 10);
	}
}
