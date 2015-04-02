package edu.unl.qte.core.executor;

import edu.unl.qte.core.QTE;
import edu.unl.qte.core.StaticWebServer;
import edu.unl.qte.core.WebAutomator;
import edu.unl.qte.core.model.WebState;
import edu.unl.qte.core.configuration.WebAutomatorConfiguration;
import edu.unl.qte.core.configuration.WebBrowserConfiguration;
import edu.unl.qte.core.configuration.WebEventTypes;
import edu.unl.qte.core.model.WebEvent;
import edu.unl.qte.core.platform.WebBrowserType;
import org.junit.Test;

import java.util.List;

public class WebEventExecutorTest {

  @Test
  public final void testExecute() throws Exception {
    StaticWebServer.start("/fixture/components/");

    WebAutomatorConfiguration config = WebAutomatorConfiguration.builder()
      .setWebBrowserConfiguration(WebBrowserConfiguration.builder(WebBrowserType.PHANTOMJS)
//        .setWebDriverBinaryPath("/Users/gigony/Development/Repository/github/webautomator/webautomator-core/build/webdrivers/chromedriver")
        .build())
      .setWebEventTypes(WebEventTypes.builder()
        .setDefaultEventTypes()
        .build())
      .setIgnoringFrameIds("")
      .build();


    WebAutomator automator = QTE.webAutomator(config);

    WebState webState = automator.getStateExtractor().extractState("http://localhost:8080/button.html");
    System.out.println(webState.getWebDoc().getDocument().toString());

    List<WebEvent> availableEvents = automator.getEventExtractor().getAvailableEvents(webState.getWebDoc());
    for (WebEvent webEvent : availableEvents) {
      System.out.println(webEvent.toString());
    }
    automator.execute(availableEvents.get(0));

    webState = automator.getStateExtractor().extractState();
    System.out.println(webState.getWebDoc().getDocument().toString());


    automator.quit();

  }
}
