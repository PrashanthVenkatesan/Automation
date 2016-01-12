package com.selenium.google.automation;

import java.io.File;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BaseDriver {
	private WebDriver driver;
	private String driverPath;
	private File driverFile;
	private static Logger log = null;

	public BaseDriver(){
		this.driver = null;
		this.driverPath = "";
		this.driverFile = null;
		log = Logger.getLogger("seleniumLogger");
	}

	/* common method to get WebDriver Object */
	public WebDriver getDriver(String browser){
		if(browser.equals("Firefox")){
			try{
				this.driver = new FirefoxDriver();
				log.info("Successfully connected to Firefox browser");
			}
			catch(Exception e){
				log.error("Unable to connect to Driver "+ e);
			}
		}

		else if( browser.equals("Chrome")){
			try{
				this.driverPath = "D:/Selenium/chromedriver.exe";
				this.driverFile = new File(this.driverPath);
				System.setProperty("webdriver.chrome.driver", this.driverFile.getAbsolutePath());
				this.driver = new ChromeDriver();
				log.info("Successfully connected to Chrome browser");
			}
			catch(IllegalStateException e){
				log.error("Unable to connect to Driver due to missing executable "+ e);
			}
			catch(Exception e){
				log.error("Unable to connect to Driver "+ e);
			}
		}

		else if( browser.equals("IE")){
			try{
				this.driverPath = "D:/Selenium/IEDriverServer.exe";
				this.driverFile = new File(this.driverPath);
				System.setProperty("webdriver.ie.driver", this.driverFile.getAbsolutePath());
				this.driver = new InternetExplorerDriver();
				log.info("Successfully connected to Internet Explorer browser");
			}
			catch(IllegalStateException e){
				log.error("Unable to connect to Driver due to missing executable "+ e);
			}
			catch(Exception e){
				log.error("Unable to connect to Driver "+ e);
			}
		}
		return this.driver;
	}
}
