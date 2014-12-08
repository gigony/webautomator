package edu.unl.webautomator;

import com.google.inject.Guice;
import edu.unl.webautomator.core.QTE;
import edu.unl.webautomator.core.WebAutomator;
import edu.unl.webautomator.core.WebAutomatorModule;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import junit.framework.TestCase;
import org.junit.Test;

public class WebAutomatorModuleTest extends TestCase {

    @Test
    public void WebAutomatorModuleTest() {
        WebAutomatorConfiguration config = WebAutomatorConfiguration.defaultConfiguration();
        Guice.createInjector(new WebAutomatorModule(config));

    }

    @Test
    public void WebAutomatorTest() {
        WebAutomator automator = QTE.webAutomator();
    }



}