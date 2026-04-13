package pages;

import utils.*;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CollectionPage {

    WebDriver driver;
    WebDriverWait wait;

    // ===== CONSTRUCTOR =====
    public CollectionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ===== LOCATORS =====
    By collectionDrop = By.xpath("(//*[name()='svg'][@class='lottie-icon'])[3]");
    By collectionBtn = By.xpath("//span[text()='Collections']");
    By createCollection = By.xpath("//span[text()='Create new collection']");
    By nameField = By.xpath("//input[@placeholder='Add a collection name']");
    By nextBtn = By.xpath("//span[text()='Next']");
    By confirmBtn = By.xpath("//span[text()='Confirm']");
    By addAssetBtn = By.xpath("(//span[text()='Add Assets'])[1]");
    By browseFileBtn = By.xpath("//h6[text()='Personal drives']");
    By fileInput = By.xpath("//input[@type='file']");
    By saveChangesBtn = By.xpath("//div[@class='menu_menu__LmZYv menu_show__eShmd']//span[@class='actionbar_title__KLxa3'][normalize-space()='Save Changes']");
    By deleteCollectionBtn = By.xpath("//i[@class='actionbar_icon__ki32r paperflite-delete']");
    By radioBtn = By.xpath("//div[2]//a[1]//div[1]//div[1]//i[1]");
    By delete=By.xpath("//i[@class='toolbar_icon__i4WSR paperflite-delete']");

    // ===== CREATE COLLECTION =====
    public void createCollection(String name) {

        wait.until(ExpectedConditions.elementToBeClickable(collectionDrop)).click();
        wait.until(ExpectedConditions.elementToBeClickable(collectionBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(createCollection)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);

        wait.until(ExpectedConditions.elementToBeClickable(nextBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(nextBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmBtn)).click();
    }

    // ===== VALIDATION =====
    public boolean isCollectionCreated(String name) {
        return driver.getPageSource().contains(name);
    }

    // ===== UPLOAD FILES =====
    public void uploadAssetsUsingRobot(String file1, String file2, String file3, String file4) {

        wait.until(ExpectedConditions.elementToBeClickable(addAssetBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(browseFileBtn)).click();

        // Upload file 1
        clickBrowseAndUpload(file1);

        // Upload file 2
        clickBrowseAndUpload(file2);

        // Upload file 3
        clickBrowseAndUpload(file3);

        // Upload file 4
        clickBrowseAndUpload(file4);

        wait.until(ExpectedConditions.elementToBeClickable(confirmBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(saveChangesBtn)).click();
    }
    
    public void clickBrowseAndUpload(String filePath) {

        // Click Browse button
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()='Browse']"))).click();

        // Call Robot
        RobotUtil.uploadFile(filePath);
    }
    
 // ===== DELETE COLLECTION =====
    public void deleteCollection(String collectionName) {
    	
    	wait.until(ExpectedConditions.elementToBeClickable(collectionDrop)).click();
        wait.until(ExpectedConditions.elementToBeClickable(collectionBtn)).click();

        wait.until(ExpectedConditions.elementToBeClickable(deleteCollectionBtn)).click();

        // Select collection
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(radioBtn));
        radio.click();

        // Delete
        wait.until(ExpectedConditions.elementToBeClickable(delete)).click();

        wait.until(ExpectedConditions.elementToBeClickable(confirmBtn)).click();
    }


}