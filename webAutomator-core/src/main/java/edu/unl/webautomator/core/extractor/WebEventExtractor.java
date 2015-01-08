package edu.unl.webautomator.core.extractor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import edu.unl.webautomator.core.WebAutomator;
import edu.unl.webautomator.core.configuration.WebEventTypes;
import edu.unl.webautomator.core.model.WebDocument;
import edu.unl.webautomator.core.model.WebEvent;
import edu.unl.webautomator.core.model.WebEventType;
import edu.unl.webautomator.core.model.WebState;
import edu.unl.webautomator.core.util.JSoupHelper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
        Elements elements = this.select(doc, eventType);
        for (Element elem:elements) {
          String uniqueId = JSoupHelper.getCssSelector(elem);
          WebEvent event = new WebEvent(eventTypeName, frameId, uniqueId);
          availableElements.put(eventTypeName, event);
        }
      }
    }

    for (String eventTypeName : excludingEventTypes.keySet()) {
      Collection<WebEventType> eventTypes = excludingEventTypes.get(eventTypeName);
      Collection<WebEvent> includedCollection = availableElements.get(eventTypeName);
      for (WebEventType eventType : eventTypes) {
        Elements elements = this.select(doc, eventType);
        for (Element elem:elements) {
          String uniqueId = JSoupHelper.getCssSelector(elem);
          WebEvent event = new WebEvent(eventTypeName, frameId, uniqueId);
          includedCollection.remove(event);
        }
      }
    }

    ArrayList<WebEvent> result = new ArrayList<WebEvent>(availableElements.values());

    Map<String, WebDocument> frames = webDoc.getFrames();
    for (String id: frames.keySet()) {
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



  private Elements select(final Document doc, final WebEventType eventType) {
    Elements elements = null;

    if (eventType.isCssLocator()) {
      elements = doc.select(eventType.extractCssLocator());
    } else if (eventType.isXPathLocator()) {
      elements = Xsoup.select(doc, eventType.extractXPathLocator()).getElements();
    } else {
      throw new RuntimeException(String.format("'%s' should be a CSS locator or a XPATH locator"));
    }
    return elements;
  }
}
