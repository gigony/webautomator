package edu.unl.webautomator.core.executor;


import com.google.inject.Inject;
import edu.unl.webautomator.core.WebAutomator;
import edu.unl.webautomator.core.model.Event;
import edu.unl.webautomator.core.model.WebEvent;
import edu.unl.webautomator.core.platform.WebBrowser;
import edu.unl.webautomator.core.util.MyWebDriverBackedSelenium;

/**
 * Created by gigony on 12/6/14.
 */
public class WebEventExecutor implements EventExecutor {
  private WebAutomator webAutomator;


  @Inject
  public WebEventExecutor(final WebAutomator automator) {
    this.webAutomator = automator;

  }

  @Override
  public final void execute(final Event e) {
    WebBrowser webBrowser = this.webAutomator.getWebBrowser();
    MyWebDriverBackedSelenium selenium = webBrowser.getSelenium();
    WebEvent event = (WebEvent) e;

    String oldFrameId = webBrowser.getFrameId();

    String frameId = event.getFrameId();
    webBrowser.moveToAbsoluteFrame(frameId);

    selenium.doCommand(event.getEventType(), event.getCssLocator(), event.getInput());

    webBrowser.moveToAbsoluteFrame(oldFrameId);
  }
}
