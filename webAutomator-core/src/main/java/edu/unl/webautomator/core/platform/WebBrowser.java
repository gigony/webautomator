package edu.unl.webautomator.core.platform;

import edu.unl.webautomator.core.model.WebDocument;
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


    String getJsonPageSourceWithFrameContent();

    WebDocument getPageDomWithFrameContent();

    String getFrameContent(String frameID);




    /**
     * Return normalized ('script' tag is removed) page source.
     * @return HTML code
     */
    String getNormalizedPageSource();
}
