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

package edu.unl.webautomator.core.extractor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import edu.unl.webautomator.core.WebAutomator;
import edu.unl.webautomator.core.configuration.WebEventTypes;
import edu.unl.webautomator.core.model.*;
import edu.unl.webautomator.core.util.JSoupHelper;
import edu.unl.webautomator.core.util.SeleniumHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

import java.util.*;

/**
 * Created by gigony on 12/6/14.
 */
public class WebEventExtractor implements EventExtractor {
  private WebAutomator webAutomator;

  @Inject
  public WebEventExtractor(final WebAutomator automator) {
    this.webAutomator = automator;
  }

  @Override
  public final List<WebEvent> getAvailableEvents() {
    WebState state = this.webAutomator.getState();

    List<WebEvent> events = this.getAvailableEvents(state.getWebDoc());
    return events;
  }

  public final List<WebEvent> getAvailableEvents(final WebDocument webDoc) {
    WebEventTypes eventTypes = this.webAutomator.getConfiguration().getEventTypes();

    ImmutableMultimap<String, WebEventType> includingEventTypes = eventTypes.getIncludingEventTypeMap();
    ImmutableMultimap<String, WebEventType> excludingEventTypes = eventTypes.getExcludingEventTypeMap();

    return this.getAvailableEvents(webDoc, "", includingEventTypes, excludingEventTypes);
  }

  public final List<WebEvent> getAvailableEvents(final WebDocument webDoc, final ImmutableMultimap<String, WebEventType> includingEventTypes) {
    return this.getAvailableEvents(webDoc, "", includingEventTypes, ImmutableMultimap.<String, WebEventType>of());
  }

  public final ArrayList<WebEvent> getAvailableEvents(final WebDocument webDoc, final String frameId, final ImmutableMultimap<String, WebEventType> includingEventTypes, final ImmutableMultimap<String, WebEventType> excludingEventTypes) {
    Document doc = webDoc.getDocument();
    Multimap<String, WebEvent> availableElements = LinkedHashMultimap.create();
    for (String eventTypeName : includingEventTypes.keySet()) {
      Collection<WebEventType> eventTypes = includingEventTypes.get(eventTypeName);
      for (WebEventType eventType : eventTypes) {
        Elements elements = this.select(doc, eventType.getEventLocator());
        for (Element elem : elements) {
          String uniqueId = "css=" + JSoupHelper.getCssSelector(elem);
          WebEventElement eventElement = new WebEventElement(eventTypeName, frameId, uniqueId);
          WebEvent event = new WebEvent(eventElement);
          availableElements.put(eventTypeName, event);
        }
      }
    }

    for (String eventTypeName : excludingEventTypes.keySet()) {
      Collection<WebEventType> eventTypes = excludingEventTypes.get(eventTypeName);
      Collection<WebEvent> includedCollection = availableElements.get(eventTypeName);
      for (WebEventType eventType : eventTypes) {
        Elements elements = this.select(doc, eventType.getEventLocator());
        for (Element elem : elements) {
          String uniqueId = "css=" + JSoupHelper.getCssSelector(elem);
          WebEventElement eventElement = new WebEventElement(eventTypeName, frameId, uniqueId);
          WebEvent event = new WebEvent(eventElement);
          includedCollection.remove(event);
        }
      }
    }

    ArrayList<WebEvent> result = new ArrayList<WebEvent>(availableElements.values());

    Map<String, WebDocument> frames = webDoc.getFrames();
    for (String id : frames.keySet()) {
      WebDocument frame = frames.get(id);
      ArrayList<WebEvent> frameResult;
      if (frameId.equals("")) {
        frameResult = this.getAvailableEvents(frame, id, includingEventTypes, excludingEventTypes);
      } else {
        frameResult = this.getAvailableEvents(frame, String.format("%s%s%s", frameId, WebDocument.FRAME_SPLITTER, id), includingEventTypes, excludingEventTypes);
      }
      result.addAll(frameResult);
    }

    return result;
  }


  public static Set<String> selectIdSet(final WebDocument webDoc, final String locator) {
    Set<String> result = selectIdSet(webDoc.getDocument(), locator);

    Map<String, WebDocument> frames = webDoc.getFrames();
    for (String id : frames.keySet()) {
      WebDocument frame = frames.get(id);
      Set<String> frameResult;
      frameResult = selectIdSet(frame, locator);
      result.addAll(frameResult);
    }
    return result;
  }

  public static Set<String> selectIdSet(final Document doc, final String locator) {
    Set<String> result = new HashSet<String>();

    Elements elements = select(doc, locator);
    for (Element elem : elements) {
      String uniqueId = "css=" + JSoupHelper.getCssSelector(elem);
      result.add(uniqueId);
    }
    return result;
  }

  public static Elements select(final Document doc, final String locator) {
    Elements elements = null;

    if (SeleniumHelper.isCssLocator(locator)) {
      elements = doc.select(SeleniumHelper.extractCssLocator(locator));
    } else if (SeleniumHelper.isXPathLocator(locator)) {
      elements = Xsoup.select(doc, SeleniumHelper.extractXPathLocator(locator)).getElements();
    } else {
      throw new RuntimeException(String.format("'%s' should be a CSS locator or a XPATH locator", locator));
    }
    return elements;
  }
}
