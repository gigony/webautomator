/*
 * Copyright 2015 Gigon Bae
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under <></>he License.
 */

package edu.unl.webautomator.core;

import com.google.inject.Inject;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.converter.WebTestCaseConverter;
import edu.unl.webautomator.core.executor.WebEventExecutor;
import edu.unl.webautomator.core.executor.WebTestCaseExecutor;
import edu.unl.webautomator.core.extractor.WebEventExtractor;
import edu.unl.webautomator.core.extractor.WebStateExtractor;
import edu.unl.webautomator.core.model.*;
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
  private final WebTestCaseExecutor testCaseExecutor;


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
    this.testCaseExecutor = componentMaker.createTestCaseExecutor(this);
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
  public final WebTestCaseExecutor getTestCaseExecutor() {
    return this.testCaseExecutor;
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
  public final EventExecutionResult<WebEventElement> execute(final WebEvent webEvent) {
    return this.eventExecutor.execute(webEvent);
  }

  @Override
  public final TestCaseExecutionResult<WebState, WebEventElement> execute(final WebTestCase webTestCase) {
    return this.testCaseExecutor.execute(webTestCase);
  }

  @Override
  public final void quit() {
    this.getWebBrowser().getWebDriver().quit();
  }
}
