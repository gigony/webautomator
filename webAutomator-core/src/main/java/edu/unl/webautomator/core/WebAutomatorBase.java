package edu.unl.webautomator.core;

import com.google.inject.Inject;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.converter.WebTestCaseConverter;
import edu.unl.webautomator.core.executor.WebEventExecutor;
import edu.unl.webautomator.core.extractor.WebEventExtractor;
import edu.unl.webautomator.core.extractor.WebStateExtractor;
import edu.unl.webautomator.core.model.WebEvent;
import edu.unl.webautomator.core.model.WebState;
import edu.unl.webautomator.core.platform.WebBrowser;
import edu.unl.webautomator.core.platform.WebBrowserFactory;
import edu.unl.webautomator.core.provider.WebEventInputProvider;
import org.openqa.selenium.WebDriver;

/**
 * Created by gigony on 12/6/14.
 */
public class WebAutomatorBase implements WebAutomator {

  private final WebAutomatorConfiguration configuration;
  private final WebBrowser webBrowser;
  private final WebDriver webDriver;
  private final WebTestCaseConverter testCaseConverter;
  private final WebStateExtractor stateExtractor;
  private final WebEventExtractor eventExtractor;
  private final WebEventInputProvider eventInputProvider;
  private final WebEventExecutor eventExecutor;


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
  public final WebTestCaseConverter getTestCaseConverter() {
    return this.testCaseConverter;
  }

  @Override
  public final WebStateExtractor getStateExtractor() {
    return this.stateExtractor;
  }

  @Override
  public final WebEventExtractor getEventExtractor() {
    return this.eventExtractor;
  }

  @Override
  public final WebEventInputProvider getEventInputProvider() {
    return this.eventInputProvider;
  }

  @Override
  public final WebEventExecutor getEventExecutor() {
    return this.eventExecutor;
  }

  @Override
  public final WebState getState() {
    return this.stateExtractor.extractState();
  }

  @Override
  public final WebState getState(final String uri) {
    return this.stateExtractor.extractState(uri);
  }

  @Override
  public final void execute(final WebEvent webEvent) {
    this.eventExecutor.execute(webEvent);
  }

  @Override
  public final void quit() {
    this.getWebBrowser().getWebDriver().quit();
  }
}
