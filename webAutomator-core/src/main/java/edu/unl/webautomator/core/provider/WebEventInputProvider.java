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

package edu.unl.webautomator.core.provider;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import edu.unl.webautomator.core.WebAutomator;
import edu.unl.webautomator.core.model.Event;
import edu.unl.webautomator.core.model.EventType;
import edu.unl.webautomator.core.model.WebEventElement;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

/**
 * Created by gigony on 12/6/14.
 */
public class WebEventInputProvider implements EventInputProvider<WebEventElement, WebElement> {
  private WebAutomator webAutomator;

  private List<TypeHandlerPair<EventType, EventInputHandler<WebEventElement, WebElement>>> customEventInputHandler;
  private Map<String, EventInputHandler<WebEventElement, WebElement>> defaultEventInputHandler;

  @Inject
  public WebEventInputProvider(final WebAutomator automator) {
    this.webAutomator = automator;
    this.customEventInputHandler = Lists.newArrayList();
    this.defaultEventInputHandler = Maps.newHashMap();
  }

  @Override
  public final void addCustomEventInputHandler(final EventType eventType, final EventInputHandler<WebEventElement, WebElement> inputHandler) {
    this.customEventInputHandler.add(new TypeHandlerPair<EventType, EventInputHandler<WebEventElement, WebElement>>(eventType, inputHandler));
  }

  @Override
  public final void setDefaultEventInputHandler(final String eventType, final EventInputHandler<WebEventElement, WebElement> inputHandler) {
    this.defaultEventInputHandler.put(eventType, inputHandler);
  }

  @Override
  public final List<TypeHandlerPair<EventType, EventInputHandler<WebEventElement, WebElement>>> getCustomEventInputHandler() {
    return this.customEventInputHandler;
  }

  @Override
  public final Map<String, EventInputHandler<WebEventElement, WebElement>> getDefaultEventInputHandler() {
    return this.defaultEventInputHandler;
  }

//  @Override
//  public final void addEventInputHandler(final EventInputHandler<WebEventElement, WebElement> inputHandler) {
//    this.customEventInputHandler.add(inputHandler);
//  }
//
//  @Override
//  public final void setDefaultEventInputHandler(final String eventType, final EventInputHandler<WebEventElement, WebElement> inputHandler) {
//    this.defaultEventInputHandler.put(eventType, inputHandler);
//  }
//
//  @Override
//  public final String getEventInputFor(final Event<WebEventElement> event) {
//    for(EventInputHandler<WebEventElement,WebElement> )
//  }
//
//  @Override
//  public final String getEventInputFor(final WebElement element) {
//    return null;
//  }
}
