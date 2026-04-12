package pages;

import utils.*;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	WebDriver driver;
	WebDriverWait wait;
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	By email =By.xpath("//input[@placeholder='Enter your username']");
	By contineBtn= By.xpath("//button[normalize-space()='Continue']");
	By password=By.xpath("//input[@placeholder='Enter your password']");
	
	public void Login(String email, String Password) throws InterruptedException {
		
		wait.until(ExpectedConditions.elementToBeClickable(this.email)).sendKeys(email);
		wait.until(ExpectedConditions.elementToBeClickable(this.contineBtn)).click();
		wait.until(ExpectedConditions.elementToBeClickable(this.password)).sendKeys(Password);	
		wait.until(ExpectedConditions.elementToBeClickable(this.contineBtn)).click();
	}
	
}
