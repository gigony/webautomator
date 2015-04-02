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
import com.gigony.qte.core.util.MyWebDriverBackedSelenium;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gigony on 12/6/14.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
  getterVisibility = JsonAutoDetect.Visibility.NONE,
  setterVisibility = JsonAutoDetect.Visibility.NONE,
  isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class WebEventElement implements EventElement {
  private String eventType;
  private String frameId;
  private List<String> args = new ArrayList<String>();

  public WebEventElement(final String eventTypeName, final String uniqueId) {
    this(eventTypeName, null, uniqueId, null);

  }

  public WebEventElement(final String eventTypeName, final String frameId, final String uniqueId) {
    this(eventTypeName, frameId, uniqueId, null);
  }

  public WebEventElement(final String eventTypeName, final String frameId, final String target, final String input) {
    this.eventType = eventTypeName;
    this.frameId = frameId;
    int argSize = MyWebDriverBackedSelenium.getArgCount(eventTypeName);
    if (argSize >= 1) {
      if (target != null) {
        this.args.add(target);
      } else {
        throw new RuntimeException(String.format("First argument of an event '%s' should not null!", eventTypeName));
      }
    }

    if (argSize >= 2 && input != null) {
      this.args.add(input);
    }
  }

  /**
   * @param eventTypeName event type name
   * @param frameId frame ID
   * @param args arguments
   */
  @JsonCreator
  public WebEventElement(@JsonProperty("eventType") final String eventTypeName,
                         @JsonProperty("frameId") final String frameId,
                         @JsonProperty("args") final List<String> args) {
    this.eventType = eventTypeName;
    this.frameId = frameId;
    this.args = args;
  }

  @Override
  public final String getEventType() {
    return this.eventType;
  }

  public final void setEventType(final String eventType) {
    this.eventType = eventType;
  }

  public final String getFrameId() {
    return this.frameId;
  }

  public final void setFrameId(final String frameId) {
    this.frameId = frameId;
  }

  public final List<String> getArgs() {
    return this.args;
  }

  public final void setArg(final int index, final String value) {
    this.args.set(index, value);
  }

  public final int getArgSize() {
    return this.args.size();
  }

  public final void addArg(final String value) {
    if (value != null) {
      this.args.add(value);
    }
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(this.eventType, this.frameId, this.args);
  }

  @Override
  public final boolean equals(final Object obj) {
    if (obj instanceof WebEventElement) {
      WebEventElement that = (WebEventElement) obj;
      return Objects.equal(this.eventType, that.eventType)
        && Objects.equal(this.frameId, that.frameId)
        && Objects.equal(this.args, that.args);
    }
    return false;
  }

  @Override
  public final String toString() {
    return MoreObjects.toStringHelper(this)
      .add("eventType", this.eventType)
      .add("frameId", this.frameId)
      .add("args", this.args)
      .toString();
  }


}
