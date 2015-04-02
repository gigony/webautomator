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
import com.gigony.qte.core.model.*;
import com.gigony.qte.core.provider.EventInputHandler;
import com.gigony.qte.core.provider.TypeHandlerPair;
import com.gigony.qte.core.provider.WebEventInputProvider;
import com.gigony.qte.core.util.MyWebDriverBackedSelenium;
import com.google.inject.Inject;
import com.gigony.qte.core.platform.WebBrowser;
import com.gigony.qte.core.util.SeleniumHelper;
import com.gigony.qte.core.extractor.WebEventExtractor;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by gigony on 12/6/14.
 */
public class WebEventExecutor implements EventExecutor<WebEventElement> {
  private WebAutomator webAutomator;


  @Inject
  public WebEventExecutor(final WebAutomator automator) {
    this.webAutomator = automator;

  }

  @Override
  public final EventExecutionResult<WebEventElement> execute(final Event<WebEventElement> e) {
    return this.execute(e, null);
  }


  public final EventExecutionResult<WebEventElement> execute(final Event<WebEventElement> e, final WebState state) {
    WebBrowser webBrowser = this.webAutomator.getWebBrowser();
    WebEventInputProvider eventInputProvider = this.webAutomator.getEventInputProvider();
    MyWebDriverBackedSelenium selenium = webBrowser.getSelenium();

    if (e.getPreConditions() != null) {
      for (WebEventElement elem : e.getPreConditions()) {
        WebEventExecutionResult executionResult = this.execute(webBrowser, selenium, elem, eventInputProvider, state);
        if (!executionResult.isPassed()) {
          executionResult.setFailedEvent(e);
          executionResult.setCauseMessage(String.format("Failed to verify a precondition (%s) in an event (%s) exception: %s", elem, e, executionResult.getThrowable().getMessage()));
          return executionResult;
        }
      }
    }

    for (WebEventElement elem : e.getActions()) {
      WebEventExecutionResult executionResult = this.execute(webBrowser, selenium, elem, eventInputProvider, state);
      if (!executionResult.isPassed()) {
        executionResult.setFailedEvent(e);
        executionResult.setCauseMessage(String.format("Failed to execute an action (%s) in an event (%s) exception: %s", elem, e, executionResult.getThrowable().getMessage()));
        return executionResult;
      }
    }

    if (e.getPostConditions() != null) {
      for (WebEventElement elem : e.getPostConditions()) {
        WebEventExecutionResult executionResult = this.execute(webBrowser, selenium, elem, eventInputProvider, state);
        if (!executionResult.isPassed()) {
          executionResult.setFailedEvent(e);
          executionResult.setCauseMessage(String.format("Failed to verify a postcondition (%s) in an event (%s) exception: %s", elem, e, executionResult.getThrowable().getMessage()));
          return executionResult;
        }
      }
    }

    return new WebEventExecutionResult();
  }


  public final EventExecutionResult execute(final WebEventElement eventElem, final WebState state) {
    WebBrowser webBrowser = this.webAutomator.getWebBrowser();
    WebEventInputProvider eventInputProvider = this.webAutomator.getEventInputProvider();
    MyWebDriverBackedSelenium selenium = webBrowser.getSelenium();
    return this.execute(webBrowser, selenium, eventElem, eventInputProvider, state);
  }

  private WebEventExecutionResult execute(final WebBrowser webBrowser, final MyWebDriverBackedSelenium selenium, final WebEventElement elem, final WebEventInputProvider eventInputProvider, final WebState state) {
    String oldFrameId = webBrowser.getFrameId();
    WebEventElement eventElem = elem;

    String frameId = eventElem.getFrameId();

    if (frameId != null) {
      webBrowser.moveToAbsoluteFrame(frameId);
    } else {
      frameId = oldFrameId;
    }

    try {
      if (state != null) {
        if (MyWebDriverBackedSelenium.requireInput(eventElem)) {
          if (MyWebDriverBackedSelenium.isLocationBasedInputAction(eventElem.getEventType())) {
            // assume that first argument is a locator
            By locator = SeleniumHelper.convertStringLocatorToBy(eventElem.getArgs().get(0));
            WebElement webElement = webBrowser.getWebDriver().findElement(locator);
            String input = this.getAugmentedInput(frameId, eventElem, eventInputProvider, webElement, state);
            eventElem.addArg(input);
          }
        }
      }
      selenium.doCommand(eventElem);

      // change internal framestack if eventtype is 'selectFrame'
      this.changeFrame(eventElem, webBrowser);

    } catch (Throwable t) {
      return new WebEventExecutionResult(false, null, null, elem, "", t);
    } finally {
      webBrowser.moveToAbsoluteFrame(oldFrameId);
    }
    return new WebEventExecutionResult();
  }

  private void changeFrame(final WebEventElement eventElem, final WebBrowser webBrowser) {
    if (!eventElem.equals("selectFrame")) {
      return;
    }
    String frameSelector = eventElem.getArgs().get(0);

    webBrowser.changeFrameStack(frameSelector);
  }

  private String getAugmentedInput(final String frameId, final WebEventElement eventElem, final WebEventInputProvider eventInputProvider, final WebElement webElement, final WebState state) {
    String eventType = eventElem.getEventType();
    String input = null;

    int argCount = MyWebDriverBackedSelenium.getArgCount(eventType);

    // command should require input (argCount == 2)     format: command (locator, input)
    if (argCount == 2) {

      Document document = state.getWebDoc().getFrame(frameId).getDocument();

      // it first uses custom input handler
      List<TypeHandlerPair<EventType, EventInputHandler<WebEventElement, WebElement>>> customInputHandler = eventInputProvider.getCustomEventInputHandler();
      for (TypeHandlerPair<EventType, EventInputHandler<WebEventElement, WebElement>> pair : customInputHandler) {
        String targetEventType = pair.getKey().getEventTypeName();
        if (!eventType.equals(targetEventType)) {
          continue;
        }

        Set<String> idSet = WebEventExtractor.selectIdSet(document, pair.getKey().getEventLocator());
        if (idSet.contains(idSet)) {
          EventInputHandler<WebEventElement, WebElement> handler = pair.getValue();
          input = handler.getInput(eventElem, webElement);

          if (input != null) {
            return input;
          }
        }
      }

      // or, try to use default Input handler
      Map<String, EventInputHandler<WebEventElement, WebElement>> defaultInputHandler = eventInputProvider.getDefaultEventInputHandler();
      for (String targetEventType : defaultInputHandler.keySet()) {
        if (!eventType.equals(targetEventType)) {
          continue;
        }

        EventInputHandler<WebEventElement, WebElement> handler = defaultInputHandler.get(targetEventType);
        input = handler.getInput(eventElem, webElement);
        if (input != null) {
          return input;
        }
      }

      if (input == null) {
        throw new RuntimeException("Event input is null!");
      }

    }

    return input;
  }
}
