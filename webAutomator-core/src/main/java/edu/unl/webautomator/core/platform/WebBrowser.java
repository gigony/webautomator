package edu.unl.webautomator.core.platform;

import edu.unl.webautomator.core.model.State;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

/**
 * Created by gigony on 12/9/14.
 */
public interface WebBrowser {

    WebDriver getWebDriver();


    // Functions from WebDriver

    String getPageSource();




    // ####################################
    // Improved functions with WebDriver
    // ####################################


    String getFrameId();

    void moveToDefaultFrame();

    void moveToRelativeFrame(String frameId);

    void moveToAbsoluteFrame(String frameId);

    void moveToParentFrame();


    String getPageSourceWithFrameContent();

    Document getPageDomWithFrameContent();

    String getFrameContent(String frameID);




    /**
     * Return normalized ('script' tag is removed) page source.
     * @return HTML code
     */
    String getNormalizedPageSource();
}
