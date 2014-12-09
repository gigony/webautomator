package edu.unl.webautomator.core.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

/**
 * Created by gigony on 12/6/14.
 */
public class WebEventTypes {

    private final ImmutableMultimap<String,EventType> includingEventTypeMap;
    private final ImmutableMultimap<String,EventType> excludingEventTypeMap;

    @JsonCreator
    WebEventTypes(@JsonProperty("includingEventTypeMap")Multimap<String,EventType> includingEventTypeMap,
                  @JsonProperty("excludingEventTypeMap")Multimap<String,EventType> excludingEventTypeMap){
        this.includingEventTypeMap = ImmutableMultimap.copyOf(includingEventTypeMap);
        this.excludingEventTypeMap = ImmutableMultimap.copyOf(excludingEventTypeMap);
    }

    public ImmutableMultimap<String, EventType> getIncludingEventTypeMap() {
        return includingEventTypeMap;
    }

    public ImmutableMultimap<String, EventType> getExcludingEventTypeMap() {
        return excludingEventTypeMap;
    }


    public static WebEventTypes defaultEventTypes() {
        return new WebEventTypesBuilder().setDefaultEventTypes().build();
    }
}
