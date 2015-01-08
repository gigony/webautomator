package edu.unl.webautomator.core.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Created by gigony on 12/6/14.
 */
public class WebEvent implements Event {
  private String eventType;
  private String frameId;
  private String id;
  private String input;


  public WebEvent(final String eventTypeName, final String uniqueId) {
    this.eventType = eventTypeName;
    this.frameId = "";
    this.id = uniqueId;
    this.input = "";
  }

  public WebEvent(final String eventTypeName, final String frameId, final String uniqueId) {
    this.eventType = eventTypeName;
    this.frameId = frameId;
    this.id = uniqueId;
    this.input = "";
  }
  @JsonCreator
  public WebEvent(@JsonProperty("eventType") final String eventTypeName,
                  @JsonProperty("frameId") final String frameId,
                  @JsonProperty("id") final String uniqueId,
                  @JsonProperty("input") final String input) {
    this.eventType = eventTypeName;
    this.frameId = frameId;
    this.id = uniqueId;
    this.input = input;
  }

  @Override
  public final String getEventType() {
    return this.eventType;
  }

  public final String getFrameId() {
    return this.frameId;
  }

  @Override
  public final String getId() {
    return this.id;
  }
  @Override
  public final String getInput() {
    return this.input;
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(this.eventType, this.frameId, this.id, this.input);
  }

  @Override
  public final boolean equals(final Object obj) {
    if (obj instanceof WebEvent) {
      WebEvent that = (WebEvent) obj;
      return Objects.equal(this.eventType, that.eventType)
        && Objects.equal(this.frameId, that.frameId)
        && Objects.equal(this.id, that.id)
        && Objects.equal(this.input, that.input);
    }
    return false;
  }

  @Override
  public final String toString() {
    return MoreObjects.toStringHelper(this)
      .add("eventType", this.eventType)
      .add("frameId", this.frameId)
      .add("id", this.id)
      .add("input", this.input)
      .toString();
  }
}
