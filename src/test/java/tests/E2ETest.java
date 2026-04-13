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

        // Read test data from CSV
        Map<String, String> data = CSVReaderUtil.getData();

        // Initialize page objects
        LoginPage login = new LoginPage(driver);
        CollectionPage collection = new CollectionPage(driver);
        LinkPage link = new LinkPage(driver);

        // LOGIN to application
        login.Login("rakeshafc001@gmail.com", "13@Raki11");

        // CREATE new collection
        collection.createCollection(data.get("collectionName"));

        // UPLOAD multiple assets using Robot (file upload)
        collection.uploadAssetsUsingRobot(
                FileUtil.getFilePath(data.get("file1")),
                FileUtil.getFilePath(data.get("file2")),
                FileUtil.getFilePath(data.get("file3")),
                FileUtil.getFilePath(data.get("file4"))
        );

        // GENERATE shareable link
        link.clickGenerateLink();

        // GET generated link and validate
        String generatedUrl = link.getLinkUsingCopy().trim();

        AssertionUtil.verifyTrue(
                generatedUrl.contains("paperflite.dev/collections"),
                "Link not generated"
        );

        // APPLY email & password gating
        link.applyGating(data.get("password"));

        // VALIDATE error message for missing password
        AssertionUtil.verifyTrue(
                link.getErrorMessage().contains("password"),
                "Error message missing"
        );

        // OPEN generated link in incognito browser
        launchIncognito(generatedUrl);

        FliteviewPage flite = new FliteviewPage(incognitoDriver);

        // VALIDATE email field error
        flite.clickContinueWithoutEmail();
        AssertionUtil.verifyTrue(
                flite.getErrorMessage().toLowerCase().contains("email"),
                "Email error missing"
        );

        // ENTER email and proceed
        flite.enterEmail(data.get("email"));

        // VALIDATE password field error
        flite.clickContinueWithoutPassword();
        AssertionUtil.verifyTrue(
                flite.getErrorMessage().toLowerCase().contains("password"),
                "Password error missing"
        );

        // ENTER password and access assets
        flite.enterPassword(data.get("password"));

        // CAPTURE asset count and names from Fliteview
        int assetCount = flite.getAssetCount();
        List<String> assetNamesList = flite.getAssetNames();

        System.out.println("Asset Count: " + assetCount);
        System.out.println("Asset Names: " + assetNamesList);

        // VALIDATE asset count consistency
        AssertionUtil.verifyTrue(
                assetNamesList.size() == assetCount,
                "Asset count mismatch"
        );

        // CLOSE incognito browser
        incognitoDriver.quit();

        // WAIT before switching back
        Thread.sleep(2000);

        // CLOSE link popup in main window
        link.getcloselinkpage();

        // CONTINUE validation in main application
        ConversationPage convo = new ConversationPage(driver);

        // OPEN conversation module
        convo.openConversationModule();

        // SEARCH conversation using collection name
        convo.searchConversation(data.get("collectionName"));

        // VALIDATE recipient email
        convo.validateRecipientEmail(data.get("email"));

        // VALIDATE asset names and count in conversation
        convo.validateAssets(assetNamesList, assetCount);

        // CLOSE conversation slider
        convo.closeSlider();

        // DELETE conversation
        convo.deleteConversation();

        // DELETE collection
        collection.deleteCollection(data.get("collectionName"));
    }
}