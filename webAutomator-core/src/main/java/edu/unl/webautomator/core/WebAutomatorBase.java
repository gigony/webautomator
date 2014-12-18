package edu.unl.webautomator.core;

import com.google.inject.Inject;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.converter.TestCaseConverter;
import edu.unl.webautomator.core.executor.EventExecutor;
import edu.unl.webautomator.core.extractor.EventExtractor;
import edu.unl.webautomator.core.extractor.StateExtractor;
import edu.unl.webautomator.core.model.State;
import edu.unl.webautomator.core.platform.WebBrowser;
import edu.unl.webautomator.core.platform.WebBrowserFactory;
import edu.unl.webautomator.core.provider.EventInputProvider;
import org.openqa.selenium.WebDriver;

/**
 * Created by gigony on 12/6/14.
 */
public class WebAutomatorBase implements WebAutomator {

  private final WebAutomatorConfiguration configuration;
  private final WebBrowser webBrowser;
  private final WebDriver webDriver;
  private final TestCaseConverter testCaseConverter;
  private final StateExtractor stateExtractor;
  private final EventExtractor eventExtractor;
  private final EventInputProvider eventInputProvider;
  private final EventExecutor eventExecutor;


  @Inject
  WebAutomatorBase(final WebAutomatorConfiguration config, final WebAutomatorComponentFactory componentMaker) {
    this.configuration = config;
    this.webBrowser = WebBrowserFactory.create(config);
    this.webDriver = this.webBrowser.getWebDriver();
    this.testCaseConverter = componentMaker.createTestCaseConverter(this);
    this.stateExtractor = componentMaker.createStateExtractor(this);
    this.eventExtractor = componentMaker.createEventExtractor(this);
    this.eventInputProvider = componentMaker.createEventInputProvider(this);
    this.eventExecutor = componentMaker.createEventExecutor(this);
  }

  @Override
  public final WebAutomatorConfiguration getConfiguration() {
    return this.configuration;
  }

  @Override
  public final WebBrowser getWebBrowser() {
    return this.webBrowser;
  }

  @Override
  public final WebDriver getWebDriver() {
    return this.webDriver;
  }

  @Override
  public final WebDriverBackedSelenium createSelenium(final String baseUrl) {
    return new WebDriverBackedSelenium(this.getWebDriver(), baseUrl);
  }

  @Override
  public final TestCaseConverter getTestCaseConverter() {
    return this.testCaseConverter;
  }

  @Override
  public final StateExtractor getStateExtractor() {
    return this.stateExtractor;
  }

  @Override
  public final EventExtractor getEventExtractor() {
    return this.eventExtractor;
  }

  @Override
  public final EventInputProvider getEventInputProvider() {
    return this.eventInputProvider;
  }

  @Override
  public final EventExecutor getEventExecutor() {
    return this.eventExecutor;
  }

  @Override
  public final State getState() {
    return this.stateExtractor.extractState();
  }

  @Override
  public final void quit() {
    this.getWebBrowser().getWebDriver().quit();
  }
}
