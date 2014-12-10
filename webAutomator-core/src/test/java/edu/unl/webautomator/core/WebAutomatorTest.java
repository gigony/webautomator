package edu.unl.webautomator.core;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import edu.unl.webautomator.core.platform.WebBrowser;
import edu.unl.webautomator.core.platform.WebBrowserType;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class WebAutomatorTest {

    @Test
    public final void openChromeBrowserTest() {
        WebAutomator automator = QTE.webAutomator(WebBrowserType.CHROME,
                "/Users/gigony/Development/Repository/github/webautomator/webautomator-core/build/webdrivers/chromedriver");

        WebBrowser browser = automator.getWebBrowser();
        WebDriver driver = automator.getWebDriver();
        WebDriverBackedSelenium selenium = automator.createSelenium("http://www.nate.com");
        selenium.open("");

        automator.quit();
    }

}
