package edu.unl.webautomator.core.configuration;


import org.junit.Test;

import java.io.File;

public class WebAutomatorConfigurationTest {

    @Test
    public void configurationLoadTest() {
        WebAutomatorConfiguration config = new WebAutomatorConfiguration();
        File file = new File("/Users/gigony/Development/Repository/github/webautomator/temp/config.json");
        config.exportToJson(file);
        config = WebAutomatorConfiguration.importFromJson(file);
    }

}