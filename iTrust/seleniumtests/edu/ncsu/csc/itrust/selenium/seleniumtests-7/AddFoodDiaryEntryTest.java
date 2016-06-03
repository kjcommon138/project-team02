package edu.ncsu.csc.itrust.seleniumtests;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;
import static org.junit.Assert.*;

public class AddFoodDiaryEntryTest extends iTrustSeleniumTest {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		driver.setJavascriptEnabled(true);
	}

	@Test
	public void testAddEntryToEmpty() throws Exception {
		gen.patient30(); //Derek Morgan

		login("30", "pw", "Patient");

		driver.findElementByLinkText("My Food Diary").click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 30L, 30L, "");
		driver.findElementByPartialLinkText("Add a New Diary Entry").click();

		assertTrue(driver.findElementById("edit-modal").isDisplayed());

		WebElement addForm = driver.findElementById("editEntryForm");
		addForm.findElement(By.name("date")).sendKeys("02/14/2014");
		new Select(addForm.findElement(By.name("meal"))).selectByVisibleText("Dinner");
		addForm.findElement(By.name("food")).sendKeys("Fruity Pebbles");
		addForm.findElement(By.name("servings")).sendKeys("7");
		addForm.findElement(By.name("calories")).sendKeys("110");
		addForm.findElement(By.name("fat")).sendKeys("1");
		addForm.findElement(By.name("sodium")).sendKeys("170");
		addForm.findElement(By.name("carbohydrates")).sendKeys("24");
		addForm.findElement(By.name("sugar")).sendKeys("0");
		addForm.findElement(By.name("fiber")).sendKeys("11");
		addForm.findElement(By.name("protein")).sendKeys("1");
		addForm.submit();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 30L, 30L, "");
		assertLogged(TransactionType.CREATE_FOOD_DIARY_ENTRY, 30L, 30L, "");
		assertFalse(driver.getPageSource().contains("This form has not been validated correctly"));
		assertTitleEquals("iTrust - View Food Diary");

		driver.findElementById("filterShowAll").click();

		List<WebElement> rows = driver.findElementsByCssSelector(".fTable > tbody tr");
		assertEquals("02/14/2014", rows.get(0).findElements(By.cssSelector("td")).get(0).getText());
		assertEquals("Dinner", rows.get(0).findElements(By.cssSelector("td")).get(2).getText());

	}

	@Test
	public void testAddEntryToNonEmpty() throws Exception {
		gen.patient31(); //Jennifer Jareau

		login("31", "pw", "Patient");

		driver.findElementByLinkText("My Food Diary").click();
		driver.findElementById("filterShowAll").click();
		driver.findElementByPartialLinkText("Add a New Diary Entry").click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 31L, 31L, "");
		assertTrue(driver.findElementById("edit-modal").isDisplayed());

		WebElement addForm = driver.findElementById("editEntryForm");
		addForm.findElement(By.name("date")).sendKeys("11/12/2014");
		new Select(addForm.findElement(By.name("meal"))).selectByVisibleText("Snack");
		addForm.findElement(By.name("food")).sendKeys("Cookie Dough Ice Cream");
		addForm.findElement(By.name("servings")).sendKeys("0.5");
		addForm.findElement(By.name("calories")).sendKeys("160");
		addForm.findElement(By.name("fat")).sendKeys("8");
		addForm.findElement(By.name("sodium")).sendKeys("45");
		addForm.findElement(By.name("carbohydrates")).sendKeys("21");
		addForm.findElement(By.name("sugar")).sendKeys("0");
		addForm.findElement(By.name("fiber")).sendKeys("16");
		addForm.findElement(By.name("protein")).sendKeys("2");
		addForm.submit();
		assertLogged(TransactionType.CREATE_FOOD_DIARY_ENTRY, 31L, 31L, "");
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 31L, 31L, "");
		assertFalse(driver.getPageSource().contains("This form has not been validated correctly"));
		assertTitleEquals("iTrust - View Food Diary");

		driver.findElementById("filterShowAll").click();

		List<WebElement> rows = driver.findElementsByCssSelector(".fTable > tbody tr");
		assertEquals("11/12/2014", rows.get(0).findElements(By.cssSelector("td")).get(0).getText());
		assertEquals("Snack", rows.get(0).findElements(By.cssSelector("td")).get(2).getText());
	}

	@Test
	public void testAddEntryFutureDate() throws Exception {
		gen.patient1(); //Random Person

		login("1", "pw", "Patient");

		driver.findElementByLinkText("My Food Diary").click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 1L, 1L, "");
		driver.findElementById("filterShowAll").click();
		driver.findElementByPartialLinkText("Add a New Diary Entry").click();
		assertTrue(driver.findElementById("edit-modal").isDisplayed());

		WebElement addForm = driver.findElementById("editEntryForm");
		driver.executeScript("$('#date').val('12/31/2200')"); //Set the date via javascript to get around clientside validation
		new Select(addForm.findElement(By.name("meal"))).selectByVisibleText("Snack");
		addForm.findElement(By.name("food")).sendKeys("Doughnut");
		addForm.findElement(By.name("servings")).sendKeys("3");
		addForm.findElement(By.name("calories")).sendKeys("300");
		addForm.findElement(By.name("fat")).sendKeys("12");
		addForm.findElement(By.name("sodium")).sendKeys("50");
		addForm.findElement(By.name("carbohydrates")).sendKeys("18");
		addForm.findElement(By.name("sugar")).sendKeys("36");
		addForm.findElement(By.name("fiber")).sendKeys("3");
		addForm.findElement(By.name("protein")).sendKeys("0");
		addForm.submit();

		assertLogged(TransactionType.VIEW_FOOD_DIARY, 1L, 1L, "");
		assertNotLogged(TransactionType.CREATE_FOOD_DIARY_ENTRY, 1L, 1L, "");

		assertTitleEquals("iTrust - View Food Diary");
		assertTrue(driver.findElementByClassName("alert-danger").getText().contains("Entry date cannot be a future date!"));
		assertTrue(driver.findElementByClassName("alert-danger").getText().contains("This form has not been validated correctly"));
	}

	@Test
	public void testAddEntryNegativeValue() throws Exception {
		gen.patient1(); //Random Person
		login("1", "pw", "Patient");

		driver.findElementByLinkText("My Food Diary").click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 1L, 1L, "");
		driver.findElementById("filterShowAll").click();
		driver.findElementByPartialLinkText("Add a New Diary Entry").click();
		assertTrue(driver.findElementById("edit-modal").isDisplayed());

		WebElement addForm = driver.findElementById("editEntryForm");
		addForm.findElement(By.name("date")).sendKeys("11/12/2014");
		new Select(addForm.findElement(By.name("meal"))).selectByVisibleText("Lunch");
		addForm.findElement(By.name("food")).sendKeys("Pepperoni Pizza");
		addForm.findElement(By.name("servings")).sendKeys("2");
		addForm.findElement(By.name("calories")).sendKeys("240");
		addForm.findElement(By.name("fat")).sendKeys("10");
		addForm.findElement(By.name("sodium")).sendKeys("70");
		addForm.findElement(By.name("carbohydrates")).sendKeys("28");
		addForm.findElement(By.name("sugar")).sendKeys("2");
		addForm.findElement(By.name("fiber")).sendKeys("-9");
		addForm.findElement(By.name("protein")).sendKeys("5");
		addForm.submit();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 1L, 1L, "");
		assertNotLogged(TransactionType.CREATE_FOOD_DIARY_ENTRY, 1L, 1L, "");

		assertTitleEquals("iTrust - View Food Diary");
		assertTrue(driver.findElementByClassName("alert-danger").getText().contains("Must be a zero or positive double"));
		assertTrue(driver.findElementByClassName("alert-danger").getText().contains("This form has not been validated correctly"));
	}

	@Test
	public void testAddEntryEmptyName() throws Exception {
		gen.patient1(); //Random Person
		login("1", "pw", "Patient");

		driver.findElementByLinkText("My Food Diary").click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 1L, 1L, "");
		driver.findElementById("filterShowAll").click();
		driver.findElementByPartialLinkText("Add a New Diary Entry").click();
		assertTrue(driver.findElementById("edit-modal").isDisplayed());

		WebElement addForm = driver.findElementById("editEntryForm");
		addForm.findElement(By.name("date")).sendKeys("11/12/2014");
		new Select(addForm.findElement(By.name("meal"))).selectByVisibleText("Dinner");
		addForm.findElement(By.name("food")).sendKeys(" ");
		addForm.findElement(By.name("servings")).sendKeys("1");
		addForm.findElement(By.name("calories")).sendKeys("130");
		addForm.findElement(By.name("fat")).sendKeys("7");
		addForm.findElement(By.name("sodium")).sendKeys("15");
		addForm.findElement(By.name("carbohydrates")).sendKeys("9");
		addForm.findElement(By.name("sugar")).sendKeys("8");
		addForm.findElement(By.name("fiber")).sendKeys("2");
		addForm.findElement(By.name("protein")).sendKeys("4");
		addForm.submit();
		
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 1L, 1L, "");
		assertNotLogged(TransactionType.CREATE_FOOD_DIARY_ENTRY, 1L,1L, "");

		assertTitleEquals("iTrust - View Food Diary");
		assertTrue(driver.findElementByClassName("alert-danger").getText().contains("Food name"));
		assertTrue(driver.findElementByClassName("alert-danger").getText().contains("This form has not been validated correctly"));
	}

	@Test
	public void testAddEntryManyNull() throws Exception {
		gen.patient1(); //Random Person
		login("1", "pw", "Patient");

		driver.findElementByLinkText("My Food Diary").click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 1L, 1L, "");
		driver.findElementById("filterShowAll").click();
		driver.findElementByPartialLinkText("Add a New Diary Entry").click();
		assertTrue(driver.findElementById("edit-modal").isDisplayed());

		WebElement addForm = driver.findElementById("editEntryForm");
		addForm.findElement(By.name("date")).sendKeys("11/13/2014");
		new Select(addForm.findElement(By.name("meal"))).selectByVisibleText("Breakfast");
		addForm.findElement(By.name("food")).sendKeys("Waffles");
		addForm.findElement(By.name("servings")).clear();
		addForm.findElement(By.name("calories")).clear();
		addForm.findElement(By.name("fat")).clear();
		addForm.findElement(By.name("sodium")).clear();
		addForm.findElement(By.name("carbohydrates")).clear();
		addForm.findElement(By.name("sugar")).clear();
		addForm.findElement(By.name("fiber")).clear();
		addForm.findElement(By.name("protein")).clear();
		addForm.submit();

		assertLogged(TransactionType.VIEW_FOOD_DIARY, 1L, 1L, "");
		assertNotLogged(TransactionType.CREATE_FOOD_DIARY_ENTRY, 1L, 1L, "");
		
		assertTitleEquals("iTrust - View Food Diary");
		assertTrue(driver.findElementByClassName("alert-danger").getText().contains("make sure to fill out all fields"));
		assertTrue(driver.findElementByClassName("alert-danger").getText().contains("This form has not been validated correctly"));
	}


}
