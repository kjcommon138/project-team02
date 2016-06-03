package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;


public class PersonnelUseCaseTest extends iTrustSeleniumTest {
	
	protected void setUp() throws Exception {
		//super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**ADDRESS*/
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	
	/**
	 * testAddER
	 * @throws Exception
	 */
	public void testAddER() throws Exception {
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("9000000001");
        driver.findElement(By.name("j_password")).sendKeys("pw");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
      
        //Check to make sure this is the correct page
        assertEquals("iTrust - Admin Home", driver.getTitle());
		
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Add ER")).click();
	    assertEquals("iTrust - Add ER", driver.getTitle());
	    
	    //quit driver
	    driver.quit();
	}
	
	/**
	 * testCreateER
	 * @throws Exception
	 */
	public void testCreateER() throws Exception {
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("9000000001");
        driver.findElement(By.name("j_password")).sendKeys("pw");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
      
        //Check to make sure this is the correct page
        assertEquals("iTrust - Admin Home", driver.getTitle());
		
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Add ER")).click();
	    driver.findElement(By.name("firstName")).clear();
	    driver.findElement(By.name("firstName")).sendKeys("Nick");
	    driver.findElement(By.name("lastName")).clear();
	    driver.findElement(By.name("lastName")).sendKeys("Oftime");
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("nick@iTrust.com");
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("nick@itrust.com");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("New ER Nick Oftime succesfully added!", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	
	    //quit driver
	    driver.quit();
	}
	
	/**
	 * testEditERDetails
	 * @throws Exception
	 */
	public void testEditERDetails() throws Exception {
		//gen.surveyResults();
		
		// Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        //And now use this to visit iTrust
        driver.get(ADDRESS);
        
	    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        assertEquals("iTrust - Login", driver.getTitle());

        //Find the text input element by its name
        driver.findElement(By.name("j_username")).sendKeys("9000000001");
        driver.findElement(By.name("j_password")).sendKeys("pw");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
      
        //Check to make sure this is the correct page
        assertEquals("iTrust - Admin Home", driver.getTitle());
		
	    driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Add ER")).click();
	    driver.findElement(By.name("firstName")).clear();
	    driver.findElement(By.name("firstName")).sendKeys("Nick");
	    driver.findElement(By.name("lastName")).clear();
	    driver.findElement(By.name("lastName")).sendKeys("Oftime");
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("nick@iTrust.com");
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("nick@itrust.com");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("New ER Nick Oftime succesfully added!", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    driver.findElement(By.linkText("Continue to personnel information.")).click();
	    driver.findElement(By.name("streetAddress1")).clear();
	    driver.findElement(By.name("streetAddress1")).sendKeys("900 Main Campus Dr");
	    driver.findElement(By.name("streetAddress2")).clear();
	    driver.findElement(By.name("streetAddress2")).sendKeys("Box 2509");
	    driver.findElement(By.name("city")).clear();
	    driver.findElement(By.name("city")).sendKeys("Raleigh");
	    new Select(driver.findElement(By.name("state"))).selectByVisibleText("North Carolina");
	    driver.findElement(By.name("zip")).clear();
	    driver.findElement(By.name("zip")).sendKeys("27606-1234");
	    driver.findElement(By.name("phone")).clear();
	    driver.findElement(By.name("phone")).sendKeys("919-100-1000");
	    driver.findElement(By.name("action")).click();
	    assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());

	    //quit driver
	    driver.quit();
	    
	}
}
