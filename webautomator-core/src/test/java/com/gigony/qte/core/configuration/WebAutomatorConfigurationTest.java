package com.gigony.qte.core.configuration;


import com.gigony.qte.core.util.JacksonHelper;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class WebAutomatorConfigurationTest {

  @Test
  public final void configurationLoadTest() {
    WebAutomatorConfiguration config = new WebAutomatorConfiguration.WebAutomatorConfigurationBuilder().build();
    File file = new File("config.json");
    config.exportToJson(file);
    JacksonHelper.printObjectToJson(config);
    String src = JacksonHelper.saveObjectToJsonString(config);
    config = WebAutomatorConfiguration.importFromJson(file);
    String target = JacksonHelper.saveObjectToJsonString(config);
    Assert.assertEquals(src, target);
  }

}
