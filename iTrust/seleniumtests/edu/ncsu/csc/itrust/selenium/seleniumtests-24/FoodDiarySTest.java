package edu.ncsu.csc.itrust.seleniumtests;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;


public class FoodDiarySTest extends iTrustSeleniumTest {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		gen.clearAllTables();
		gen.standardData();
	}
	
	@Override
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	@Test
	public void testemptyFoodDiaryAcceptanceTest1() throws Exception {
		WebDriver driver = login("133708", "pw");
		driver.findElement(By.linkText("Add Food Diary Entry")).click();

		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("02/14/2015");
		driver.findElement(By.name("foodName")).sendKeys("Fruity Pebbles");
	    Select oSelection = new Select(driver.findElement(By.name("mealType")));
        oSelection.selectByVisibleText("Dinner");
		driver.findElement(By.name("servings")).sendKeys("7");
		driver.findElement(By.name("calories")).sendKeys("110");
		driver.findElement(By.name("fat")).sendKeys("1");
		driver.findElement(By.name("carbs")).sendKeys("24");
		driver.findElement(By.name("protein")).sendKeys("1");
		driver.findElement(By.name("fiber")).sendKeys("0");
		driver.findElement(By.name("sugars")).sendKeys("11");
		driver.findElement(By.name("sodium")).sendKeys("170");
		driver.findElement(By.name("action")).click();

		assertTrue(driver.getTitle().equals("iTrust - View My Food Diary"));
		assertTrue(driver.getPageSource().contains("Fruity Pebbles"));
	}
	
	@Test
	public void testemptyFoodDiaryAcceptanceTest2() throws Exception {
		WebDriver driver = login("133709", "pw");
		driver.findElement(By.linkText("Add Food Diary Entry")).click();

		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("11/12/2014");
		driver.findElement(By.name("foodName")).sendKeys("Cookie Dough Ice Cream");
	    Select oSelection = new Select(driver.findElement(By.name("mealType")));
        oSelection.selectByVisibleText("Snack");
		driver.findElement(By.name("servings")).sendKeys("0.5");
		driver.findElement(By.name("calories")).sendKeys("160");
		driver.findElement(By.name("fat")).sendKeys("8");
		driver.findElement(By.name("carbs")).sendKeys("21");
		driver.findElement(By.name("protein")).sendKeys("2");
		driver.findElement(By.name("fiber")).sendKeys("0");
		driver.findElement(By.name("sugars")).sendKeys("16");
		driver.findElement(By.name("sodium")).sendKeys("45");
		driver.findElement(By.name("action")).click();


		assertTrue(driver.getTitle().equals("iTrust - View My Food Diary"));
		assertTrue(driver.getPageSource().contains("Cookie Dough Ice Cream"));
		assertTrue(driver.getPageSource().contains("Hot dog"));
	}
	
	@Test
	public void testHCPViewPatientDiaryAcceptanceTest3() throws Exception {
		WebDriver driver = login("69", "pw");
		driver.findElement(By.linkText("Patient Food Diary")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("133710");
		driver.findElement(By.xpath(".//*[@value='133710']")).submit();
		assertTrue(driver.getPageSource().contains("Oreos") && driver.getPageSource().contains("Cheese and Bean Dip"));
	}
	
	@Test
	public void testInvalidDate1() throws Exception {
		WebDriver driver = login("133709", "pw");
		driver.findElement(By.linkText("Add Food Diary Entry")).click();

		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("11/12/13/2014");
		driver.findElement(By.name("action")).click();

		assertTrue(driver.getPageSource().contains("Invalid Date: Please enter date as mm/dd/yyyy"));
	}
	
	@Test
	public void testInvalidDate2() throws Exception {
		WebDriver driver = login("133709", "pw");
		driver.findElement(By.linkText("Add Food Diary Entry")).click();

		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("11/2014");
		driver.findElement(By.name("action")).click();

		assertTrue(driver.getPageSource().contains("Invalid Date: Please enter date as mm/dd/yyyy"));
	}
	
	@Test
	public void testInvalidInput1() throws Exception {
		WebDriver driver = login("133709", "pw");
		driver.findElement(By.linkText("Add Food Diary Entry")).click();

		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("11/12/2014");
		driver.findElement(By.name("foodName")).sendKeys("Cookie Dough Ice Cream");
	    Select oSelection = new Select(driver.findElement(By.name("mealType")));
        oSelection.selectByVisibleText("Snack");
		driver.findElement(By.name("servings")).sendKeys("0.5");
		driver.findElement(By.name("calories")).sendKeys("-160");
		driver.findElement(By.name("fat")).sendKeys("8");
		driver.findElement(By.name("carbs")).sendKeys("21");
		driver.findElement(By.name("protein")).sendKeys("2");
		driver.findElement(By.name("fiber")).sendKeys("0");
		driver.findElement(By.name("sugars")).sendKeys("16");
		driver.findElement(By.name("sodium")).sendKeys("45");
		driver.findElement(By.name("action")).click();

		assertTrue(driver.getPageSource().contains("Calories must be positive or zero."));
	}
	
	@Test
	public void testInvalidInput2() throws Exception {
		WebDriver driver = login("133709", "pw");
		driver.findElement(By.linkText("Add Food Diary Entry")).click();

		driver.findElement(By.name("date")).clear();
		driver.findElement(By.name("date")).sendKeys("11/12/2014");
		driver.findElement(By.name("foodName")).sendKeys("");
	    Select oSelection = new Select(driver.findElement(By.name("mealType")));
        oSelection.selectByVisibleText("Snack");
		driver.findElement(By.name("servings")).sendKeys("0.5");
		driver.findElement(By.name("calories")).sendKeys("-160");
		driver.findElement(By.name("fat")).sendKeys("8");
		driver.findElement(By.name("carbs")).sendKeys("21");
		driver.findElement(By.name("protein")).sendKeys("2");
		driver.findElement(By.name("fiber")).sendKeys("0");
		driver.findElement(By.name("sugars")).sendKeys("16");
		driver.findElement(By.name("sodium")).sendKeys("45");
		driver.findElement(By.name("action")).click();

		assertTrue(driver.getPageSource().contains("Please enter the name of the food."));
	}
	
	@Test
	public void testEditValidInputAcceptanceTest1() throws Exception {
	

		WebDriver driver = login("133711", "pw");
		driver.findElement(By.linkText("View My Food Diary")).click();
		driver.findElement(By.xpath("//*[@id='iTrustContent']/table/tbody/tr[2]/td[13]/form/input[2]")).click();
		driver.findElement(By.name("servings")).clear();
		driver.findElement(By.name("servings")).sendKeys("3");
		driver.findElement(By.name("calories")).clear();
		driver.findElement(By.name("calories")).sendKeys("1327");
		driver.findElement(By.name("fat")).clear();
		driver.findElement(By.name("fat")).sendKeys("62.5");
		driver.findElement(By.name("sodium")).clear();
		driver.findElement(By.name("sodium")).sendKeys("687");
		driver.findElement(By.name("carbs")).clear();
		driver.findElement(By.name("carbs")).sendKeys("176.4");
		driver.findElement(By.name("sugars")).clear();
		driver.findElement(By.name("sugars")).sendKeys("112.4");
		driver.findElement(By.name("protein")).clear();
		driver.findElement(By.name("protein")).sendKeys("15.6");
		driver.findElement(By.name("action")).click();
		
		String title = driver.getTitle();
		assertEquals("iTrust - View My Food Diary", title);
		assertTrue(driver.getPageSource().contains("1327"));
		assertFalse(driver.getPageSource().contains("500"));
	}	
	
	@Test
	public void testEditValidInputAcceptanceTest2() throws Exception {
		WebDriver driver = login("133710", "pw");
		driver.findElement(By.linkText("View My Food Diary")).click();
		driver.findElement(By.xpath("//*[@id='iTrustContent']/table/tbody/tr[2]/td[13]/form/input[2]")).click();
		

		String title = driver.getTitle();
		assertEquals("iTrust - Edit Food Entry", title);
		driver.findElement(By.name("servings")).clear();
		driver.findElement(By.name("servings")).sendKeys("-17");
		driver.findElement(By.name("action")).click();
		
		title = driver.getTitle();
		assertEquals("iTrust - View My Food Diary", title);
		assertTrue(driver.getPageSource().contains("Error"));
		assertTrue(driver.getPageSource().contains("Serving size must be positive."));
	}
	
	@Test
	public void testEditValidInputAcceptanceTest3() throws Exception {
	WebDriver driver = login("133709", "pw");
	driver.findElement(By.linkText("View My Food Diary")).click();
	assertTrue(driver.getPageSource().contains("Hot dog"));
	driver.findElement(By.xpath("//*[@id='iTrustContent']/table/tbody/tr[3]/td[14]/form/input[2]")).click();
	

	String title = driver.getTitle();
	assertEquals("iTrust - Delete Food Entry", title);
	
	driver.findElement(By.name("CONFIRMDELETE")).submit();
	title = driver.getTitle();
	assertEquals("iTrust - View My Food Diary", title);
	assertFalse(driver.getPageSource().contains("Hot dog"));
}
	
	@Test
	public void testDeleteEntryConfirmation() throws Exception {
	WebDriver driver = login("133710", "pw");
	driver.findElement(By.linkText("View My Food Diary")).click();
	assertTrue(driver.getPageSource().contains("Oreos"));
	driver.findElement(By.xpath("//*[@id='iTrustContent']/table/tbody/tr[2]/td[14]/form/input[2]")).click();
	

	String title = driver.getTitle();
	assertEquals("iTrust - Delete Food Entry", title);
	
	driver.findElement(By.name("CANCEL")).submit();
	title = driver.getTitle();
	assertEquals("iTrust - View My Food Diary", title);
	assertTrue(driver.getPageSource().contains("Oreos"));
	}
	
	@Test
	public void testEditNoChange() throws Exception {
		WebDriver driver = login("133710", "pw");
		driver.findElement(By.linkText("View My Food Diary")).click();
		assertTrue(driver.getPageSource().contains("Oreos"));
		driver.findElement(By.xpath("//*[@id='iTrustContent']/table/tbody/tr[2]/td[13]/form/input[2]")).click();
		

		String title = driver.getTitle();
		assertEquals("iTrust - Edit Food Entry", title);
		
		driver.findElement(By.name("action")).submit();
		title = driver.getTitle();
		assertEquals("iTrust - View My Food Diary", title);
		assertTrue(driver.getPageSource().contains("Oreos"));
		//assertFalse(driver.getPageSource().contains("Error"));
	}
//	
//	@Test
//	public void testAddFoodWithTag() throws Exception {
//		WebDriver driver = login("133708", "pw");
//		driver.findElement(By.linkText("Add Food Diary Entry")).click();
//
//		driver.findElement(By.name("date")).clear();
//		driver.findElement(By.name("date")).sendKeys("02/14/2015");
//		driver.findElement(By.name("foodName")).sendKeys("Fruity Pebbles");
//	    Select oSelection = new Select(driver.findElement(By.name("mealType")));
//        oSelection.selectByVisibleText("Dinner");
//		driver.findElement(By.name("servings")).sendKeys("7");
//		driver.findElement(By.name("calories")).sendKeys("110");
//		driver.findElement(By.name("fat")).sendKeys("1");
//		driver.findElement(By.name("carbs")).sendKeys("24");
//		driver.findElement(By.name("protein")).sendKeys("1");
//		driver.findElement(By.name("fiber")).sendKeys("0");
//		driver.findElement(By.name("sugars")).sendKeys("11");
//		driver.findElement(By.name("sodium")).sendKeys("170");
//		driver.findElement(By.name("tag")).sendKeys("#cereal");
//		driver.findElement(By.name("action")).click();
//
//		assertTrue(driver.getTitle().equals("iTrust - View My Food Diary"));
//		assertTrue(driver.getPageSource().contains("#cereal"));
//	}
//	
//	@Test
//	public void testEditFoodTag() throws Exception {
//		WebDriver driver = login("133709", "pw");
//		driver.findElement(By.linkText("View My Food Diary")).click();
//		driver.findElement(By.xpath("//*[@id='iTrustContent']/table/tbody/tr[2]/td[13]/form/input[2]")).click();
//		
//
//		String title = driver.getTitle();
//		assertEquals("iTrust - Edit Food Entry", title);
//
//		driver.findElement(By.name("tag")).sendKeys("#juice");
//		driver.findElement(By.name("action")).submit();
//		title = driver.getTitle();
//		assertEquals("iTrust - View My Food Diary", title);
//		assertTrue(driver.getPageSource().contains("Mango Passionfruit Juice"));
//		assertTrue(driver.getPageSource().contains("#juice"));
//		//assertFalse(driver.getPageSource().contains("Error"));
//	}
//	
//	@Test
//	public void testInvalidTag() throws Exception {
//		WebDriver driver = login("133709", "pw");
//		driver.findElement(By.linkText("Add Food Diary Entry")).click();
//
//		driver.findElement(By.name("date")).clear();
//		driver.findElement(By.name("date")).sendKeys("12/30/2012");
//		driver.findElement(By.name("foodName")).sendKeys("Potato Salad");
//	    Select oSelection = new Select(driver.findElement(By.name("mealType")));
//        oSelection.selectByVisibleText("Lunch");
//		driver.findElement(By.name("servings")).sendKeys("1");
//		driver.findElement(By.name("calories")).sendKeys("90");
//		driver.findElement(By.name("fat")).sendKeys("10");
//		driver.findElement(By.name("carbs")).sendKeys("60");
//		driver.findElement(By.name("protein")).sendKeys("1");
//		driver.findElement(By.name("fiber")).sendKeys("0");
//		driver.findElement(By.name("sugars")).sendKeys("0");
//		driver.findElement(By.name("sodium")).sendKeys("20");
//		driver.findElement(By.name("tag")).sendKeys("#!@a bad tag!");
//		driver.findElement(By.name("action")).click();
//
//		assertTrue(driver.getTitle().equals("iTrust - Add Food Entry"));
//		assertTrue(driver.getPageSource().contains("Tags must be alphanumeric"));
//	}
}
