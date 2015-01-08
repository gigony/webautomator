package edu.unl.webautomator.core.extractor;

import com.google.inject.Inject;
import edu.unl.webautomator.core.WebAutomator;
import edu.unl.webautomator.core.model.WebDocument;
import edu.unl.webautomator.core.model.WebState;
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
  public final WebState extractState() {
    WebBrowser browser = this.webAutomator.getWebBrowser();

    WebDocument webDoc = browser.getPageDomWithFrameContent();
    WebState webState = new WebState(webDoc);

    return webState;
  }

  @Override
  public final WebState extractState(final String uri) {
    return this.extractState(uri, this.webAutomator.getConfiguration().getPageLoadTimeOut());
  }

  public final WebState extractState(final String uri, final long timeout) {
    WebBrowser browser = this.webAutomator.getWebBrowser();
    browser.open(uri, timeout);
    WebDocument webDoc = browser.getPageDomWithFrameContent();
    WebState webState = new WebState(webDoc);

    return webState;
  }
}
