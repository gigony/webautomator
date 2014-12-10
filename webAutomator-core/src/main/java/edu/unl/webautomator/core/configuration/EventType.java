package edu.unl.webautomator.core.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

/**
 * Created by gigony on 12/9/14.
 */
public class EventType {
    private String eventTypeName;
    private String eventLocator;

    @JsonCreator
    public EventType(@JsonProperty("eventTypeName") final String eTypeName,
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

    @Override
    public final int hashCode() {
        return Objects.hashCode(this.eventTypeName, this.eventLocator);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof EventType) {
            EventType that = (EventType) obj;
            return Objects.equal(this.eventTypeName, that.eventTypeName)
                    && Objects.equal(this.eventLocator, that.eventLocator);
        }
        return false;
    }
}
