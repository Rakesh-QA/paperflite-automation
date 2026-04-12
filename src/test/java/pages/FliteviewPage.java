package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FliteviewPage {

    WebDriver driver;
    WebDriverWait wait;
    String errmsg;

    public FliteviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    By emailField = By.id("emailInput");
    By passwordField = By.xpath("//input[@placeholder='Enter password']");
    By continueBtn = By.xpath("//span[contains(text(),'CONTINUE')]");
    By errorMsg = By.xpath("(//div[contains(@class,'error')])[2]");
    By assets = By.xpath("//img[contains(@src,'cloudfront')]");

    public void clickContinueWithoutEmail() {
    	wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
    	errmsg = wait.until(ExpectedConditions.elementToBeClickable(errorMsg)).getText();
    }

    public void enterEmail(String email) {
    	wait.until(ExpectedConditions.elementToBeClickable(emailField)).sendKeys(email);
    	wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
    }

    public void clickContinueWithoutPassword() {
    	
    	wait.until(ExpectedConditions.elementToBeClickable(passwordField)).click();
    	wait.until(ExpectedConditions.presenceOfElementLocated(continueBtn)).click();

    	      	    
    	    errmsg = wait.until(ExpectedConditions.elementToBeClickable(errorMsg)).getText();
    	}
    

    public void enterPassword(String password) {
    	wait.until(ExpectedConditions.elementToBeClickable(passwordField)).sendKeys(password);
    	WebElement continueButton = wait.until(
    	        ExpectedConditions.presenceOfElementLocated(continueBtn)
    	    );

    	    ((JavascriptExecutor) driver)
    	        .executeScript("arguments[0].click();", continueButton);
    	}
    

    public String getErrorMessage() {
    	return errmsg;
       
    }

    public int getAssetCount() {

        List<WebElement> elements = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class,'card_name')]")
            )
        );

        Set<String> uniqueNames = new LinkedHashSet<>();

        for (WebElement el : elements) {
            String name = el.getText().trim();
            if (!name.isEmpty()) {
                uniqueNames.add(name);
            }
        }

        return uniqueNames.size();
    }
    
    public List<String> getAssetNames() {

        List<WebElement> elements = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[contains(@class,'card_name')]")
            )
        );

        Set<String> uniqueNames = new LinkedHashSet<>();

        for (WebElement el : elements) {
            String name = el.getText().trim();
            if (!name.isEmpty()) {
                uniqueNames.add(name);
            }
        }

        return new ArrayList<>(uniqueNames);
    }
    
}