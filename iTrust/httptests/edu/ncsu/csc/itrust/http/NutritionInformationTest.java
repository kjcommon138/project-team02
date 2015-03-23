package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

/**
 * Test cases for Use Case 68 & 69
 */
public class NutritionInformationTest extends iTrustHTTPTest{
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.hcp1();
		gen.hcp7(); // need to make nutritionist
		gen.patient1();
	}
	
	/*
	 * This test will check to see if the system allows a patient
	 * to access their patient nutrition info.
	 */
	public void testPatientGood() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Nutrition Entries").click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - View Nutrition", wr.getTitle());
		assertTrue(wr.getText().contains("My Nutrition"));
	}
	
	/*
	 * This test will check to see if the system allows a patient
	 * to access their patient nutrition info, and checks for 
	 * incorrect data format.
	 */
	public void testPatientBadEntryDate() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Add Nutrition Entry").click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Add Food Entry", wr.getTitle());
		assertTrue(wr.getText().contains("Add New Food Entry"));
		
		WebForm patientForm = wr.getForms()[0];
		
		// tests if the patient information on the form is updated
		patientForm.setParameter("entryDate","Today");
		patientForm.setParameter("foodName","Turkey Club");
		patientForm.setParameter("numServings","1");
		patientForm.setParameter("numCalories","10");
		patientForm.setParameter("numFat","10");
		patientForm.setParameter("numSodium","10");
		patientForm.setParameter("numCarbs","10");
		patientForm.setParameter("numSugar","10");
		patientForm.setParameter("numFiber","10");
		patientForm.setParameter("numProtein","10");
		patientForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Unparseable date:"));
	}
	
	/*
	 * This test will check to see if the system allows a patient
	 * to access their patient nutrition info, and checks for 
	 * incomplete form data for submitting a new entry.
	 */
	public void testPatientBadEntryFoodName() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Add Nutrition Entry").click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Add Food Entry", wr.getTitle());
		assertTrue(wr.getText().contains("Add New Food Entry"));
		
		WebForm patientForm = wr.getForms()[0];
		
		// tests if the patient information on the form is updated
		patientForm.setParameter("foodName","Turkey");
		patientForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Missing or incorrect input."));
	}
	
	/*
	 * This test will check to see if the system allows a patient
	 * to access their patient nutrition info, and checks for 
	 * bad number for calories.
	 */
	public void testPatientBadCalories() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Add Nutrition Entry").click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Add Food Entry", wr.getTitle());
		assertTrue(wr.getText().contains("Add New Food Entry"));
		
		WebForm patientForm = wr.getForms()[0];
		
		// tests if the patient information on the form is updated
		patientForm.setParameter("foodName","Turkey Club");
		patientForm.setParameter("numServings","1");
		patientForm.setParameter("numCalories","-5");
		patientForm.setParameter("numFat","10");
		patientForm.setParameter("numSodium","10");
		patientForm.setParameter("numCarbs","10");
		patientForm.setParameter("numSugar","10");
		patientForm.setParameter("numFiber","10");
		patientForm.setParameter("numProtein","10");
		patientForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Calories must be a non-negative integer."));
	}
	
	/*
	 * This test will check to see if the system allows a patient
	 * to access their patient nutrition info, and checks for 
	 * bad number for servings.
	 */
	public void testPatientBadServings() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Add Nutrition Entry").click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Add Food Entry", wr.getTitle());
		assertTrue(wr.getText().contains("Add New Food Entry"));
		
		WebForm patientForm = wr.getForms()[0];
		
		// tests if the patient information on the form is updated
		patientForm.setParameter("foodName","Turkey Club");
		patientForm.setParameter("numServings","0");
		patientForm.setParameter("numCalories","1");
		patientForm.setParameter("numFat","10");
		patientForm.setParameter("numSodium","10");
		patientForm.setParameter("numCarbs","10");
		patientForm.setParameter("numSugar","10");
		patientForm.setParameter("numFiber","10");
		patientForm.setParameter("numProtein","10");
		patientForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Servings must be a positive integer."));
	}
	
	/*
	 * This test will check to see if the system allows a patient
	 * to access their patient nutrition info, and checks for 
	 * bad input for fat.
	 */
	public void testPatientBadFat() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Add Nutrition Entry").click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Add Food Entry", wr.getTitle());
		assertTrue(wr.getText().contains("Add New Food Entry"));
		
		WebForm patientForm = wr.getForms()[0];
		
		// tests if the patient information on the form is updated
		patientForm.setParameter("foodName","Turkey Club");
		patientForm.setParameter("numServings","1");
		patientForm.setParameter("numCalories","1");
		patientForm.setParameter("numFat","A");
		patientForm.setParameter("numSodium","10");
		patientForm.setParameter("numCarbs","10");
		patientForm.setParameter("numSugar","10");
		patientForm.setParameter("numFiber","10");
		patientForm.setParameter("numProtein","10");
		patientForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Missing or incorrect input."));
	}
	
	/*
	 * This test will check to see if the system allows a patient
	 * to access their patient nutrition info, and checks for 
	 * bad input for sodium.
	 */
	public void testPatientBadSodium() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Add Nutrition Entry").click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Add Food Entry", wr.getTitle());
		assertTrue(wr.getText().contains("Add New Food Entry"));
		
		WebForm patientForm = wr.getForms()[0];
		
		// tests if the patient information on the form is updated
		patientForm.setParameter("foodName","Turkey Club");
		patientForm.setParameter("numServings","1");
		patientForm.setParameter("numCalories","1");
		patientForm.setParameter("numFat","1");
		patientForm.setParameter("numSodium","123$");
		patientForm.setParameter("numCarbs","10");
		patientForm.setParameter("numSugar","10");
		patientForm.setParameter("numFiber","10");
		patientForm.setParameter("numProtein","10");
		patientForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Missing or incorrect input."));
	}
	
	/*
	 * This test will check to see if the system allows a patient
	 * to access their patient nutrition info, and checks for 
	 * bad input for protein.
	 */
	public void testPatientBadProtein() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Add Nutrition Entry").click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Add Food Entry", wr.getTitle());
		assertTrue(wr.getText().contains("Add New Food Entry"));
		
		WebForm patientForm = wr.getForms()[0];
		
		// tests if the patient information on the form is updated
		patientForm.setParameter("foodName","Turkey Club");
		patientForm.setParameter("numServings","1");
		patientForm.setParameter("numCalories","1");
		patientForm.setParameter("numFat","1");
		patientForm.setParameter("numSodium","10");
		patientForm.setParameter("numCarbs","10");
		patientForm.setParameter("numSugar","10");
		patientForm.setParameter("numFiber","10");
		patientForm.setParameter("numProtein","1/2");
		patientForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Missing or incorrect input."));
	}
	
	/*
	 * This test will check to see if the system allows a patient
	 * to access their patient nutrition info, and checks for 
	 * correct data format.
	 */
	public void testPatientGoodEntry() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Add Nutrition Entry").click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Add Food Entry", wr.getTitle());
		assertTrue(wr.getText().contains("Add New Food Entry"));
		
		WebForm patientForm = wr.getForms()[0];
		
		// tests if the patient information on the form is updated
		patientForm.setParameter("foodName","Turkey Club");
		patientForm.setParameter("numServings","1");
		patientForm.setParameter("numCalories","10");
		patientForm.setParameter("numFat","10");
		patientForm.setParameter("numSodium","10");
		patientForm.setParameter("numCarbs","10");
		patientForm.setParameter("numSugar","10");
		patientForm.setParameter("numFiber","10");
		patientForm.setParameter("numProtein","10");
		patientForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Turkey Club"));
	}
	
	/*
	 * This test will check to see if the system allows
	 * a nutritionist from viewing the nutrition page.
	 */
	public void testNutritionist() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Patient Nutrition").click();
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Please Select a Patient", wr.getTitle());
		assertTrue(wr.getText().contains("Select a Patient"));
	}
}
