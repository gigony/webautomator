package edu.unl.webautomator.core.extractor;

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

public class WebEventExtractorTest {

  /**
   * Test all default elements
   * @throws Exception
   */
  @Test
  public final void testGetAvailableEvents() throws Exception {
    StaticWebServer.start("/fixture/homepage/");

    WebAutomator automator = QTE.webAutomator(WebBrowserType.PHANTOMJS);

    WebState webState = automator.getStateExtractor().extractState("http://localhost:8080/nate.html");
    System.out.println(webState.getWebDoc().getJsonNode().toString());

    List<WebEvent> availableEvents = automator.getEventExtractor().getAvailableEvents(webState.getWebDoc());
    for (WebEvent webEvent : availableEvents) {
      System.out.println(webEvent.toString());
    }

    automator.quit();

  }

  /**
   * Test CSS Selector
   * @throws Exception
   */
  @Test
  public final void testGetAvailableEvents2() throws Exception {
    StaticWebServer.start("/fixture/frames/");

    WebAutomatorConfiguration config = WebAutomatorConfiguration.builder()
      .setWebBrowserConfiguration(WebBrowserConfiguration.builder(WebBrowserType.PHANTOMJS)
        .build())
      .setWebEventTypes(WebEventTypes.builder()
          .setDefaultEventTypes()
          .click("css=iframe")
          .dontClick("css=frame[id=\"a\"]")
          .click("css=frame").build()
      ).build();

    WebAutomator automator = QTE.webAutomator(config);

    WebState webState = automator.getStateExtractor().extractState("http://localhost:8080/frameset.html");
    System.out.println(webState.getWebDoc().getJsonNode().toString());

    List<WebEvent> availableEvents = automator.getEventExtractor().getAvailableEvents(webState.getWebDoc());
    for (WebEvent webEvent : availableEvents) {
      System.out.println(webEvent.toString());
    }
    /*
     Output:
      WebEvent{eventType=click, frameId=, id=#a, input=}   --> this item will be removed by '.dontClick("css=frame[id=\"a\"]")' statement
      WebEvent{eventType=click, frameId=, id=#b, input=}
      WebEvent{eventType=click, frameId=, id=#c, input=}
      WebEvent{eventType=click, frameId=a, id=#aa, input=}
      WebEvent{eventType=click, frameId=a, id=#aaa, input=}
      WebEvent{eventType=click, frameId=a>aaa, id=#d, input=}
     */

    automator.quit();

  }

  /**
   * Test XPATH selector
   * @throws Exception
   */
  @Test
  public final void testGetAvailableEvents3() throws Exception {
    StaticWebServer.start("/fixture/frames/");

    WebAutomatorConfiguration config = WebAutomatorConfiguration.builder()
      .setWebBrowserConfiguration(WebBrowserConfiguration.builder(WebBrowserType.PHANTOMJS)
        .build())
      .setWebEventTypes(WebEventTypes.builder()
        .click("//iframe")
        .dontClick("//frame[@id='a']")
        .click("xpath=//frame").build())
      .setIgnoringFrameIds("a>aaa")
      .build();

    WebAutomator automator = QTE.webAutomator(config);

    WebState webState = automator.getStateExtractor().extractState("http://localhost:8080/frameset.html");
    System.out.println(webState.getWebDoc().getJsonNode().toString());

    List<WebEvent> availableEvents = automator.getEventExtractor().getAvailableEvents(webState.getWebDoc());
    for (WebEvent webEvent : availableEvents) {
      System.out.println(webEvent.toString());
    }
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
