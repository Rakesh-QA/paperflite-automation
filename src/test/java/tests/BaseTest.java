package tests;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    public WebDriver driver;
    public WebDriver incognitoDriver;

    @BeforeMethod
    public void setup() {
    	
    	ChromeOptions options = new ChromeOptions();

    	Map<String, Object> prefs = new HashMap<>();
    	prefs.put("profile.default_content_setting_values.notifications", 2);

    	options.setExperimentalOption("prefs", prefs);
    	options.addArguments("--disable-notifications");


        WebDriverManager.chromedriver().setup();

        // Normal browser
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://app.paperflite.dev/public/login");
    }

    public void launchIncognito(String url) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        incognitoDriver = new ChromeDriver(options);
        incognitoDriver.manage().window().maximize();
        incognitoDriver.get(url);
    }

    @AfterMethod
    public void tearDown() {
    		driver.quit();
        if (incognitoDriver != null) {
        	incognitoDriver.quit();
        }
    }
}
