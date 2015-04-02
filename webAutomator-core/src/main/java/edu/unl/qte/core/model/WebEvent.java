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

package edu.unl.qte.core.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gigony on 1/12/15.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
  getterVisibility = JsonAutoDetect.Visibility.NONE,
  setterVisibility = JsonAutoDetect.Visibility.NONE,
  isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class WebEvent implements Event<WebEventElement>, Iterable<WebEventElement> {
  private List<WebEventElement> preConditions;
  private List<WebEventElement> actions;
  private List<WebEventElement> postConditions;

  public WebEvent() {
//    this.preConditions = new ArrayList<WebEventElement>();
    this.actions = new ArrayList<WebEventElement>();
//    this.postConditions = new ArrayList<WebEventElement>();
  }

  public WebEvent(final WebEventElement webElem) {
//    this.preConditions = new ArrayList<WebEventElement>();
    this.actions = new ArrayList<WebEventElement>();
//    this.postConditions = new ArrayList<WebEventElement>();

    this.actions.add(webElem);
  }

  @JsonCreator
  public WebEvent(@JsonProperty("preConditions") final List<WebEventElement> preConditions,
                  @JsonProperty("actions") final List<WebEventElement> actions,
                  @JsonProperty("postConditions") final List<WebEventElement> postConditions) {
    this.preConditions = preConditions;
    this.actions = actions;
    this.postConditions = postConditions;
  }


  @Override
  public final int size() {
    int totalSize = this.getPreConditionSize() + this.getActionSize() + this.getPostConditionSize();
    return totalSize;
  }

  @Override
  public final WebEventElement get(final int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(String.format("index is out of bound (index: %d)", index));
    }

    int modifiedIdx = index;

    int pivot = this.getPreConditionSize();
    if (modifiedIdx < pivot) {
      return this.getPreCondition(modifiedIdx);
    } else {
      modifiedIdx -= pivot;
    }

    pivot = this.getActionSize();
    if (modifiedIdx < pivot) {
      return this.getAction(modifiedIdx);
    } else {
      modifiedIdx -= pivot;
    }

    pivot = this.getPostConditionSize();
    if (modifiedIdx < pivot) {
      return this.getPostCondition(modifiedIdx);
    } else {
      throw new IndexOutOfBoundsException(String.format("index is out of bound (index: %d, size: %d)", index, this.size()));
    }
  }

  @Override
  public final WebEvent addPreCondition(final WebEventElement element) {
    if (this.preConditions == null) {
      this.preConditions = new ArrayList<WebEventElement>();
    }
    this.preConditions.add(element);
    return this;
  }

  @Override
  public final WebEvent addAction(final WebEventElement element) {
    this.actions.add(element);
    return this;
  }

  @Override
  public final WebEvent addPostCondition(final WebEventElement element) {
    if (this.postConditions == null) {
      this.postConditions = new ArrayList<WebEventElement>();
    }
    this.postConditions.add(element);
    return this;
  }

  @Override
  public final List<WebEventElement> getPreConditions() {
    return this.preConditions;
  }

  @Override
  public final WebEventElement getPreCondition(final int index) {
    return this.preConditions.get(index);
  }

  @Override
  public final WebEventElement getFirstPreCondition() {
    if (this.preConditions == null || this.preConditions.isEmpty()) {
      return null;
    }
    return this.preConditions.get(0);
  }

  @Override
  public final WebEventElement getLastPreCondition() {
    if (this.preConditions.isEmpty()) {
      return null;
    }
    return this.preConditions.get(this.preConditions.size() - 1);
  }

  @Override
  public final int getPreConditionSize() {
    return this.preConditions != null ? this.preConditions.size() : 0;
  }

  @Override
  public final List<WebEventElement> getActions() {
    return this.actions;
  }

  @Override
  public final WebEventElement getAction(final int index) {
    return this.actions.get(index);
  }

  @Override
  public final WebEventElement getAction() {
    return this.getAction(0);
  }

  @Override
  public final WebEventElement getFirstAction() {
    if (this.actions.isEmpty()) {
      return null;
    }
    return this.actions.get(0);
  }

  @Override
  public final WebEventElement getLastAction() {
    if (this.actions.isEmpty()) {
      return null;
    }
    return this.actions.get(this.actions.size() - 1);
  }

  @Override
  public final int getActionSize() {
    return this.actions.size();
  }

  @Override
  public final List<WebEventElement> getPostConditions() {
    return this.postConditions;
  }

  @Override
  public final WebEventElement getPostCondition(final int index) {
    return this.postConditions.get(index);
  }

  @Override
  public final WebEventElement getFirstPostCondition() {
    if (this.postConditions == null || this.postConditions.isEmpty()) {
      return null;
    }
    return this.postConditions.get(0);
  }

  @Override
  public final WebEventElement getLastPostCondition() {
    if (this.postConditions.isEmpty()) {
      return null;
    }
    return this.postConditions.get(this.postConditions.size() - 1);
  }

  @Override
  public final int getPostConditionSize() {
    return this.postConditions != null ? this.postConditions.size() : 0;
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(this.preConditions, this.actions, this.postConditions);
  }

  @Override
  public final boolean equals(final Object obj) {
    if (obj instanceof WebEvent) {
      WebEvent that = (WebEvent) obj;
      return Objects.equal(this.preConditions, that.preConditions)
        && Objects.equal(this.actions, that.actions)
        && Objects.equal(this.postConditions, that.postConditions);
    }
    return false;
  }

  @Override
  public final String toString() {
    return MoreObjects.toStringHelper(this)
      .add("preConditions", this.preConditions)
      .add("actions", this.actions)
      .add("postConditions", this.postConditions)
      .toString();
  }

  @Override
  public final Iterator<WebEventElement> iterator() {
    return new WebEventIterator(this);
  }

  private class WebEventIterator implements Iterator<WebEventElement> {
    private WebEvent webEvent;
    private int totSize;
    private int index;

    public WebEventIterator(final WebEvent webEvent) {
      this.webEvent = webEvent;
      this.index = 0;
      this.totSize = webEvent.size();
    }

    @Override
    public boolean hasNext() {
      return this.index < this.totSize;
    }

    @Override
    public WebEventElement next() {
      WebEventElement result = this.webEvent.get(this.index);
      this.index++;
      return result;
    }
  }
}
