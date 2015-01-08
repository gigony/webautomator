package edu.unl.webautomator.core;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.converter.WebTestCaseConverter;
import edu.unl.webautomator.core.executor.WebEventExecutor;
import edu.unl.webautomator.core.extractor.WebEventExtractor;
import edu.unl.webautomator.core.extractor.WebStateExtractor;
import edu.unl.webautomator.core.model.WebEvent;
import edu.unl.webautomator.core.model.WebState;
import edu.unl.webautomator.core.platform.WebBrowser;
import edu.unl.webautomator.core.provider.WebEventInputProvider;
import org.openqa.selenium.WebDriver;

/**
 * Created by gigony on 12/6/14.
 */
public interface WebAutomator extends Automator {

  WebAutomatorConfiguration getConfiguration();

  WebBrowser getWebBrowser();

  WebDriver getWebDriver();

  WebDriverBackedSelenium createSelenium(String baseUrl);

  WebTestCaseConverter getTestCaseConverter();

  WebStateExtractor getStateExtractor();

  WebEventExtractor getEventExtractor();

  WebEventInputProvider getEventInputProvider();

  WebEventExecutor getEventExecutor();

  WebState getState();

  WebState getState(String uri);


  void execute(WebEvent webEvent);

  void quit();


}
