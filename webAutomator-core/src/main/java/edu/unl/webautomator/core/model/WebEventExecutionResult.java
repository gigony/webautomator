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

package edu.unl.webautomator.core.model;

/**
 * Created by gigony on 2/23/15.
 */
public class WebEventExecutionResult implements EventExecutionResult<WebEventElement> {
  private boolean isPassed;
  private String generatedInput;
  private Event<WebEventElement> failedEvent;
  private WebEventElement failedEventElement;
  private String causeMessage;
  private Throwable throwable;

  public WebEventExecutionResult() {
    this(true, null, null, null, "", null);
  }

  public WebEventExecutionResult(final boolean isPassed, final String generatedInput, final Event<WebEventElement> failedEvent, final WebEventElement failedEventElement, final String causeMessage, final Throwable throwable) {
    this.isPassed = isPassed;
    this.generatedInput = generatedInput;
    this.failedEvent = failedEvent;
    this.failedEventElement = failedEventElement;
    this.causeMessage = causeMessage;
    this.throwable = throwable;
  }

  @Override
  public final boolean isPassed() {
    return this.isPassed;
  }

  public final void setPassed(final boolean isPassed) {
    this.isPassed = isPassed;
  }

  public final String getGeneratedInput() {
    return this.generatedInput;
  }

  public final void setGeneratedInput(final String generatedInput) {
    this.generatedInput = generatedInput;
  }

  @Override
  public final String getCauseMessage() {
    return this.causeMessage;
  }

  public final void setCauseMessage(final String causeMessage) {
    this.causeMessage = causeMessage;
  }

  @Override
  public final Event<WebEventElement> getFailedEvent() {
    return this.failedEvent;
  }

  public final void setFailedEvent(final Event<WebEventElement> failedEvent) {
    this.failedEvent = failedEvent;
  }

  @Override
  public final WebEventElement getFailedEventElement() {
    return this.failedEventElement;
  }

  public final void setFailedEventElement(final WebEventElement failedEventElement) {
    this.failedEventElement = failedEventElement;
  }

  public final Throwable getThrowable() {
    return this.throwable;
  }

  public final void setThrowable(final Throwable throwable) {
    this.throwable = throwable;
  }
}
