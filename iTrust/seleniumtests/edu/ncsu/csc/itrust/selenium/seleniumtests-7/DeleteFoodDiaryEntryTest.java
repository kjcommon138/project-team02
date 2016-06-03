package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import edu.ncsu.csc.itrust.enums.TransactionType;
import static org.junit.Assert.*;

public class DeleteFoodDiaryEntryTest extends iTrustSeleniumTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		driver.setJavascriptEnabled(true);
	}

	@Test
	public void testDeleteFoodDiaryEntry() throws Exception {
		gen.patient31();

		login("31", "pw", "Patient");
		driver.findElement(By.linkText("My Food Diary")).click();
		assertTitleEquals("iTrust - View Food Diary");
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 31L, 31L, "");
		driver.findElementById("filterShowAll").click();
		
		driver.findElementById("delete-1").click();
		driver.findElementById("delete-confirm-btn").click();

		assertLogged(TransactionType.VIEW_FOOD_DIARY, 31L, 31L, "");
		assertLogged(TransactionType.DELETE_FOOD_DIARY_ENTRY, 31L, 31L, "");
		assertTextInBody("Entry deleted successfully!");
		assertTextInBody("Mango Passionfruit Juice");
		assertTextInBody("Lunch");
		assertFalse(driver.findElement(By.id("diaryTable")).getText().contains("Dinner"));
	}

	@Test
	public void testDeleteLastFoodDiaryEntry() throws Exception {
		gen.patient33();

		login("33", "pw", "Patient");

		driver.findElement(By.linkText("My Food Diary")).click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 33L, 33L, "");
		driver.findElementById("filterShowAll").click();

		driver.findElementById("delete-1").click();
		driver.findElementById("delete-confirm-btn").click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 33L, 33L, "");
		assertLogged(TransactionType.DELETE_FOOD_DIARY_ENTRY, 33L, 33L, "");
		assertTextInBody("Entry deleted successfully!");
		assertTextInBody("There are no entries in your food diary for the given date range.");
		
		try {
			driver.findElement(By.id("diaryTable"));
			fail("Expected Exception not encountered");
		} catch (NoSuchElementException e) {
			assertTrue("Expected Exception encountered.", true);
		}
	}
}
