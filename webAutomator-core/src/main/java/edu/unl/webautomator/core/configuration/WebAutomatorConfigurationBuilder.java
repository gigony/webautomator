package edu.unl.webautomator.core.configuration;

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
        //config.
    }

    public WebAutomatorConfiguration build() {

        return config;
    }
}
