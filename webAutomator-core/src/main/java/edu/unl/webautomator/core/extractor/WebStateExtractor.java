package edu.unl.webautomator.core.extractor;

import com.google.inject.Inject;
import edu.unl.webautomator.core.WebAutomator;
import edu.unl.webautomator.core.model.State;
import edu.unl.webautomator.core.platform.WebBrowser;

/**
 * Created by gigony on 12/6/14.
 */
public class WebStateExtractor implements StateExtractor {
  private WebAutomator webAutomator;

  @Inject
  public WebStateExtractor(final WebAutomator automator) {
    this.webAutomator = automator;
  }

  @Override
  public final State extractState() {
    WebBrowser browser = this.webAutomator.getWebBrowser();
    return null;



  }
}
