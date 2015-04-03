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

package com.gigony.qte.core.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gigony.qte.core.util.SeleniumHelper;
import com.google.common.base.Objects;

/**
 * Created by gigony on 1/7/15.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
  getterVisibility = JsonAutoDetect.Visibility.NONE,
  setterVisibility = JsonAutoDetect.Visibility.NONE,
  isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class WebEventType implements EventType {

  private String eventTypeName;
  private String eventLocator;

  @JsonCreator
  public WebEventType(@JsonProperty("eventTypeName") final String eTypeName,
                      @JsonProperty("eventLocator") final String locator) {
    this.eventTypeName = eTypeName;
    this.eventLocator = locator;
  }

  public final String getEventTypeName() {
    return this.eventTypeName;
  }

  public final String getEventLocator() {
    return this.eventLocator;
  }

  public final boolean isCssLocator() {
    return SeleniumHelper.isCssLocator(this.eventLocator);
  }

  public final String extractCssLocator() {
    return SeleniumHelper.extractCssLocator(this.eventLocator);
  }

  public final boolean isXPathLocator() {
    return SeleniumHelper.isXPathLocator(this.eventLocator);
  }

  public final String extractXPathLocator() {
    return SeleniumHelper.extractXPathLocator(this.eventLocator);
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(this.eventTypeName, this.eventLocator);
  }

  @Override
  public final boolean equals(final Object obj) {
    if (obj instanceof WebEventType) {
      WebEventType that = (WebEventType) obj;
      return Objects.equal(this.eventTypeName, that.eventTypeName)
        && Objects.equal(this.eventLocator, that.eventLocator);
    }
    return false;
  }
}
