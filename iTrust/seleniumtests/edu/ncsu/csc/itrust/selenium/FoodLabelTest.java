package edu.ncsu.csc.itrust.selenium;

import java.util.Iterator;
import java.util.List;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class FoodLabelTest extends iTrustSeleniumTest{
	private WebDriver driver = null;
	
	@Before
	public void setUp() throws Exception {
	    // Create a new instance of the driver
	    driver = new HtmlUnitDriver();
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testViewFoodLabelPatient() throws Exception {
		// Patient 1 logs in.
		driver = (HtmlUnitDriver)login("1", "pw");
		assertTrue(driver.getTitle().contains("iTrust - Patient Home"));
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// Patient 1 moves to the view nutrition page.
		driver.findElement(By.linkText("View Nutrition Entries")).click();
		assertTrue(driver.getPageSource().contains("iTrust - View Nutrition"));
		
		// Patient 1 checks to see if their food label is viewable.
		WebElement tableElem = driver.findElement(By.name("FoodDiaryTable"));
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		int x = 0;
		while(rowsOnTable.hasNext()) {
			WebElement row = rowsOnTable.next(); 
			if(x == 0 && (row.getText().contains("06/12/2007"))) {
				assertTrue(row.getText().contains("Atkins Diet"));
			}
		}
	}
	
	public void testEditFoodLabelPatient() throws Exception {
		// Patient 1 logs in.
		driver = (HtmlUnitDriver)login("1", "pw");
		assertTrue(driver.getTitle().contains("iTrust - Patient Home"));
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		// Patient 1 moves to the view nutrition page.
		driver.findElement(By.linkText("View Nutrition Entries")).click();
		assertTrue(driver.getPageSource().contains("iTrust - View Nutrition"));
		
		// Patient 1 checks to see if their food label is viewable.
		WebElement tableElem = driver.findElement(By.name("FoodDiaryTable"));
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		int x = 0;
		while(rowsOnTable.hasNext()) {
			WebElement row = rowsOnTable.next(); 
			if(x == 0 && (row.getText().contains("06/12/2007"))) {
				assertTrue(row.getText().contains("Atkins Diet"));
			}
		}

		// Patient 1 edits their food label for 06/12/2007
		driver.findElement(By.id("btnEdit0")).click();
		driver.findElement(By.name("label")).sendKeys("Weight Watchers");
		driver.findElement(By.id("btnSubmit0")).click();
		
		// Patient 1 checks to see if their food label was updated.
		WebElement tableElem2 = driver.findElement(By.name("FoodDiaryTable"));
		List<WebElement> tableData2 = tableElem2.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable2 = tableData2.iterator();
		int y = 0;
		while(rowsOnTable2.hasNext()) {
			WebElement row = rowsOnTable2.next(); 
			if(y == 0 && (row.getText().contains("06/12/2007"))) {
				assertTrue(row.getText().contains("Weight Watchers"));
			}
		}
	}
	
	public void testViewFoodLabelHCP() throws Exception {
		// HCP 9000000000 logs in.
		driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertTrue(driver.getTitle().contains("iTrust - HCP Home"));
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		// HCP 9000000000 moves to the view nutrition page.
		driver.findElement(By.linkText("View Patient Nutrition")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.cssSelector("input[value='1']")).submit();
		assertTrue(driver.getPageSource().contains("iTrust - View Patient Nutrition"));
		
		// HCP 9000000000 checks to see if the food label is viewable for patient 1.
		WebElement tableElem = driver.findElement(By.id("FoodDiaryTable"));
		List<WebElement> tableData = tableElem.findElements(By.tagName("tr"));
		Iterator<WebElement> rowsOnTable = tableData.iterator();
		int x = 0;
		while(rowsOnTable.hasNext()) {
			WebElement row = rowsOnTable.next(); 
			if(x == 0 && (row.getText().contains("06/12/2007"))) {
				assertTrue(row.getText().contains("Atkins Diet"));
			}
		}
	}
}
