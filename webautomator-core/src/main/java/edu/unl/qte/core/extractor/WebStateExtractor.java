/*
 * Copyright 2015 Gigon Bae
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package edu.unl.qte.core.extractor;

import com.google.inject.Inject;
import edu.unl.qte.core.WebAutomator;
import edu.unl.qte.core.model.WebState;
import edu.unl.qte.core.model.WebDocument;
import edu.unl.qte.core.platform.WebBrowser;

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
