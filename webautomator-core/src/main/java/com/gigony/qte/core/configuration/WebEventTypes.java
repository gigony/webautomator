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

package com.gigony.qte.core.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.gigony.qte.core.model.WebEventType;

/**
 * Created by gigony on 12/6/14.
 *
 */
public class WebEventTypes {

  private final ImmutableMultimap<String, WebEventType> includingEventTypeMap;
  private final ImmutableMultimap<String, WebEventType> excludingEventTypeMap;

  @JsonCreator
  WebEventTypes(@JsonProperty("includingEventTypeMap") final Multimap<String, WebEventType> includingETypeMap,
                @JsonProperty("excludingEventTypeMap") final Multimap<String, WebEventType> excludingETypeMap) {
    this.includingEventTypeMap = ImmutableMultimap.copyOf(includingETypeMap);
    this.excludingEventTypeMap = ImmutableMultimap.copyOf(excludingETypeMap);
  }

  public final ImmutableMultimap<String, WebEventType> getIncludingEventTypeMap() {
    return this.includingEventTypeMap;
  }

  public final ImmutableMultimap<String, WebEventType> getExcludingEventTypeMap() {
    return this.excludingEventTypeMap;
  }

  public static WebEventTypesBuilder builder() {
    return new WebEventTypesBuilder();
  }

  public static WebEventTypes defaultEventTypes() {
    return new WebEventTypesBuilder().setDefaultEventTypes().build();
  }


  public static class WebEventTypesBuilder {
    private Multimap<String, WebEventType> includingEventTypeMap;
    private Multimap<String, WebEventType> excludingEventTypeMap;

    public WebEventTypesBuilder() {
      this.includingEventTypeMap = LinkedHashMultimap.create();
      this.excludingEventTypeMap = LinkedHashMultimap.create();
    }

    public final WebEventTypesBuilder setDefaultEventTypes() {
      this.click("css=a");
      this.click("css=button");
      this.click("css=input[type=\"submit\"]");
      this.click("css=input[type=\"button\"]");
      this.click("css=input[type=\"checkbox\"]");
      this.click("css=input[type=\"radio\"]");
      this.type("css=input[type=\"text\"]");
      this.type("css=input[type=\"password\"]");
      this.select("css=select");
      return this;
    }

    public final WebEventTypesBuilder includeEventType(final String eventTypeName, final String locator) {
      this.includingEventTypeMap.put(eventTypeName, new WebEventType(eventTypeName, locator));
      return this;
    }

    public final WebEventTypesBuilder excludeEventType(final String eventTypeName, final String locator) {
      this.excludingEventTypeMap.put(eventTypeName, new WebEventType(eventTypeName, locator));
      return this;
    }


    public final WebEventTypesBuilder click(final String locator) {
      return this.includeEventType("click", locator);
    }

    public final WebEventTypesBuilder select(final String locator) {
      return this.includeEventType("select", locator);
    }

    public final WebEventTypesBuilder type(final String locator) {
      return this.includeEventType("type", locator);
    }

    public final WebEventTypesBuilder dontClick(final String locator) {
      return this.excludeEventType("click", locator);
    }

    public final WebEventTypesBuilder dontSelect(final String locator) {
      return this.excludeEventType("select", locator);
    }

    public final WebEventTypesBuilder dontType(final String locator) {
      return this.excludeEventType("type", locator);
    }

    public final WebEventTypes build() {
      return new WebEventTypes(this.includingEventTypeMap, this.excludingEventTypeMap);
    }


  }
}
