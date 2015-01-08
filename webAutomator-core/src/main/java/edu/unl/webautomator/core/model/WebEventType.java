package edu.unl.webautomator.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import edu.unl.webautomator.core.util.SeleniumHelper;

/**
 * Created by gigony on 1/7/15.
 */
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
