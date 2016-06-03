package edu.ncsu.csc.itrust.seleniumtests;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class ExpertReviewsTest extends iTrustSeleniumTest{
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	super.setUp();
	gen.reviews();
    driver = new HtmlUnitDriver();
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  @Test
  public void testValidHCP() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("2");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Find an Expert")).click();
    new Select(driver.findElement(By.name("specialty"))).selectByVisibleText("Surgeon");
    new Select(driver.findElement(By.name("range"))).selectByVisibleText("250 Miles");
    driver.findElement(By.xpath("//form[@id='mainForm']/div[4]/button")).click();
    driver.findElement(By.linkText("View")).click();
    driver.findElement(By.xpath("//div[@id='iTrustContent']/a")).click();
    driver.findElement(By.name("title")).clear();
    driver.findElement(By.name("title")).sendKeys("Too bored?");
    new Select(driver.findElement(By.name("rating"))).selectByVisibleText("2");
    driver.findElement(By.name("description")).clear();
    driver.findElement(By.name("description")).sendKeys("They seemed nice, but they asked how I was then started snoring.");
    driver.findElement(By.name("addReview")).click();
  }
  
  @Test
  public void testInvalidHCP() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("109");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
    driver.findElement(By.linkText("Find an Expert")).click();
    new Select(driver.findElement(By.name("specialty"))).selectByVisibleText("Pediatrician");
    new Select(driver.findElement(By.name("range"))).selectByVisibleText("All");
    driver.findElement(By.xpath("//form[@id='mainForm']/div[4]/button")).click();
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Beaker Beaker"));
    driver.findElement(By.linkText("View")).click();
    assertFalse(driver.findElement(By.cssSelector("BODY")).getText().contains("Add a Review"));
  }
  
  @Test
  public void testDirectRating() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("109");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.get(ADDRESS + "/iTrust/auth/patient/reviewsPage.jsp?expertID=9000000000");
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Kelly Doctor is horrible!"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Best doctor at this hospital!"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("So Bad."));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("I am pretty happy"));
  }
  
  @Test
  public void testOverallRating() throws Exception {
    driver.get(ADDRESS + "/iTrust/auth/forwardUser.jsp");
    driver.findElement(By.id("j_username")).clear();
    driver.findElement(By.id("j_username")).sendKeys("22");
    driver.findElement(By.id("j_password")).clear();
    driver.findElement(By.id("j_password")).sendKeys("pw");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.get(ADDRESS + "/iTrust/auth/patient/reviewsPage.jsp?expertID=9000000003");
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Gandalf Stormcrow"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Pretty happy"));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Good service."));
    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Add a Review"));
  }
	
  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}
