package edu.unl.webautomator.core.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import edu.unl.webautomator.core.util.JSoupHelper;
import edu.unl.webautomator.core.util.JacksonHelper;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gigony on 12/18/14.
 */
public class WebDocument {
  public static final String FRAME_SPLITTER = ">";
  public static final String FRAME_SPLITTER_MATCHER = "\\s*>[>\\s]*";

  private Document document;
  private Map<String, WebDocument> frames = Maps.newHashMap();

  public WebDocument(final Document doc) {
    this.document = doc;
  }

  public final Document getDocument() {
    return this.document;
  }

  public final Map<String, WebDocument> getFrames() {
    return this.frames;
  }

  public final WebDocument getFrame(final String id) {
    Preconditions.checkNotNull(id);
    if (id.equals("")) {
      return this;
    }

    String[] names = id.split(FRAME_SPLITTER_MATCHER);
    WebDocument currDoc = this;
    for (String name : names) {
      currDoc = currDoc.frames.get(name);
      if (currDoc == null) {
        throw new RuntimeException("Error found while finding frame (id: " + id + ")");
      }
    }
    return currDoc;
  }

  public final void appendFrame(final String id, final Document doc) {
    Preconditions.checkNotNull(id);
    Preconditions.checkNotNull(doc);

    this.frames.put(id, new WebDocument(doc));
  }

  public final void appendFrame(final String id, final WebDocument doc) {
    Preconditions.checkNotNull(id);
    Preconditions.checkNotNull(doc);
    if (id.indexOf(FRAME_SPLITTER) >= 0) {
      throw new RuntimeException("Append frame with id not having '>'");
    }
    this.frames.put(id, doc);
  }

//  public final String toPageSourceWithFrameContent() {
//    Element frameCollection = new Element(Tag.valueOf("collection"), "");
//    for (String frameId : this.frames.keySet()) {
//      Element frame = new Element(Tag.valueOf("item"), "");
//      frame.attr("frameId", frameId);
//      frameCollection.appendChild(frame);
//      frame.appendChild(this.frames.get(frameId).getDocument());
//    }
//
//    return this.document.toString();
//  }

  public final String toJson() {
    try {
      return JacksonHelper.getObjectMapper().writeValueAsString(this.getJsonNode());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public final JsonNode getJsonNode() {
    ObjectNode node = JacksonHelper.getObjectMapper().createObjectNode();
    node.put("document", this.document.toString());
    JsonNode frameCollection = this.getFrameCollectionNode();
    if (frameCollection.size() > 0) {
      node.set("frames", frameCollection);
    }

    return node;
  }

  private JsonNode getFrameCollectionNode() {
    ObjectNode node = JacksonHelper.getObjectMapper().createObjectNode();
    for (String frameId : this.frames.keySet()) {
      WebDocument frame = this.frames.get(frameId);
      node.set(frameId, frame.getJsonNode());
    }
    return node;
  }

  public static WebDocument getDocumentFromJson(final String json) {
    try {
      JsonNode node = JacksonHelper.getObjectMapper().readTree(json);
      return getDocumentFromJson(node);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static WebDocument getDocumentFromJson(final JsonNode node) {
    String html = node.get("document").asText();
    Document dom = JSoupHelper.parse(html);

    WebDocument doc = new WebDocument(dom);

    if (node.has("frames")) {
      JsonNode frames = node.get("frames");
      Iterator<String> iter = frames.fieldNames();
      while (iter.hasNext()) {
        String frameId = iter.next();
        JsonNode frameNode = frames.get(frameId);
        WebDocument frame = getDocumentFromJson(frameNode);

        doc.appendFrame(frameId, frame);
      }
    }

    return doc;
  }
}
