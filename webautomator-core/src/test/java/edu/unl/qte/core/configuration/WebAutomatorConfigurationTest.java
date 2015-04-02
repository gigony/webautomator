package edu.unl.qte.core.configuration;


import edu.unl.qte.core.util.JacksonHelper;
import org.junit.Test;

import java.io.File;

public class WebAutomatorConfigurationTest {

  @Test
  public final void configurationLoadTest() {
    WebAutomatorConfiguration config = new WebAutomatorConfiguration.WebAutomatorConfigurationBuilder().build();
    File file = new File("/Users/gigony/Development/Repository/github/webautomator/temp/config.json");
    config.exportToJson(file);
    JacksonHelper.printObjectToJson(config);
    config = WebAutomatorConfiguration.importFromJson(file);
  }

}
