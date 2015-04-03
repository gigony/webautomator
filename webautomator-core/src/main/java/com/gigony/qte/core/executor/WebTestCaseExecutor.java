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

package com.gigony.qte.core.executor;

import com.gigony.qte.core.WebAutomator;
import com.gigony.qte.core.extractor.WebStateExtractor;
import com.gigony.qte.core.model.*;
import com.google.inject.Inject;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

/**
 * Created by gigony on 1/24/15.
 */
public class WebTestCaseExecutor implements TestCaseExecutor<WebTestCase> {
  private WebAutomator webAutomator;


  @Inject
  public WebTestCaseExecutor(final WebAutomator automator) {
    this.webAutomator = automator;
  }

  @Override
  public final TestCaseExecutionResult execute(final WebTestCase testCase) {
    return this.execute(testCase, true);
  }

  @Override
  public final TestCaseExecutionResult execute(final WebTestCase testCase, final boolean saveState) {
    WebEventExecutor eventExecutor = this.webAutomator.getEventExecutor();
    WebStateExtractor stateExtractor = this.webAutomator.getStateExtractor();

    WebDriverBackedSelenium selenium = this.webAutomator.createSelenium(testCase.getBaseUrl());

    WebTestCaseExecutionResult result = new WebTestCaseExecutionResult(testCase);


    WebState webState = null;

    // execute prefix event
    EventExecutionResult<WebEventElement> eventExecutionResult = eventExecutor.execute(testCase.getPrefixEvent(), webState);
    if (!eventExecutionResult.isPassed()) {
      result.setFailureInducingEventInfo(eventExecutionResult);
      result.setResult((ExecutionResult.FAILED));
    }

    // add an initial state after prefix event execution.
    if (testCase.getPrefixEvent().size() > 0 && saveState) {
      result.addState(stateExtractor.extractState());
    } else {
      // just fill first state
      result.addState(null);
    }


    int eventIndex = 0;
    for (WebEvent e : testCase) {
      eventIndex++;
      if (saveState) {
        eventExecutionResult = eventExecutor.execute(e, result.getState(eventIndex - 1));
        webState = stateExtractor.extractState();
        result.addState(webState);
      } else {
        eventExecutionResult = eventExecutor.execute(e);
        result.addState(null);
      }
      if (!eventExecutionResult.isPassed()) {
        result.setFailureInducingEventInfo(eventExecutionResult);
        result.setResult(ExecutionResult.FAILED);
        break;
      }
    }

    return result;
  }
}
