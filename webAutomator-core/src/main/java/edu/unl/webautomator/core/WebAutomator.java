package edu.unl.webautomator.core;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.converter.TestCaseConverter;
import edu.unl.webautomator.core.executor.EventExecutor;
import edu.unl.webautomator.core.extractor.EventExtractor;
import edu.unl.webautomator.core.extractor.StateExtractor;
import edu.unl.webautomator.core.model.State;
import edu.unl.webautomator.core.platform.WebBrowser;
import edu.unl.webautomator.core.provider.EventInputProvider;
import org.openqa.selenium.WebDriver;

/**
 * Created by gigony on 12/6/14.
 */
public interface WebAutomator extends Automator {

    WebAutomatorConfiguration getConfiguration();

    WebBrowser getWebBrowser();

    WebDriver getWebDriver();

    WebDriverBackedSelenium createSelenium(String baseUrl);

    TestCaseConverter getTestCaseConverter();

    StateExtractor getStateExtractor();

    EventExtractor getEventExtractor();

    EventInputProvider getEventInputProvider();

    EventExecutor getEventExecutor();



    State getState();


    void quit();
}
