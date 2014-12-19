package edu.unl.webautomator.core.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import edu.unl.webautomator.core.platform.WebBrowserType;
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
    private WebAutomatorPlugins pluginConfiguration;
    private WebBrowserConfiguration browserConfiguration;
    private WebEventTypes eventTypes;




    WebAutomatorConfiguration() {
    }

    @JsonCreator
    WebAutomatorConfiguration(@JsonProperty("pluginConfiguration") final WebAutomatorPlugins pluginConfig,
                              @JsonProperty("browserConfiguration") final WebBrowserConfiguration browserConfig,
                              @JsonProperty("eventTypes") final WebEventTypes eTypes) {
        this.pluginConfiguration = pluginConfig;
        this.browserConfiguration = browserConfig;
        this.eventTypes = eTypes;
    }

    public final WebAutomatorPlugins getPluginConfiguration() {
        return this.pluginConfiguration;
    }

    public final WebBrowserConfiguration getBrowserConfiguration() {
        return this.browserConfiguration;
    }


    public final WebEventTypes getEventTypes() {
        return this.eventTypes;
    }

    public final <T> Class<T> getPluginClass(final String pluginName) {
        return this.pluginConfiguration.getPluginClass(pluginName);
    }

    public static WebAutomatorConfiguration create(final WebBrowserType browserType) {
        return builder().setWebBrowserConfiguration(
                WebBrowserConfiguration.builder(browserType)
                        .build()
        ).build();
    }

    public static WebAutomatorConfiguration create(final WebBrowserType browserType, final String webDriverBinPath) {
        return builder().setWebBrowserConfiguration(
                WebBrowserConfiguration.builder(browserType)
                        .setWebDriverBinaryPath(webDriverBinPath)
                        .build()
        ).build();
    }

    public static WebAutomatorConfigurationBuilder builder() {
        return new WebAutomatorConfigurationBuilder();
    }

    public static WebAutomatorConfiguration defaultConfiguration() {
        return new WebAutomatorConfigurationBuilder().build();
    }

    public static WebAutomatorConfiguration importFromJson(final File jsonFile) {
        return JacksonHelper.loadObjectFromJsonFile(jsonFile, WebAutomatorConfiguration.class);
    }

    public final void exportToJson(final File jsonFile) {
        JacksonHelper.saveObjectToJsonFile(jsonFile, this);
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(this.pluginConfiguration, this.browserConfiguration, this.eventTypes);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof WebAutomatorConfiguration) {
            WebAutomatorConfiguration that = (WebAutomatorConfiguration) obj;
            return Objects.equal(this.pluginConfiguration, that.pluginConfiguration)
                    && Objects.equal(this.browserConfiguration, that.browserConfiguration)
                    && Objects.equal(this.eventTypes, that.eventTypes);

        }
        return false;
    }


    public static class WebAutomatorConfigurationBuilder {
        private static final Logger LOG = LoggerFactory.getLogger(WebAutomatorConfigurationBuilder.class);

        private WebAutomatorConfiguration config;

        public WebAutomatorConfigurationBuilder() {
            this.config = new WebAutomatorConfiguration();

            this.config.pluginConfiguration = WebAutomatorPlugins.defaultPlugins();
            this.config.browserConfiguration = WebBrowserConfiguration.defaultBrowser();
            this.config.eventTypes = WebEventTypes.defaultEventTypes();
        }


        public final WebAutomatorConfigurationBuilder setWebAutomatorPlugins(final WebAutomatorPlugins plugins) {
            Preconditions.checkNotNull(plugins);
            this.config.pluginConfiguration = plugins;

            return this;
        }


        public final WebAutomatorConfigurationBuilder setWebBrowserConfiguration(final WebBrowserConfiguration browserConfiguration) {
            Preconditions.checkNotNull(browserConfiguration);
            this.config.browserConfiguration = browserConfiguration;

            return this;
        }


        public final WebAutomatorConfigurationBuilder setWebEventTypes(final WebEventTypes eventTypes) {
            Preconditions.checkNotNull(eventTypes);
            this.config.eventTypes = eventTypes;

            return this;
        }

        public final WebAutomatorConfiguration build() {
            return this.config;
        }
    }
}
