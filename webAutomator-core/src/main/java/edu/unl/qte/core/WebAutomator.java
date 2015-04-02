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
 *    limitations under the License.
 */

package edu.unl.qte.core;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import edu.unl.qte.core.model.*;
import edu.unl.qte.core.configuration.WebAutomatorConfiguration;
import edu.unl.qte.core.converter.WebTestCaseConverter;
import edu.unl.qte.core.executor.WebEventExecutor;
import edu.unl.qte.core.executor.WebTestCaseExecutor;
import edu.unl.qte.core.extractor.WebEventExtractor;
import edu.unl.qte.core.extractor.WebStateExtractor;
import edu.unl.qte.core.platform.WebBrowser;
import edu.unl.qte.core.provider.WebEventInputProvider;
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

  WebTestCaseExecutor getTestCaseExecutor();

  WebState getState();

  WebState getState(String uri);

  EventExecutionResult<WebEventElement> execute(WebEvent webEvent);

  TestCaseExecutionResult<WebState, WebEventElement> execute(WebTestCase webTestCase);

  void quit();


}
