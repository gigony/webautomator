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

import com.fasterxml.jackson.annotation.JsonAutoDetect;

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
public class WebTestCase implements TestCase<WebEventElement> {
  private String url = "";
  private List<Event<WebEventElement>> testCase = new ArrayList<Event<WebEventElement>>();

  public WebTestCase(final String url) {
    this.url = url;
  }

  @Override
  public final int size() {
    return this.testCase.size();
  }

  @Override
  public final void add(final Event<WebEventElement> event) {
    this.testCase.add(event);
  }

  @Override
  public final void remove(final int index) {
    this.testCase.remove(index);
  }

  @Override
  public final Event<WebEventElement> get(final int index) {
    return this.testCase.get(index);
  }

  @Override
  public final void set(final int index, final Event<WebEventElement> event) {
    this.testCase.set(index, event);
  }


  @Override
  public final Iterator<Event<WebEventElement>> iterator() {
    return this.testCase.iterator();
  }


  public final void setUrl(final String url) {
    this.url = url;
  }

  public final String getUrl() {
    return this.url;
  }
}
