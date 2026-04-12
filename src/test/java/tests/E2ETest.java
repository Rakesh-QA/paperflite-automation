package tests;

import org.testng.annotations.Test;
import pages.*;
import utils.*;

import java.awt.HeadlessException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class E2ETest extends BaseTest {

    @Test
    public void fullFlowTest() throws InterruptedException, HeadlessException, UnsupportedFlavorException, IOException {

        Map<String, String> data = CSVReaderUtil.getData();

        LoginPage login = new LoginPage(driver);
        CollectionPage collection = new CollectionPage(driver);
        LinkPage link = new LinkPage(driver);

        // LOGIN
        login.Login("rakeshafc001@gmail.com", "13@Raki11");

        // CREATE COLLECTION
        collection.createCollection(data.get("collectionName"));

        // UPLOAD FILES
        collection.uploadAssetsUsingRobot(
        	    FileUtil.getFilePath(data.get("file1")),
        	    FileUtil.getFilePath(data.get("file2")),
        	    FileUtil.getFilePath(data.get("file3")),
        	    FileUtil.getFilePath(data.get("file4"))
        	);

        // GENERATE LINK
        link.clickGenerateLink();

        // GET LINK
        String generatedUrl = link.getLinkUsingCopy().trim();

        AssertionUtil.verifyTrue(
                generatedUrl.contains("paperflite.dev/collections"),
                "Link not generated"
        );

        // APPLY GATING
        link.applyGating(data.get("password"));

        // ERROR VALIDATION
        AssertionUtil.verifyTrue(
                link.getErrorMessage().contains("password"),
                "Error message missing"
        );

        //  OPEN IN INCOGNITO
        launchIncognito(generatedUrl);

        FliteviewPage flite = new FliteviewPage(incognitoDriver);

        // EMAIL VALIDATION
        flite.clickContinueWithoutEmail();
        AssertionUtil.verifyTrue(
                flite.getErrorMessage().toLowerCase().contains("email"),
                "Email error missing"
        );

        flite.enterEmail(data.get("email"));

        // PASSWORD VALIDATION
        flite.clickContinueWithoutPassword();
        AssertionUtil.verifyTrue(
                flite.getErrorMessage().toLowerCase().contains("password"),
                "Password error missing"
        );

        flite.enterPassword(data.get("password"));

        // ASSET VALIDATION
        int assetCount = flite.getAssetCount();
        List<String> assetNamesList = flite.getAssetNames();

        System.out.println("Asset Count: " + assetCount);
        System.out.println("Asset Names: " + assetNamesList);

        AssertionUtil.verifyTrue(
        	    assetNamesList.size() == assetCount,
        	    "Asset count mismatch"
        	);

        // CLOSE INCOGNITO WINDOW
        incognitoDriver.quit();

        // WAIT BEFORE CONTINUING
        Thread.sleep(2000);
        
        link.getcloselinkpage();

        //  CONTINUE IN MAIN DRIVER
        ConversationPage convo = new ConversationPage(driver);

        convo.openConversationModule();

        convo.searchConversation(data.get("collectionName"));

        convo.validateRecipientEmail(data.get("email"));

        convo.validateAssets(assetNamesList, assetCount);

        convo.closeSlider();

        convo.deleteConversation();
        
        collection.deleteCollection(data.get("collectionName"));
    }
}