package edu.unl.webautomator;

import com.google.inject.Guice;
import edu.unl.webautomator.core.QTE;
import edu.unl.webautomator.core.WebAutomator;
import edu.unl.webautomator.core.WebAutomatorModule;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import org.junit.Test;

public class WebAutomatorModuleTest {

    @Test
    public final void webAutomatorModuleTest() {
        WebAutomatorConfiguration config = WebAutomatorConfiguration.defaultConfiguration();
//        WebAutomatorConfiguration config = WebAutomatorConfiguration.create(
//                WebBrowserType.CHROME,
//                "/Users/gigony/Development/Repository/github/webautomator/webautomator-core/build/webdrivers/chromedriver");

        Guice.createInjector(new WebAutomatorModule(config));

    }

    @Test
    public final void webAutomatorTest() {
        WebAutomator automator = QTE.webAutomator();
//        WebAutomator automator = QTE.webAutomator(WebBrowserType.CHROME,
//                "/Users/gigony/Development/Repository/github/webautomator/webautomator-core/build/webdrivers/chromedriver");

        automator.quit();

    }


}
