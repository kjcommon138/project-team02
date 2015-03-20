package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class ChangePasswordTest {
	
    private static WebDriver driver = null;
	
	@Before
	public void setUp() throws Exception {
	    // Create a new instance of the driver
	    driver = new HtmlUnitDriver();

	}

	@Test
	public final void test() {
		fail("Not yet implemented");
	}

}
