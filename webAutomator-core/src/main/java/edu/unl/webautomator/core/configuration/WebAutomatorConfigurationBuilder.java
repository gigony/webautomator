package edu.unl.webautomator.core.configuration;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gigony on 12/7/14.
 */
public class WebAutomatorConfigurationBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(WebAutomatorConfigurationBuilder.class);

    private WebAutomatorConfiguration config;

    public WebAutomatorConfigurationBuilder() {
        config = new WebAutomatorConfiguration();

        config.plugins = WebAutomatorPlugins.defaultPlugins();
        config.browserConfiguration = WebBrowserConfiguration.defaultBrowser();
        config.proxyConfiguration = WebProxyConfiguration.noProxy();
        config.eventTypes = WebEventTypes.defaultEventTypes();
    }



    public WebAutomatorConfigurationBuilder setWebAutomatorPlugins(WebAutomatorPlugins plugins){
        Preconditions.checkNotNull(plugins);
        config.plugins = plugins;

        return this;
    }


    public WebAutomatorConfigurationBuilder setWebBrowserConfiguration(WebBrowserConfiguration browserConfiguration){
        Preconditions.checkNotNull(browserConfiguration);
        config.browserConfiguration=browserConfiguration;

        return this;
    }

    public WebAutomatorConfigurationBuilder setWebProxyConfiguration(WebProxyConfiguration proxyConfiguration) {
        Preconditions.checkNotNull(proxyConfiguration);
        config.proxyConfiguration=proxyConfiguration;

        return this;
    }
    public WebAutomatorConfigurationBuilder setWebEventTypes(WebEventTypes eventTypes){
        Preconditions.checkNotNull(eventTypes);
        config.eventTypes=eventTypes;

        return this;
    }

    public WebAutomatorConfiguration build() {
        return config;
    }
}
