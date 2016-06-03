package edu.ncsu.csc.itrust.seleniumtests;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class EditFoodDiaryEntryTest extends iTrustSeleniumTest {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		driver.setJavascriptEnabled(true);
	}

	@Test
	public void testEditFoodDiaryEntryValid() throws Exception {
		gen.patient33();

		login("33", "pw", "Patient");

		driver.findElementByLinkText("My Food Diary").click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 33L, 33L, "");
		driver.findElementById("filterShowAll").click();

		driver.findElementById("edit-1").click();

		assertTrue(driver.findElementById("edit-modal").isDisplayed());

		WebElement editForm = driver.findElementById("editEntryForm");
		//Clear fields to modify
		editForm.findElement(By.name("servings")).clear();
		editForm.findElement(By.name("calories")).clear();
		editForm.findElement(By.name("fat")).clear();
		editForm.findElement(By.name("sodium")).clear();
		editForm.findElement(By.name("carbohydrates")).clear();
		editForm.findElement(By.name("sugar")).clear();
		editForm.findElement(By.name("protein")).clear();

		//Enter new data
		editForm.findElement(By.name("servings")).sendKeys("3");
		editForm.findElement(By.name("calories")).sendKeys("1327");
		editForm.findElement(By.name("fat")).sendKeys("62.5");
		editForm.findElement(By.name("sodium")).sendKeys("687");
		editForm.findElement(By.name("carbohydrates")).sendKeys("176.4");
		editForm.findElement(By.name("sugar")).sendKeys("112.4");
		editForm.findElement(By.name("protein")).sendKeys("15.6");
		editForm.submit();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 33L, 33L, "");
		assertLogged(TransactionType.EDIT_FOOD_DIARY_ENTRY, 33L, 33L, "");
		assertTitleEquals("iTrust - View Food Diary");
		assertTrue(driver.findElementByClassName("alert").getText().contains("Entry updated successfully!"));

		WebElement entryRow = driver.findElementById("entry-1");
		List<WebElement> cells = entryRow.findElements(By.cssSelector("td"));
		assertEquals("03/06/2014", cells.get(0).getText());
		assertEquals("3.0", cells.get(3).getText());
		assertEquals("1327.0", cells.get(4).getText());
		assertEquals("62.5", cells.get(5).getText());
		assertEquals("687.0", cells.get(6).getText());
		assertEquals("176.4", cells.get(7).getText());
		assertEquals("112.4", cells.get(8).getText());
		assertEquals("15.6", cells.get(10).getText());
	}

	@Test
	public void testEditFoodDiaryEntryInvalid() throws Exception {
		gen.patient32();

		login("32", "pw", "Patient");

		driver.findElementByLinkText("My Food Diary").click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 32L, 32L, "");
		driver.findElementById("filterShowAll").click();

		driver.findElementById("edit-1").click();

		assertTrue(driver.findElementById("edit-modal").isDisplayed());

		WebElement editForm = driver.findElementById("editEntryForm");
		editForm.findElement(By.name("servings")).clear();
		editForm.findElement(By.name("servings")).sendKeys("-17");
		editForm.submit();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 32L, 32L, "");
		assertNotLogged(TransactionType.EDIT_FOOD_DIARY_ENTRY, 32L, 32L, "");
		
		assertTitleEquals("iTrust - View Food Diary");
		assertTrue(driver.findElementByClassName("alert").getText().contains("This form has not been validated correctly."));
		assertTrue(driver.findElementByClassName("alert").getText().contains("Servings"));

		WebElement entryRow = driver.findElementById("entry-1");
		List<WebElement> cells = entryRow.findElements(By.cssSelector("td"));
		assertEquals("53.0", cells.get(3).getText());
	}

	@Test
	public void testEditFoodDiaryEntryDate() throws Exception {
		gen.patient32();

		login("32", "pw", "Patient");

		driver.findElementByLinkText("My Food Diary").click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 32L, 32L, "");
		driver.findElementById("filterShowAll").click();

		driver.findElementById("edit-1").click();

		assertTrue(driver.findElementById("edit-modal").isDisplayed());

		WebElement editForm = driver.findElementById("editEntryForm");
		editForm.findElement(By.name("date")).clear();
		editForm.findElement(By.name("date")).sendKeys("04/13/2012");
		editForm.submit();
		
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 32L, 32L, "");
		assertLogged(TransactionType.EDIT_FOOD_DIARY_ENTRY, 32L ,32L, "");
		
		assertTitleEquals("iTrust - View Food Diary");
		assertTrue(driver.findElementByClassName("alert").getText().contains("Entry updated successfully!"));
		System.out.println(driver.findElementByClassName("alert").getText());

		WebElement diaryTable = driver.findElementById("diaryTable");
		List<WebElement> rows = diaryTable.findElements(By.tagName("tr"));

		assertEquals("04/13/2012", rows.get(4).findElements(By.tagName("td")).get(0).getText());
		assertEquals("Totals for 04/13/2012", rows.get(6).findElements(By.tagName("th")).get(0).getText());
	}
	
	@Test
	public void testPatientEditLabel() throws Exception {
		gen.patient33();
		
		login("33", "pw", "Patient");
		driver.findElementByLinkText("My Food Diary").click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 33L, 33L, "");
		driver.findElementById("filterShowAll").click();
		
		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(4, rows.size());
		WebElement row = rows.get(1);
		assertTrue(row.getText().contains("Chocolate Shake"));
		assertTrue(row.getText().contains("Lunch"));
		row = rows.get(2);
		assertTrue(row.getText().contains("No Label"));
		
		new Actions(driver).moveToElement(driver.findElement(By.id("label-2014-03-06"))).doubleClick().perform();
		WebElement form = driver.findElementByCssSelector("input[id='label-date'][value='2014-03-06']").findElement(By.xpath(".."));
		form.findElement(By.name("label-label")).sendKeys("Atkin's Diet");
		form.submit();
		
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 33L, 33L, "");
		assertLogged(TransactionType.EDIT_FOOD_DIARY_LABEL, 33L, 33L, "");
		
		table = driver.findElement(By.id("diaryTable"));
		rows = table.findElements(By.tagName("tr"));
		assertEquals(4, rows.size());
		row = rows.get(1);
		assertTrue(row.getText().contains("Chocolate Shake"));
		assertTrue(row.getText().contains("Lunch"));
		row = rows.get(2);
		assertTrue(row.getText().contains("Atkin's Diet"));
	}
	
	@Test
	public void testPatientAttemptsToEditComment() throws Exception {
		gen.patient33();
		
		login("33", "pw", "Patient");
		driver.findElementByLinkText("My Food Diary").click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 33L, 33L, "");
		driver.findElementById("filterShowAll").click();
		
		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(4, rows.size());
		WebElement row = rows.get(1);
		assertTrue(row.getText().contains("Chocolate Shake"));
		assertTrue(row.getText().contains("Lunch"));
		row = rows.get(2);
		assertTrue(row.getText().contains("No comment."));
		
		new Actions(driver).moveToElement(driver.findElement(By.tagName("span"))).doubleClick().perform();
		assertTextInBody("No comment.");
		assertNotLogged(TransactionType.EDIT_FOOD_DIARY_COMMENT, 33L, 33L, "");

	}
	
	@Test
	public void testPatientEditLabelToLong() throws Exception {
		gen.patient33();
		
		login("33", "pw", "Patient");
		driver.findElementByLinkText("My Food Diary").click();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 33L, 33L, "");
		driver.findElementById("filterShowAll").click();
		
		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(4, rows.size());
		WebElement row = rows.get(1);
		assertTrue(row.getText().contains("Chocolate Shake"));
		assertTrue(row.getText().contains("Lunch"));
		row = rows.get(2);
		assertTrue(row.getText().contains("No Label"));
		
		new Actions(driver).moveToElement(driver.findElement(By.id("label-2014-03-06"))).doubleClick().perform();
		WebElement form = driver.findElementByCssSelector("input[id='label-date'][value='2014-03-06']").findElement(By.xpath(".."));
		form.findElement(By.name("label-label")).sendKeys(StringUtils.repeat("a", 201));
		form.submit();
		assertLogged(TransactionType.VIEW_FOOD_DIARY, 33L, 33L, "");
		assertNotLogged(TransactionType.EDIT_FOOD_DIARY_LABEL, 33L, 33L, "");
		assertFalse(driver.findElementByTagName("body").getText().contains(StringUtils.repeat("a", 201)));
		assertTextInBody("Label must be less than or equal to 200 characters in length.");
	}
	
	@Test
	public void testHcpAttemptsToEditPatientLabel() throws Exception {
		gen.hcp11();
		gen.patient32();
		
		login("9000000014", "pw", "HCP");
		driver.findElementByLinkText("View Patient Food Diaries").click();
		selectPatientFromSearch("32");
		driver.findElementById("filterShowAll").click();
		assertLogged(TransactionType.HCP_VIEW_PATIENT_FOOD_DIARY, 9000000014L, 32L, "");
		
		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(7, rows.size());
		WebElement row = rows.get(1);
		assertTrue(row.getText().contains("Oreos"));
		assertTrue(row.getText().contains("Snack"));
		row = rows.get(4);
		assertTrue(row.getText().contains("Cheese and Bean Dip"));
		assertTrue(row.getText().contains("Breakfast"));
		row = rows.get(2);
		assertTrue(row.getText().contains("No Label"));
		row = rows.get(5);
		assertTrue(row.getText().contains("Atkin's Diet"));
		
		WebElement row5cols = row.findElement(By.tagName("td"));
		new Actions(driver).moveToElement(row5cols.findElement(By.tagName("span"))).doubleClick().perform();
		assertTextInBody("Atkin's Diet");
	}
	
	@Test
	public void testPatientAddComment() throws Exception {
		gen.hcp11();
		gen.patient32();
		
		login("9000000014", "pw", "HCP");
		driver.findElementByLinkText("View Patient Food Diaries").click();
		selectPatientFromSearch("32");
		driver.findElementById("filterShowAll").click();
		assertLogged(TransactionType.HCP_VIEW_PATIENT_FOOD_DIARY, 9000000014L, 32L, "");

		
		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(7, rows.size());
		WebElement row = rows.get(1);
		assertTrue(row.getText().contains("Oreos"));
		assertTrue(row.getText().contains("Snack"));
		row = rows.get(4);
		assertTrue(row.getText().contains("Cheese and Bean Dip"));
		assertTrue(row.getText().contains("Breakfast"));
		row = rows.get(2);
		assertTrue(row.getText().contains("No comment."));
		row = rows.get(5);
		assertTrue(row.getText().contains("Good job!"));
		
		new Actions(driver).moveToElement(driver.findElement(By.id("comment-2014-04-13"))).doubleClick().perform();
		WebElement form = driver.findElementByCssSelector("input[id='comment-date'][value='2014-04-13']").findElement(By.xpath(".."));
		form.findElement(By.name("comment-contents")).sendKeys("Good job!");
		form.submit();
		
		assertLogged(TransactionType.HCP_VIEW_PATIENT_FOOD_DIARY, 9000000014L, 32L, "");
		assertLogged(TransactionType.EDIT_FOOD_DIARY_COMMENT, 9000000014L, 32L, "");
		
		table = driver.findElement(By.id("diaryTable"));
		rows = table.findElements(By.tagName("tr"));
		assertEquals(7, rows.size());
		row = rows.get(1);
		assertTrue(row.getText().contains("Oreos"));
		assertTrue(row.getText().contains("Snack"));
		row = rows.get(4);
		assertTrue(row.getText().contains("Cheese and Bean Dip"));
		assertTrue(row.getText().contains("Breakfast"));
		row = rows.get(2);
		assertTrue(row.getText().contains("Good job!"));
		row = rows.get(5);
		assertTrue(row.getText().contains("Good job!"));
	}
	
	@Test
	public void testPatientEditComment() throws Exception {
		gen.hcp11();
		gen.patient32();
		
		login("9000000014", "pw", "HCP");
		driver.findElementByLinkText("View Patient Food Diaries").click();
		selectPatientFromSearch("32");
		driver.findElementById("filterShowAll").click();
		assertLogged(TransactionType.HCP_VIEW_PATIENT_FOOD_DIARY, 9000000014L, 32L, "");

		
		WebElement table = driver.findElement(By.id("diaryTable"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		assertEquals(7, rows.size());
		WebElement row = rows.get(1);
		assertTrue(row.getText().contains("Oreos"));
		assertTrue(row.getText().contains("Snack"));
		row = rows.get(4);
		assertTrue(row.getText().contains("Cheese and Bean Dip"));
		assertTrue(row.getText().contains("Breakfast"));
		row = rows.get(2);
		assertTrue(row.getText().contains("No comment."));
		row = rows.get(5);
		assertTrue(row.getText().contains("Good job!"));
		
		new Actions(driver).moveToElement(driver.findElement(By.id("comment-2013-05-21"))).doubleClick().perform();
		WebElement form = driver.findElementByCssSelector("input[id='comment-date'][value='2013-05-21']").findElement(By.xpath(".."));
		form.findElement(By.name("comment-contents")).sendKeys("Good job! Need to watch the carbs though.");
		form.submit();
		assertLogged(TransactionType.EDIT_FOOD_DIARY_COMMENT, 9000000014L, 32L, "");
		table = driver.findElement(By.id("diaryTable"));
		rows = table.findElements(By.tagName("tr"));
		assertEquals(7, rows.size());
		row = rows.get(1);
		assertTrue(row.getText().contains("Oreos"));
		assertTrue(row.getText().contains("Snack"));
		row = rows.get(4);
		assertTrue(row.getText().contains("Cheese and Bean Dip"));
		assertTrue(row.getText().contains("Breakfast"));
		row = rows.get(2);
		assertTrue(row.getText().contains("No comment."));
		row = rows.get(5);
		assertTrue(row.getText().contains("Good job! Need to watch the carbs though."));
	}
	
}
