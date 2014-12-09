package edu.unl.webautomator.core.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import edu.unl.webautomator.core.util.JacksonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by gigony on 12/6/14.
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
                getterVisibility = JsonAutoDetect.Visibility.NONE,
                setterVisibility = JsonAutoDetect.Visibility.NONE,
                isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class WebAutomatorConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(WebAutomatorConfiguration.class);

    WebAutomatorPlugins plugins;
    WebBrowserConfiguration browserConfiguration;
    WebProxyConfiguration proxyConfiguration;
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

    @Override
    public int hashCode() {
        return Objects.hashCode(plugins,browserConfiguration,proxyConfiguration,eventTypes);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WebAutomatorConfiguration){
            WebAutomatorConfiguration that = (WebAutomatorConfiguration)obj;
            return Objects.equal(plugins,that.plugins) &&
                    Objects.equal(browserConfiguration,that.browserConfiguration) &&
                    Objects.equal(proxyConfiguration,that.proxyConfiguration) &&
                    Objects.equal(eventTypes,that.eventTypes);

        }
        return false;
    }
}
