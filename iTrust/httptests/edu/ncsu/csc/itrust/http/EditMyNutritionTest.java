package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Use Case 68 & 70
 */
public class EditMyNutritionTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		HttpUnitOptions.setScriptingEnabled(false);
		gen.clearAllTables();
		gen.standardData();		
	}
	
	/*
	 * Precondition: Patient Emily Prentiss is an
	 * authenticated user in the system. She has
	 * one food diary entry that contains the following information:
	 * 
	 * Date: 3/16/2014 
	 * Food: Chocolate Shake 
	 * Meal: Lunch 
	 * Servings: 2 
	 * Calories: 500 
	 * Fat: 23.5 
	 * Sodium: 259 
	 * Carbs: 66.5 
	 * Fiber: 0 
	 * Sugar: 42.4 
	 * Protein: 5.9 
	 * Description: 
	 * Select the existing entry from the table of diary 
	 * entries 
	 * 
	 * Make the following changes to the entry 
	 * Servings to 9 
     * Calories to 127 
     * Fat to 68 
     * Sodium to 687 
     * Carbs to 55.1 
     * Sugars to 112.4 
     * Protein to 15.6 
     *
	 * Click the Save changes button 
	 * Expected results: A prompt should appear notifying the user that the 
	 * changes have been saved.
	 */
	public void testEditFoodDiaryWithValidValues() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("27", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Nutrition Entries").click();
		wr = wc.getCurrentPage();
		
		assertEquals("iTrust - View Nutrition", wr.getTitle());
		assertTrue(wr.getText().contains("My Nutrition"));

		//Select entry here...
		WebTable table = wr.getTables()[1];
		 
		wr = table.getTableCell(2, 10).getLinkWith("Edit").click();
		assertEquals("iTrust - Edit Nutrition Entry", wr.getTitle());

		assertTrue(wr.getText().contains("My Nutrition"));
		
		WebForm patientForm = wr.getForms()[0];
		
		// tests if the patient information on the form is updated
		patientForm.setParameter("numServings","9");
		patientForm.setParameter("numCalories","127");
		patientForm.setParameter("numFat","68");
		patientForm.setParameter("numSodium","687");
		patientForm.setParameter("numCarbs","55.1");
		patientForm.setParameter("numSugar","112.4");
		patientForm.setParameter("numProtein","15.6");
		patientForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Entry Updated"));
	}

	/*
	 * Preconditions: Patient Aaron Hotchner an authenticated 
	 * user in the system.  He also has a food diary entry that
	 * contains the following  information:
	 * 
	 * Date: 4/13/2014 
	 * Food: Oreos 
	 * Meal: Snack 
	 * Servings: 53 
	 * Calories: 140 
	 * Fat: 7 
	 * Sodium: 90 
	 * Carbs: 21 
	 * Fiber: 1 
	 * Sugar: 13 
	 * Protein: 0
     *
	 * Date: 5/21/2013 
	 * Food: Cheese and Bean Dip  
	 * Meal: Breakfast 
	 * Servings: .75 
	 * Calories: 45 
	 * Fat: 2 
	 * Sodium: 230 
	 * Carbs: 5 
	 * Fiber: 2 
	 * Sugar: 0 
	 * Protein: 2 
     *
	 * Description: 
     * Select the entry from 04/13/2014 
     * 
	 * Make the following changes to the entry: 
     * -Servings to  17 
	 *     
     * Click the Save changes button 
	 * Expected results: The changes will not be changed and an error 
     * message will prompt the user of his mistaken 
     * servings total on the form. 
	 */
	public void testEditFoodDiaryWithInvalidValues() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("28", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Nutrition Entries").click();
		wr = wc.getCurrentPage();
		
		assertEquals("iTrust - View Nutrition", wr.getTitle());
		assertTrue(wr.getText().contains("My Nutrition"));

		//Select entry here...
		WebTable table = wr.getTables()[1];
		 
		wr = table.getTableCell(2, 10).getLinkWith("Edit").click();
		assertEquals("iTrust - Edit Nutrition Entry", wr.getTitle());

		assertTrue(wr.getText().contains("My Nutrition"));
		
		WebForm patientForm = wr.getForms()[0];

		// tests if the patient information on the form is updated
		patientForm.setParameter("numServings","-17");
		patientForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Servings must be a positive integer."));
	}

	/*
	 * Precondition: Patient Emily Prentiss is an
	 * authenticated user in the system. She has
	 * one food diary entry that contains the following information:
	 * 
	 * Date: 3/16/2014 
	 * Food: Chocolate Shake 
	 * Meal: Lunch 
	 * Servings: 2 
	 * Calories: 500 
	 * Fat: 23.5 
	 * Sodium: 259 
	 * Carbs: 66.5 
	 * Fiber: 0 
	 * Sugar: 42.4 
	 * Protein: 5.9 
	 * Description: 
	 * Select the existing entry from the table of diary 
	 * entries 
	 * 
	 * Make the following changes to the entry 
	 * Servings to 3 
     * Calories to 127
     * Fat to 68
     * Sodium to 687 
     * Carbs to 55.1 
     * Sugars to 112.4 
     * Protein to 15.6 
     *
	 * Click the Save changes button 
	 * Expected results: A prompt should appear notifying the user that the 
	 * changes have been saved.
	 */
	public void testEditFoodDiaryWithValidValues2() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("27", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Nutrition Entries").click();
		wr = wc.getCurrentPage();
		
		assertEquals("iTrust - View Nutrition", wr.getTitle());
		assertTrue(wr.getText().contains("My Nutrition"));

		//Select entry here...
		WebTable table = wr.getTables()[1];
		 
		wr = table.getTableCell(2, 10).getLinkWith("Edit").click();
		assertEquals("iTrust - Edit Nutrition Entry", wr.getTitle());

		assertTrue(wr.getText().contains("My Nutrition"));
		
		WebForm patientForm = wr.getForms()[0];
		
		// tests if the patient information on the form is updated
		patientForm.setParameter("numServings","3");
		patientForm.setParameter("numCalories","127");
		patientForm.setParameter("numFat","68");
		patientForm.setParameter("numSodium","687");
		patientForm.setParameter("numCarbs","55.1");
		patientForm.setParameter("numSugar","112.4");
		patientForm.setParameter("numProtein","15.6");
		patientForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Entry Updated"));
	}

	/*
	 * Preconditions: Patient Aaron Hotchner an authenticated 
	 * user in the system.  He also has a food diary entry that
	 * contains the following  information:
	 * 
	 * Date: 4/13/2014 
	 * Food: Oreos 
	 * Meal: Snack 
	 * Servings: 53 
	 * Calories: 140 
	 * Fat: 7 
	 * Sodium: 90 
	 * Carbs: 21 
	 * Fiber: 1 
	 * Sugar: 13 
	 * Protein: 0
     *
	 * Date: 5/21/2013 
	 * Food: Cheese and Bean Dip  
	 * Meal: Breakfast 
	 * Servings: .75 
	 * Calories: 45 
	 * Fat: 2 
	 * Sodium: 230 
	 * Carbs: 5 
	 * Fiber: 2 
	 * Sugar: 0 
	 * Protein: 2 
     *
	 * Description: 
     * Select the entry from 04/13/2014 
     * 
	 * Make the following changes to the entry: 
     * -Food to "" (blank field)
	 *     
     * Click the Save changes button 
     * 
	 * Expected results: The changes will not be changed and an error 
     * message will prompt the user warning him of the empty food name
	 */
	public void testEditFoodDiaryWithInvalidValues2() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("28", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Nutrition Entries").click();
		wr = wc.getCurrentPage();
		
		assertEquals("iTrust - View Nutrition", wr.getTitle());
		assertTrue(wr.getText().contains("My Nutrition"));

		//Select entry here...
		WebTable table = wr.getTables()[1];
		 
		wr = table.getTableCell(2, 10).getLinkWith("Edit").click();
		assertEquals("iTrust - Edit Nutrition Entry", wr.getTitle());

		assertTrue(wr.getText().contains("My Nutrition"));
		
		WebForm patientForm = wr.getForms()[0];
		
		// tests if the patient information on the form is updated
		patientForm.setParameter("foodName","");
		patientForm.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Food Name cannot be empty."));
	}

}
