package edu.unl.webautomator.core.configuration;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Created by gigony on 12/9/14.
 */
public class WebEventTypesBuilder {

    private Multimap<String,EventType> includingEventTypeMap;
    private Multimap<String,EventType> excludingEventTypeMap;

    public WebEventTypesBuilder(){
        includingEventTypeMap = HashMultimap.create();
        excludingEventTypeMap = HashMultimap.create();
    }

    public WebEventTypesBuilder setDefaultEventTypes() {
        click("css=a");
        click("css=button");
        click("css=input[type=\"submit\"]");
        click("css=input[type=\"button\"]");
        type("css=input[type=\"text\"]");
        type("css=input[type=\"password\"]");
        select("css=select");
        return this;
    }

    public WebEventTypesBuilder includeEventType(String eventTypeName, String locator) {
        includingEventTypeMap.put(eventTypeName,new EventType(eventTypeName,locator));
        return this;
    }

    public WebEventTypesBuilder excludeEventType(String eventTypeName, String locator) {
        excludingEventTypeMap.put(eventTypeName, new EventType(eventTypeName, locator));
        return this;
    }


    public WebEventTypesBuilder click(String locator) {
        return includeEventType("click", locator);
    }

    private WebEventTypesBuilder select(String locator) {
        return includeEventType("select", locator);
    }

    private WebEventTypesBuilder type(String locator) {
        return includeEventType("type", locator);
    }

    private WebEventTypesBuilder dontClick(String locator) {
        return excludeEventType("click", locator);
    }

    private WebEventTypesBuilder dontSelect(String locator) {
        return excludeEventType("select", locator);
    }

    private WebEventTypesBuilder dontType(String locator) {
        return excludeEventType("type", locator);
    }

    public WebEventTypes build(){
        return new WebEventTypes(includingEventTypeMap,excludingEventTypeMap);
    }


}
