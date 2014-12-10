package edu.unl.webautomator.core.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

/**
 * Created by gigony on 12/6/14.
 */
public class WebEventTypes {

    private final ImmutableMultimap<String, EventType> includingEventTypeMap;
    private final ImmutableMultimap<String, EventType> excludingEventTypeMap;

    @JsonCreator
    WebEventTypes(@JsonProperty("includingEventTypeMap") final Multimap<String, EventType> includingETypeMap,
                  @JsonProperty("excludingEventTypeMap") final Multimap<String, EventType> excludingETypeMap) {
        this.includingEventTypeMap = ImmutableMultimap.copyOf(includingETypeMap);
        this.excludingEventTypeMap = ImmutableMultimap.copyOf(excludingETypeMap);
    }

    public final ImmutableMultimap<String, EventType> getIncludingEventTypeMap() {
        return this.includingEventTypeMap;
    }

    public final ImmutableMultimap<String, EventType> getExcludingEventTypeMap() {
        return this.excludingEventTypeMap;
    }

    public static WebEventTypesBuilder builder() {
        return new WebEventTypesBuilder();
    }

    public static WebEventTypes defaultEventTypes() {
        return new WebEventTypesBuilder().setDefaultEventTypes().build();
    }


    public static class WebEventTypesBuilder {
        private Multimap<String, EventType> includingEventTypeMap;
        private Multimap<String, EventType> excludingEventTypeMap;

        public WebEventTypesBuilder() {
            this.includingEventTypeMap = HashMultimap.create();
            this.excludingEventTypeMap = HashMultimap.create();
        }

        public final WebEventTypesBuilder setDefaultEventTypes() {
            this.click("css=a");
            this.click("css=button");
            this.click("css=input[type=\"submit\"]");
            this.click("css=input[type=\"button\"]");
            this.type("css=input[type=\"text\"]");
            this.type("css=input[type=\"password\"]");
            this.select("css=select");
            return this;
        }

        public final WebEventTypesBuilder includeEventType(final String eventTypeName, final String locator) {
            this.includingEventTypeMap.put(eventTypeName, new EventType(eventTypeName, locator));
            return this;
        }

        public final WebEventTypesBuilder excludeEventType(final String eventTypeName, final String locator) {
            this.excludingEventTypeMap.put(eventTypeName, new EventType(eventTypeName, locator));
            return this;
        }


        public final WebEventTypesBuilder click(final String locator) {
            return this.includeEventType("click", locator);
        }

        public final WebEventTypesBuilder select(final String locator) {
            return this.includeEventType("select", locator);
        }

        public final WebEventTypesBuilder type(final String locator) {
            return this.includeEventType("type", locator);
        }

        public final WebEventTypesBuilder dontClick(final String locator) {
            return this.excludeEventType("click", locator);
        }

        public final WebEventTypesBuilder dontSelect(final String locator) {
            return this.excludeEventType("select", locator);
        }

        public final WebEventTypesBuilder dontType(final String locator) {
            return this.excludeEventType("type", locator);
        }

        public final WebEventTypes build() {
            return new WebEventTypes(this.includingEventTypeMap, this.excludingEventTypeMap);
        }


    }
}
