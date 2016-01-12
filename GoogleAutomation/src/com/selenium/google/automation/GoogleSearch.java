package com.selenium.google.automation;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

public class GoogleSearch {

	private static Logger log = null;
	protected static WebDriver driver = null;
	protected static Scanner scan  = null;
	protected static BufferedWriter output = null;

	@Test
	public void search() {
		if(driver != null){
			int count = 0, i = 0;
			driver.get("http://www.google.com");
			log.info("Reached Google.com");
			driver.manage().window().maximize();
			wait("lst-ib");
			count  = scan.nextInt();
			for(i = 0; i < count; ++i){
				String current = scan.next();
				driver.findElement(By.id("lst-ib")).sendKeys(current);
				driver.findElement(By.name("btnG")).click();
				
				wait("resultStats"); // wait till all results showing
				
				List<WebElement> findElements = driver.findElements(By.xpath("//*[@id='rso']//h3/a"));
				if(!findElements.isEmpty()){
					try {
						output.append(current + " - " + findElements.get(0).getText()+ " - " + findElements.get(0).getAttribute("href")+"\n");
					} catch (IOException e) {
						log.error("Cannot write to file: " + e);
					}
				}
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					log.error(e);
				}
				driver.findElement(By.id("lst-ib")).clear();
			}
		}
	}
    
	
	private void createOutputFile() throws IOException {
		
		try {
			File file = new File("output.txt");
			if(!file.exists())
				file.createNewFile();
			output = new BufferedWriter(new FileWriter(file));
		} 
		catch ( IOException e ) {
			log.error(e);
		} 
	}


	private void wait(String id){
		  WebDriverWait wait =new WebDriverWait(driver, 10);
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
	 }


	@BeforeTest
	public void beforeTest() {
		log = Logger.getLogger("seleniumLogger");
		try {
			scan = new Scanner(new File("D:/workspace/GoogleAutomation/src/com/selenium/google/automation/input.txt"));
			log.debug("File Found");
		} 
		catch (FileNotFoundException e) {
			log.error("File not found"+ e);
		}
		// Adding Default Browser as Firefox if input is empty
		String browser = scan.hasNext() ? scan.next() : "Firefox";
		log.info("Running Test Cases in "+ browser);
		driver = new BaseDriver().getDriver(browser);
		
		try {
			createOutputFile(); // create output file
			output.write("First Result in Google Search:\n\n");
			log.info("Output file succesfully created");
		} 
		catch (IOException e) {
			log.error(e);
		}
	}

	@AfterTest
	public void afterTest() throws IOException {
		scan.close();
		output.close();
		if(driver != null)
			driver.quit();
	}

}
