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
