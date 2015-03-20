package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class HelperSelenium {

	public static void seleniumLogin(HtmlUnitDriver d, String usrName, String pass) {
        d.get("http://localhost:8080/iTrust");	
		d.findElement(By.id("j_username")).sendKeys(usrName);
		d.findElement(By.id("j_password")).sendKeys(pass);
		d.findElement(By.id("j_username")).submit(); ;
	}
	
}
