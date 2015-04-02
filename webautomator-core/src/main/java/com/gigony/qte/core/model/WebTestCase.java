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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gigony on 12/7/14.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
  getterVisibility = JsonAutoDetect.Visibility.NONE,
  setterVisibility = JsonAutoDetect.Visibility.NONE,
  isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class WebTestCase implements TestCase<WebEvent>, Iterable<WebEvent> {
  private String title = "";
  private String baseUrl = "";
  private WebEvent prefixEvent = new WebEvent();
  private List<WebEvent> testCase = new ArrayList<WebEvent>();


  @JsonCreator
  public WebTestCase(@JsonProperty("title") final String title,
                     @JsonProperty("baseUrl") final String baseUrl,
                     @JsonProperty("prefixEvent") final WebEvent prefixEvent,
                     @JsonProperty("testCase") final List<WebEvent> testCase) {
    this.title = title;
    this.baseUrl = baseUrl;
    this.prefixEvent = prefixEvent;
    this.testCase = testCase;
  }

  public WebTestCase(final String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public WebTestCase(final String title, final String baseUrl) {
    this.title = title;
    this.baseUrl = baseUrl;
  }

  @Override
  public final int size() {
    return this.testCase.size();
  }

  public final WebEvent getPrefixEvent() {
    return this.prefixEvent;
  }

  @Override
  public final void setPrefixEvent(final WebEvent event) {
    this.prefixEvent = event;
  }

  @Override
  public final void add(final WebEvent event) {
    this.testCase.add(event);
  }

  @Override
  public final void remove(final int index) {
    this.testCase.remove(index);
  }

  @Override
  public final WebEvent get(final int index) {
    return this.testCase.get(index);
  }

  @Override
  public final void set(final int index, final WebEvent event) {
    this.testCase.set(index, event);
  }

  public final Iterator<WebEvent> iterator() {
    return new WebTestCaseIterator(this);
  }

  public final String getTitle() {
    return this.title;
  }

  public final void setTitle(final String title) {
    this.title = title;
  }

  public final void setBaseUrl(final String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public final String getBaseUrl() {
    return this.baseUrl;
  }


  private class WebTestCaseIterator implements Iterator<WebEvent> {
    private WebTestCase webTestCase;
    private int index;
    private int totalSize;

    public WebTestCaseIterator(final WebTestCase webTestCase) {
      this.webTestCase = webTestCase;
      this.index = webTestCase.getPrefixEvent() != null && webTestCase.getPrefixEvent().size() > 0 ? -1 : 0;
      this.totalSize = webTestCase.size();
    }

    @Override
    public boolean hasNext() {
      return this.index < this.totalSize;
    }

    @Override
    public WebEvent next() {
      WebEvent result;
      if (this.index == -1) {
        result = this.webTestCase.getPrefixEvent();
      } else {
        result = this.webTestCase.get(this.index);
      }
      this.index++;
      return result;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("remove");
    }
  }
}
