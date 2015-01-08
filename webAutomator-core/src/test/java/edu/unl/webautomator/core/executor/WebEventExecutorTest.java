package edu.unl.webautomator.core.executor;

import edu.unl.webautomator.core.QTE;
import edu.unl.webautomator.core.StaticWebServer;
import edu.unl.webautomator.core.WebAutomator;
import edu.unl.webautomator.core.configuration.WebAutomatorConfiguration;
import edu.unl.webautomator.core.configuration.WebBrowserConfiguration;
import edu.unl.webautomator.core.configuration.WebEventTypes;
import edu.unl.webautomator.core.model.WebEvent;
import edu.unl.webautomator.core.model.WebState;
import edu.unl.webautomator.core.platform.WebBrowserType;
import org.junit.Test;

import java.util.List;

public class WebEventExecutorTest {

  @Test
  public final void testExecute() throws Exception {
    StaticWebServer.start("/fixture/components/");

    WebAutomatorConfiguration config = WebAutomatorConfiguration.builder()
      .setWebBrowserConfiguration(WebBrowserConfiguration.builder(WebBrowserType.FIREFOX)
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
//    automator.getWebBrowser().getSelenium().wait(3000);


    webState = automator.getStateExtractor().extractState();
    System.out.println(webState.getWebDoc().getDocument().toString());
//    automator.getWebBrowser().getSelenium().wait(3000);



    /*
     Output:
      WebEvent{eventType=click, frameId=, id=#a, input=}   --> this item will be removed by '.dontClick("css=frame[id=\"a\"]")' statement
      WebEvent{eventType=click, frameId=, id=#b, input=}
      WebEvent{eventType=click, frameId=, id=#c, input=}
      WebEvent{eventType=click, frameId=a, id=#aa, input=}
      WebEvent{eventType=click, frameId=a, id=#aaa, input=}
      WebEvent{eventType=click, frameId=a>aaa, id=#d, input=} --> this item will be removed by '.setIgnoringFrameIds("a>aaa")' statement
     */

    automator.quit();

  }
}
