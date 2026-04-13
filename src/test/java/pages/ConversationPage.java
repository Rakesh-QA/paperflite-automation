package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.AssertionUtil;

public class ConversationPage {

    WebDriver driver;
    WebDriverWait wait;

    // Constructor 
    public ConversationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // LOCATORS
   
    By converstion= By.xpath("//a[@id='conversations']//div[@class='menu_icon__xFoqI']//*[name()='svg']//*[name()='g' and contains(@clip-path,'url(#__lot')]//*[name()='g'][1]/*[name()='g'][1]/*[name()='path'][1]");
    By conversationMenu = By.xpath("//span[contains(text(),'Conversation')]");
    By searchBox = By.xpath("//input[@placeholder='Type here to search...']");
    By result = By.xpath("//div[contains(@class,'conversation')]");
    
    // Recipient and email validation
    By recipient = By.xpath("//div[@class='recipients_avatar__3tElG']");
    By emailText = By.xpath("//span[contains(text(),'@')]");
    
    // Activity and asset validation
    By expandActivity = By.xpath("//div[@id='infinite-scroll']//div[1]//div[1]//div[1]//span[1]//p[1]");
    By assetNames = By.xpath("//div[contains(@class,'LinesEllipsis  assetShare_name__rvsxy')]");
    
    // Actions (close & delete)
    By closeSlider = By.xpath("//i[contains(@class,'paperflite-close contact_closeIcon__ZQrRo')]");
    By deleteBtn = By.xpath("//i[contains(@class,'actionbar_icon__ki32r paperflite-delete')]");
    By confirmBtn = By.xpath("//span[text()='confirm']");

   
    // Open conversation module from left navigation
    public void openConversationModule() {
        wait.until(ExpectedConditions.elementToBeClickable(converstion)).click();
        wait.until(ExpectedConditions.elementToBeClickable(conversationMenu)).click();
    }


    // Search conversation using collection name
    public void searchConversation(String name) throws InterruptedException {

        WebElement search = wait.until(
            ExpectedConditions.elementToBeClickable(searchBox)
        );

        // Scroll into view to avoid interaction issues
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", search);

        Thread.sleep(500);

        search.click();
        search.clear();
        search.sendKeys(name);
    }

 
    // Validate recipient email from conversation details
    public void validateRecipientEmail(String expectedEmail) {

        wait.until(ExpectedConditions.elementToBeClickable(recipient)).click();

        String actualEmail = wait.until(
                ExpectedConditions.visibilityOfElementLocated(emailText)
        ).getText();

        System.out.println("Actual Email: " + actualEmail);

        AssertionUtil.verifyTrue(
                actualEmail.contains(expectedEmail),
                "Email mismatch"
        );
    }


    // Validate asset count and names from conversation activity
    public void validateAssets(List<String> expectedAssetNames, int expectedCount) {

        // Expand activity section
        wait.until(ExpectedConditions.elementToBeClickable(expandActivity)).click();

        // Wait until asset list is loaded
        List<WebElement> elements = wait.until(driver -> {
            List<WebElement> list = driver.findElements(assetNames);
            return list.size() > 0 ? list : null;
        });

        // Validate asset count
        System.out.println("UI Asset Count: " + elements.size());

        AssertionUtil.verifyTrue(
                elements.size() == expectedCount,
                "Asset count mismatch"
        );

        // Capture actual asset names
        List<String> actualNames = new ArrayList<>();

        for (WebElement el : elements) {
            actualNames.add(el.getText());
        }

        // Validate each expected asset name
        for (String name : expectedAssetNames) {
            AssertionUtil.verifyTrue(
                    actualNames.contains(name),
                    "Asset name missing: " + name
            );
        }
    }


    // Close the conversation details slider
    public void closeSlider() {
        wait.until(ExpectedConditions.elementToBeClickable(closeSlider)).click();
    }

  
    // Delete conversation and confirm action
    public void deleteConversation() {

        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();

        wait.until(ExpectedConditions.elementToBeClickable(confirmBtn)).click();
    }
}