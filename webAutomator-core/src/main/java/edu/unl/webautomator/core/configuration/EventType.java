package edu.unl.webautomator.core.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

/**
 * Created by gigony on 12/9/14.
 */
public class EventType {
    private String eventTypeName;
    private String locator;

    @JsonCreator
    public EventType(@JsonProperty("eventTypeName") String eventTypeName,
                     @JsonProperty("locator") String locator) {
        this.eventTypeName=eventTypeName;
        this.locator=locator;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public String getLocator() {
        return locator;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(eventTypeName, locator);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventType){
            EventType that = (EventType)obj;
            return Objects.equal(eventTypeName,that.eventTypeName) &&
                    Objects.equal(locator,that.locator);
        }
        return false;
    }
}
