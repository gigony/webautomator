package edu.unl.webautomator.core.extractor;

import edu.unl.webautomator.core.QTE;
import edu.unl.webautomator.core.StaticWebServer;
import edu.unl.webautomator.core.WebAutomator;
import edu.unl.webautomator.core.model.WebDocument;
import edu.unl.webautomator.core.model.WebState;
import edu.unl.webautomator.core.platform.WebBrowserType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WebStateExtractorTest {

  @Test
  public final void testExtractState() throws Exception {
    StaticWebServer.start("/fixture/frames/");

    WebAutomator automator = QTE.webAutomator(WebBrowserType.PHANTOMJS);

    WebState webState = automator.getStateExtractor().extractState("http://localhost:8080/frameset.html");

    WebDocument doc = webState.getWebDoc();

    assertEquals(doc.getUri().toString(), "http://localhost:8080/frameset.html");
    assertEquals(doc.getFrame("a").getUri().toString(), "http://localhost:8080/frame_a.htm");
    assertEquals(doc.getFrame("b").getUri().toString(), "http://localhost:8080/frame_b.htm");
    assertEquals(doc.getFrame("a>aa").getUri().toString(), "http://localhost:8080/frame_aa.htm");
    assertEquals(doc.getFrame("a>aaa").getUri().toString(), "http://localhost:8080/frame_aaa.htm");
    assertEquals(doc.getFrame("a>aaa>d").getUri().toString(), "http://localhost:8080/frame_d.htm");

    System.out.println(doc.getJsonNode().toString());

    automator.quit();
  }
}
