package pages;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LinkPage {

    WebDriver driver;
    WebDriverWait wait;
    String eremsg;

    // ===== CONSTRUCTOR =====
    public LinkPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ===== LOCATORS =====
    By generateLinkBtn = By.xpath("//i[@class='actionbar_icon__ki32r paperflite-link']");
    By getLink = By.xpath("//button[text()='Get Link']");
    By copyBtn = By.xpath("//div[contains(@class,'copylink_iconContainer')]//i");
    By popupClose = By.xpath("(//i[@class='paperflite-close tour_closeButton__Mibwc'])[1]");
    By updatedLink = By.xpath("//div[contains(text(),'https')]");
    By settings = By.xpath("//i[@class='paperflite-settings-wheel']");
    By validEmailToggle = By.xpath("//span[text()='Validate work email']/parent::div");
    By setPasswordToggle = By.xpath("//div[4]//div[2]//div[1]//div[1]//span[1]");
    By saveBtn = By.xpath("//button[@class='button_button__0On-O button_primary__ZBQkr options_saveButton__EmYLY btn btn-primary']");
    By enterPassword = By.xpath("//input[@placeholder='Enter password']");
    By errorMsg = By.xpath("//span[text()='Enter a valid password']");
    By closelink= By.xpath("//i[@class='paperflite-close generateLink_closeIcon__SIXLA']");

    // ===== STEP 1: GENERATE LINK =====
    public void clickGenerateLink() {

        wait.until(ExpectedConditions.elementToBeClickable(generateLinkBtn)).click();

        wait.until(ExpectedConditions.elementToBeClickable(getLink)).click();

      
        List<WebElement> popup = driver.findElements(popupClose);

        if (popup.size() > 0) {
            try {
                popup.get(0).click();
            } catch (Exception e) {
                System.out.println("Popup present but not clickable");
            }
        }
    }

    // ===== STEP 2: GET GENERATED LINK =====
    public String getLinkUsingCopy() {

        By copyBtn = By.xpath("//div[contains(@class,'copylink')]//i");

        WebElement copy = wait.until(ExpectedConditions.presenceOfElementLocated(copyBtn));

        // Clear clipboard
        Toolkit.getDefaultToolkit().getSystemClipboard()
            .setContents(new StringSelection(""), null);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", copy);

        // Wait for new value
        try {
            Thread.sleep(2000);
        } catch (Exception e) {}

        String link = "";

        try {
            link = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard()
                    .getData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return link;
    }
  

    // ===== STEP 3: APPLY GATING =====
    public void applyGating(String password) throws InterruptedException {
    	wait.until(ExpectedConditions.elementToBeClickable(settings)).click();

    	// ✅ Email toggle (JS click)
    	WebElement emailToggle = wait.until(
    	    ExpectedConditions.presenceOfElementLocated(validEmailToggle)
    	);

    	((JavascriptExecutor) driver)
    	    .executeScript("arguments[0].click();", emailToggle);


    	// Password toggle (
    	WebElement passwordToggle = wait.until(
    	    ExpectedConditions.presenceOfElementLocated(By.id("password"))
    	);

    	// Scroll into view
    	((JavascriptExecutor) driver)
    	    .executeScript("arguments[0].scrollIntoView({block:'center'});", passwordToggle);

    	Thread.sleep(500);

    	((JavascriptExecutor) driver)
    	    .executeScript("arguments[0].click();", passwordToggle);


    	// Save (without password)
    	wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();

    	eremsg = wait.until(
    	    ExpectedConditions.visibilityOfElementLocated(errorMsg)
    	).getText();

    	// Enter password
    	wait.until(ExpectedConditions.visibilityOfElementLocated(enterPassword))
    	    .sendKeys(password);

    	// Save again
    	wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
    	
    }


    // ===== STEP 5: GET ERROR MESSAGE =====
    public String getErrorMessage() {
        return eremsg;
    }
    
    public void getcloselinkpage() {
    	wait.until(ExpectedConditions.elementToBeClickable(closelink)).click();
    }
    
    
}