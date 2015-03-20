package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
 
public class Test1 {
	 
    private static WebDriver driver = null;
 
    public static void main(String[] args) {
 
        // Create a new instance of the driver
        driver = new HtmlUnitDriver();
 
        //Put a Implicit wait, this means that any search for elements on the page could take the time the implicit wait is set for before throwing exception

    	//System.out.println("Here I am");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
 
        //Launch the Online Store Website
        driver.get("http://localhost:8080/iTrust");
        
        System.out.println(driver.getTitle());
 
        // Close the driver
 
        driver.quit();
 
        }
}