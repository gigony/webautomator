package edu.unl.webautomator.core.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.unl.webautomator.core.util.JacksonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by gigony on 12/6/14.
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class WebAutomatorConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(WebAutomatorConfiguration.class);

    WebAutomatorPlugins plugins = WebAutomatorPlugins.defaultPlugins();
    WebBrowserConfiguration browserConfiguration;
    WebProxyConfiguration proxyConfiguration = WebProxyConfiguration.noProxy();
    WebEventTypes eventTypes;

    WebAutomatorConfiguration() {
    }

    @JsonCreator
    WebAutomatorConfiguration(@JsonProperty("plugins") WebAutomatorPlugins plugins,
                              @JsonProperty("browserConfiguration") WebBrowserConfiguration browserConfiguration,
                              @JsonProperty("proxyConfiguration") WebProxyConfiguration proxyConfiguration,
                              @JsonProperty("eventTypes") WebEventTypes eventTypes) {
        this.plugins = plugins;
        this.browserConfiguration = browserConfiguration;
        this.proxyConfiguration = proxyConfiguration;
        this.eventTypes = eventTypes;
    }

    public WebAutomatorPlugins getPlugins() {
        return plugins;
    }

    public WebBrowserConfiguration getBrowserConfiguration() {
        return browserConfiguration;
    }

    public WebProxyConfiguration getProxyConfiguration() {
        return proxyConfiguration;
    }

    public WebEventTypes getEventTypes() {
        return eventTypes;
    }

    public <T> Class<T> getPluginClass(String pluginName) {
        return plugins.getPluginClass(pluginName);
    }


    public static WebAutomatorConfiguration defaultConfiguration() {
        return new WebAutomatorConfigurationBuilder().build();
    }

    public static WebAutomatorConfiguration importFromJson(File jsonFile) {
        return JacksonHelper.loadObjectFromJson(jsonFile, WebAutomatorConfiguration.class);
    }

    public void exportToJson(File jsonFile) {
        JacksonHelper.saveObjectToJson(jsonFile, this);
    }


}
