package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Use Case 68
 */
public class DeleteMyNutritionTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		HttpUnitOptions.setScriptingEnabled(false);
		gen.clearAllTables();
		gen.standardData();		
	}
	
	/*
	 * Precondition: Patient Jennifer Jareau is an authenticated 
	 * user in the system. She also has a food diary entry that  
	 * contains the following information: 
	 *
	 * Date: 9/30/2012 
	 * Food: Hot Dog 
	 * Meal: Breakfast 
	 * Servings: 4 
	 * Calories: 80 
	 * Fat: 5 
	 * Sodium: 480 
	 * Carbs: 2 
	 * Fiber: 0 
	 * Sugar: 0 
	 * Protein: 5 
 	 *
	 * Date: 9/30/2012 
	 * Food: Mango 
	 * Passionfruit Juice  
	 * Meal: Lunch 
 	 * Servings: 1.2 
	 * Calories: 130 
	 * Fat: 0 
	 * Sodium: 25 
	 * Carbs: 32 
	 * Fiber: 0 
	 * Sugar: 29 
	 * Protein: 1 
	 *
	 * Description: 
	 * Select the Breakfast entry from 09/30/2012 
	 *
	 * The user clicks “Yes, I want to delete this entry”.  
	 * Expected results: The breakfast entry will be deleted from the table 
	 * as it gets updated on the food diary page and the daily totals will be 
	 * updated as well.
	 */
	public void testEditFoodDiaryWithValidValues() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		
		WebConversation wc = login("29", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Edit/Delete Nutrition Entries").click();
		wr = wc.getCurrentPage();
		
		assertEquals("iTrust - Edit Nutrition", wr.getTitle());
		assertTrue(wr.getText().contains("My Nutrition"));
		
		//Select entry here...
		wr = wr.getLinkWith("Delete #1").click();
		
		assertEquals("iTrust - Delete Nutrition Entry", wr.getTitle());
		assertTrue(wr.getText().contains("Diary entry successfully deleted."));
	}

}
