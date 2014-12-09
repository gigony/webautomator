package edu.unl.webautomator.core.configuration;


import edu.unl.webautomator.core.util.JacksonHelper;
import org.junit.Test;

import java.io.File;

public class WebAutomatorConfigurationTest {

    @Test
    public void configurationLoadTest() {
        WebAutomatorConfiguration config = new WebAutomatorConfigurationBuilder().build();
        File file = new File("/Users/gigony/Development/Repository/github/webautomator/temp/config.json");
        config.exportToJson(file);
        JacksonHelper.printObjectToJson(config);
        config = WebAutomatorConfiguration.importFromJson(file);
    }

}